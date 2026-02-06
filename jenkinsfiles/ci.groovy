pipeline {
  agent any

  tools {
    maven 'maven-3'
  }

  environment {
    SONARQUBE_ENV = 'sonarqube'
    TRIVY_CACHE_DIR = '/var/lib/trivy'
    DOCKER_REGISTRY = 'cityonkar'
    BUILD_NUMBER_TAG = "${env.BUILD_NUMBER}"
    COMMIT = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
  }

  stages {

    stage('Checkout Code') {
      steps {
        checkout scm
      }
    }

    stage('Build, Scan & Push All Services') {
      steps {
        script {

          def services = [
            "user-service",
            "order-service",
            "payment-service",
            "inventory-service"
          ]

          for (service in services) {

            dir("services/${service}") {

              stage("Build - ${service}") {
                sh 'mvn clean package -DskipTests'
              }

              stage("SonarQube - ${service}") {
                withSonarQubeEnv('sonarqube') {
                  sh """
                    mvn sonar:sonar \
                    -Dsonar.projectKey=${service} \
                    -Dsonar.projectName=${service}
                  """
                }
              }

              stage("Quality Gate - ${service}") {
                timeout(time: 20, unit: 'MINUTES') {
                  waitForQualityGate abortPipeline: true
                }
              }

              stage("Docker Build - ${service}") {
                sh """
                  docker build -t ${DOCKER_REGISTRY}/${service}:git-${COMMIT} .
                  docker tag ${DOCKER_REGISTRY}/${service}:git-${COMMIT} ${DOCKER_REGISTRY}/${service}:build-${BUILD_NUMBER_TAG}
                """
              }

              stage("Trivy Scan - ${service}") {
                sh """
                  trivy image \
                  --exit-code 1 \
                  --severity HIGH,CRITICAL \
                  ${DOCKER_REGISTRY}/${service}:${BUILD_NUMBER_TAG}
                """
              }

              stage("Docker Push - ${service}") {
                sh """
                  docker push ${DOCKER_REGISTRY}/${service}:git-${COMMIT}
                  docker push ${DOCKER_REGISTRY}/${service}:build-${BUILD_NUMBER_TAG}
                """
              }
            }
          }
        }
      }
    }
  }

  post {
    success {
      echo "✅ CI PIPELINE SUCCESSFUL – All services built, scanned & pushed"
    }
    failure {
      echo "❌ CI PIPELINE FAILED – Check logs above"
    }
  }
}
