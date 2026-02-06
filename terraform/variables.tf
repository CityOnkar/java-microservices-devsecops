variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

variable "cluster_name" {
  description = "EKS cluster name"
  type        = string
  default     = "devsecops-eks"
}

variable "cluster_version" {
  description = "EKS Kubernetes version"
  type        = string
  default     = "1.29"
}

variable "vpc_name" {
  description = "VPC name"
  type        = string
  default     = "devsecops-vpc"
}

variable "vpc_cidr" {
  description = "VPC CIDR block"
  type        = string
  default     = "10.0.0.0/16"
}

variable "availability_zones" {
  description = "Availability zones"
  type        = list(string)
  default     = ["us-east-1a", "us-east-1b"]
}

variable "public_subnets" {
  description = "Public subnet CIDRs"
  type        = list(string)
  default     = ["10.0.1.0/24", "10.0.2.0/24"]
}

variable "private_subnets" {
  description = "Private subnet CIDRs"
  type        = list(string)
  default     = ["10.0.11.0/24", "10.0.12.0/24"]
}

variable "node_instance_types" {
  description = "EKS worker node instance types"
  type        = list(string)
  default     = ["c7i-flex.large"]
}

variable "desired_capacity" {
  description = "Desired worker nodes"
  type        = number
  default     = 2
}

variable "min_capacity" {
  description = "Minimum worker nodes"
  type        = number
  default     = 1
}

variable "max_capacity" {
  description = "Maximum worker nodes"
  type        = number
  default     = 3
}
