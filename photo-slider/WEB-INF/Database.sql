-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Theme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Theme` (
  `idTheme` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `on/off` TINYINT(1) NOT NULL,
  PRIMARY KEY (`idTheme`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`MediaType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`MediaType` (
  `idMediaType` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idMediaType`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Media`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Media` (
  `idMedia` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `on/off` TINYINT(1) NOT NULL,
  `URL` VARCHAR(45) NOT NULL,
  `MediaType_idMediaType` INT NOT NULL,
  `Theme_idTheme` INT NOT NULL,
  PRIMARY KEY (`idMedia`, `MediaType_idMediaType`, `Theme_idTheme`),
  INDEX `fk_Media_MediaType_idx` (`MediaType_idMediaType` ASC),
  INDEX `fk_Media_Theme1_idx` (`Theme_idTheme` ASC),
  CONSTRAINT `fk_Media_MediaType`
    FOREIGN KEY (`MediaType_idMediaType`)
    REFERENCES `mydb`.`MediaType` (`idMediaType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Media_Theme1`
    FOREIGN KEY (`Theme_idTheme`)
    REFERENCES `mydb`.`Theme` (`idTheme`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
