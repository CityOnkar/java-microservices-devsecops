module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "5.1.0"

  name = var.vpc_name
  cidr = var.vpc_cidr

  azs             = var.availability_zones
  public_subnets  = var.public_subnets
  private_subnets = var.private_subnets

  enable_nat_gateway = true
  single_nat_gateway = true

  tags = {
    Project = "devsecops"
  }
}

resource "aws_db_subnet_group" "rds_private" {
  name       = "rds-private-subnet-group"
  subnet_ids = module.vpc.private_subnets

  tags = {
    Name = "rds-private"
  }
}
