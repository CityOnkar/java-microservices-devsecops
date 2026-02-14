output "cluster_name" {
  value = module.eks.cluster_name
}

output "cluster_endpoint" {
  value = module.eks.cluster_endpoint
}

output "jenkins_public_ip" {
  description = "Public IP address of the Jenkins EC2 server"
  value       = aws_instance.jenkins.public_ip
}

output "sonarqube_public_ip" {
  description = "Public IP address of the SonarQube EC2 server"
  value       = aws_instance.sonarqube.public_ip
}

output "user_db_endpoint" {
  value = aws_db_instance.user_db.endpoint
}

output "order_db_endpoint" {
  value = aws_db_instance.order_db.endpoint
}
