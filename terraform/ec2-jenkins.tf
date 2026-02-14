data "aws_ami" "ubuntu" {
  most_recent = true
  owners      = ["099720109477"]

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"]
  }
}

resource "aws_instance" "jenkins" {
  ami                    = data.aws_ami.ubuntu.id
  instance_type          = "m7i-flex.large"
  subnet_id              = module.vpc.public_subnets[0]
  vpc_security_group_ids = [aws_security_group.jenkins_sg.id]
  iam_instance_profile   = aws_iam_instance_profile.jenkins_profile.name
  key_name               = "aws_login"

  associate_public_ip_address = true
  
  root_block_device {
    volume_size = 50        # increase from 8 GB → 50 GB
    volume_type = "gp3"
    delete_on_termination = true
  }

  tags = {
    Name    = "jenkins-server"
    Project = "devsecops"
  }
}
