
Java version - 11

CREATE TABLE users (
  id int(10) NOT NULL AUTO_INCREMENT
  username VARCHAR(50) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);


INSERT INTO users (username, password, enabled)
  values ('bryan',
    '$2a$10$a07FaSKwo2xAwEj4UJYa0etu8sY5o9onG/0psQ2FxzjviueQUYnbm');
