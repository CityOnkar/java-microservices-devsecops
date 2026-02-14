terraform {
  backend "s3" {
    bucket         = "devsecops-terraform-state-cityzen"
    key            = "eks/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "terraform-state-lock"
    encrypt        = true
  }
}
