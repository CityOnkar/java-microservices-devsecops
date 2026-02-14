resource "aws_instance" "sonarqube" {
  ami           = data.aws_ami.ubuntu.id
  instance_type = "c7i-flex.large"
  key_name      = "aws_login"
  subnet_id     = module.vpc.public_subnets[0]
  vpc_security_group_ids = [aws_security_group.sonarqube_sg.id]

  associate_public_ip_address = true
  
  tags = {
    Name = "sonarqube-server"
  }
}
