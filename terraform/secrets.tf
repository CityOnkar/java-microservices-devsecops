resource "random_password" "user_db_password" {
  length  = 16
  special = true
}

resource "aws_secretsmanager_secret" "user_db" {
  name = "user-service-db-secret"
}

resource "aws_secretsmanager_secret_version" "user_db" {
  secret_id = aws_secretsmanager_secret.user_db.id
  secret_string = jsonencode({
    username = "userdbuser"
    password = random_password.user_db_password.result
  })
}


resource "random_password" "order_db_password" {
  length  = 16
  special = true
}

resource "aws_secretsmanager_secret" "order_db" {
  name = "order-service-db-secret"
}

resource "aws_secretsmanager_secret_version" "order_db" {
  secret_id = aws_secretsmanager_secret.order_db.id
  secret_string = jsonencode({
    username = "orderdbuser"
    password = random_password.order_db_password.result
  })
}
