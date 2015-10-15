-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema book
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `book` ;

-- -----------------------------------------------------
-- Schema book
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `book` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `book` ;

-- -----------------------------------------------------
-- Table `book`.`author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `book`.`author` ;

CREATE TABLE IF NOT EXISTS `book`.`author` (
  `author_id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `author_name` VARCHAR(80) NULL COMMENT '',
  `date_added` DATE NULL COMMENT '',
  PRIMARY KEY (`author_id`)  COMMENT '')
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `book`.`author` (`author_name`, `date_added`) VALUES ('John Smith', '2015-09-30');
INSERT INTO `book`.`author` (`author_name`, `date_added`) VALUES ('Jane Doe', '2015-09-30');

