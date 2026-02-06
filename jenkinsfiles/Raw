pipeline {
    agent any

    tools {
        maven 'maven-3'
    }

    environment {
        SONARQUBE_ENV = 'sonarqube'
        DOCKERHUB_USER = 'cityonkar'
        IMAGE_TAG = "${BUILD_NUMBER}"
        TRIVY_CACHE_DIR = "/var/lib/trivy"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/CityOnkar/java-microservices-devsecops.git'
            }
        }

        stage('Build + Sonar (Sequential)') {
            steps {
                script {

                    def services = [
                        'user-service',
                        'order-service',
                        'payment-service',
                        'inventory-service',
                        'auth-service',
                        'notification-service',
                        'shipping-service',
                        'analytics-service',
                        'gateway-service',
                        'config-service'
                    ]

                    for (service in services) {

                        stage("Build & Scan - ${service}") {

                            dir("services/${service}") {

                                sh 'mvn clean package'

                                withSonarQubeEnv("${SONARQUBE_ENV}") {
                                    sh """
                                      mvn sonar:sonar \
                                      -Dsonar.projectKey=${service} \
                                      -Dsonar.projectName=${service}
                                    """
                                }

                                timeout(time: 10, unit: 'MINUTES') {
                                    waitForQualityGate abortPipeline: true
                                }
                            }
                        }
                    }
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {

                    def services = [
                        'user-service',
                        'order-service',
                        'payment-service',
                        'inventory-service',
                        'auth-service',
                        'notification-service',
                        'shipping-service',
                        'analytics-service',
                        'gateway-service',
                        'config-service'
                    ]

                    for (service in services) {

                        dir("services/${service}") {
                            sh """
                              docker build -t ${DOCKERHUB_USER}/${service}:${IMAGE_TAG} .
                            """
                        }
                    }
                }
            }
        }
        
        stage('Trivy Image Scan') {
            steps {
                script {
                    def services = [
                        'user-service',
                        'order-service',
                        'payment-service',
                        'inventory-service',
                        'auth-service',
                        'notification-service',
                        'shipping-service',
                        'analytics-service',
                        'gateway-service',
                        'config-service'
                    ]
        
                    for (service in services) {
        
                        sh """
                          trivy image \
                          --exit-code 1 \
                          --severity CRITICAL,HIGH \
                          ${DOCKERHUB_USER}/${service}:${IMAGE_TAG}
                        """
                    }
                }
            }
        }
        
        stage('Docker Push') {
            steps {
                script {
        
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-creds') {
        
                        def services = [
                            'user-service',
                            'order-service',
                            'payment-service',
                            'inventory-service',
                            'auth-service',
                            'notification-service',
                            'shipping-service',
                            'analytics-service',
                            'gateway-service',
                            'config-service'
                        ]
        
                        for (service in services) {
                            sh "docker push ${DOCKERHUB_USER}/${service}:${IMAGE_TAG}"
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ CI PIPELINE SUCCESSFUL – All services passed Quality Gate"
        }
        failure {
            echo "❌ CI PIPELINE FAILED – Check Sonar / Build logs"
        }
    }
}
