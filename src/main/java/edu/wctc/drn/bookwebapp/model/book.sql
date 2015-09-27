CREATE SCHEMA `book` ;

CREATE TABLE `book`.`author` (
  `author_id` INT NOT NULL AUTO_INCREMENT,
  `author_name` VARCHAR(80) NULL,
  `date_added` DATE NULL,
  PRIMARY KEY (`author_id`));