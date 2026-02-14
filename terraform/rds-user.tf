resource "aws_db_instance" "user_db" {
  identifier              = "user-service-db"
  engine                  = "mysql"
  engine_version          = "8.0"
  instance_class          = "db.t3.micro"

  allocated_storage       = 20
  db_name                 = "userdb"
  username                = "userdbuser"
  password                = random_password.user_db_password.result

  db_subnet_group_name    = aws_db_subnet_group.rds_private.name
  vpc_security_group_ids  = [aws_security_group.rds_mysql.id]

  skip_final_snapshot     = true
  publicly_accessible     = false
  multi_az                = false

  tags = {
    Service = "user-service"
  }
}


