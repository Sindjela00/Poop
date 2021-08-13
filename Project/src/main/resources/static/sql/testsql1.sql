-- MySQL Script generated by MySQL Workbench
-- Wed Aug  4 13:13:51 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema baza
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema baza
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `baza` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `baza` ;

-- -----------------------------------------------------
-- Table `baza`.`tags`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`tags` ;

CREATE TABLE IF NOT EXISTS `baza`.`tags` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`Korisnik`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`Korisnik` ;

CREATE TABLE IF NOT EXISTS `baza`.`Korisnik` (
  `idKorisnik` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(50) NOT NULL,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Poslodavac` TINYINT NOT NULL,
  `Admin` TINYINT NOT NULL,
  PRIMARY KEY (`idKorisnik`),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE,
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`Oglas`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`Oglas` ;

CREATE TABLE IF NOT EXISTS `baza`.`Oglas` (
  `idOglas` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idCoveka` INT UNSIGNED NOT NULL,
  `Naslov` VARCHAR(50) NOT NULL,
  `Tip` TINYINT UNSIGNED NOT NULL,
  `Plata` INT UNSIGNED NULL,
  `Opis` VARCHAR(512) NULL,
  PRIMARY KEY (`idOglas`),
  INDEX `Gazda_idx` (`idCoveka` ASC) VISIBLE,
  CONSTRAINT `Gazda`
    FOREIGN KEY (`idCoveka`)
    REFERENCES `baza`.`Korisnik` (`idKorisnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`lajkovi`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`lajkovi` ;

CREATE TABLE IF NOT EXISTS `baza`.`lajkovi` (
  `idCoveka` INT UNSIGNED NOT NULL,
  `idOglasa` INT UNSIGNED NOT NULL,
  `lajk` TINYINT NOT NULL,
  PRIMARY KEY (`idCoveka`, `idOglasa`),
  INDEX `Oglas_idx` (`idOglasa` ASC) VISIBLE,
  CONSTRAINT `Covek`
    FOREIGN KEY (`idCoveka`)
    REFERENCES `baza`.`Korisnik` (`idKorisnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Oglas`
    FOREIGN KEY (`idOglasa`)
    REFERENCES `baza`.`Oglas` (`idOglas`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`poruke`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`poruke` ;

CREATE TABLE IF NOT EXISTS `baza`.`poruke` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idCoveka` INT UNSIGNED NOT NULL,
  `idOglasa` INT UNSIGNED NOT NULL,
  `odgovor` INT NULL,
  `tekst` VARCHAR(512) NULL,
  PRIMARY KEY (`id`),
  INDEX `covekid_idx` (`idCoveka` ASC) VISIBLE,
  INDEX `oglasa_idx` (`idOglasa` ASC) VISIBLE,
  CONSTRAINT `covekid`
    FOREIGN KEY (`idCoveka`)
    REFERENCES `baza`.`Korisnik` (`idKorisnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `oglasa`
    FOREIGN KEY (`idOglasa`)
    REFERENCES `baza`.`Oglas` (`idOglas`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`podtags`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`podtags` ;

CREATE TABLE IF NOT EXISTS `baza`.`podtags` (
  `id` INT UNSIGNED NOT NULL,
  `idkategorije` INT UNSIGNED NOT NULL,
  `podkategorija` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `kategorija_idx` (`idkategorije` ASC) VISIBLE,
  CONSTRAINT `kategorija`
    FOREIGN KEY (`idkategorije`)
    REFERENCES `baza`.`tags` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`tagovi`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`tagovi` ;

CREATE TABLE IF NOT EXISTS `baza`.`tagovi` (
  `idoglasa` INT UNSIGNED NOT NULL,
  `idtaga` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idoglasa`, `idtaga`),
  INDEX `tag_idx` (`idtaga` ASC) VISIBLE,
  CONSTRAINT `firma`
    FOREIGN KEY (`idoglasa`)
    REFERENCES `baza`.`Oglas` (`idOglas`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `tag`
    FOREIGN KEY (`idtaga`)
    REFERENCES `baza`.`podtags` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`Telefoni`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`Telefoni` ;

CREATE TABLE IF NOT EXISTS `baza`.`Telefoni` (
  `idKorisnika` INT UNSIGNED NOT NULL,
  `Broj` INT(15) NOT NULL,
  INDEX `Covek_idx` (`idKorisnika` ASC) VISIBLE,
  PRIMARY KEY (`idKorisnika`, `Broj`),
  CONSTRAINT `Covek`
    FOREIGN KEY (`idKorisnika`)
    REFERENCES `baza`.`Korisnik` (`idKorisnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`Ocena`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`Ocena` ;

CREATE TABLE IF NOT EXISTS `baza`.`Ocena` (
  `IdCoveka` INT UNSIGNED NOT NULL,
  `IdOcenjenog` INT UNSIGNED NOT NULL,
  `Ocena` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`IdCoveka`, `IdOcenjenog`),
  INDEX `IdOcenjog_idx` (`IdOcenjenog` ASC) VISIBLE,
  CONSTRAINT `IdCoveka`
    FOREIGN KEY (`IdCoveka`)
    REFERENCES `baza`.`Korisnik` (`idKorisnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `IdOcenjog`
    FOREIGN KEY (`IdOcenjenog`)
    REFERENCES `baza`.`Korisnik` (`idKorisnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`Mesto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`Mesto` ;

CREATE TABLE IF NOT EXISTS `baza`.`Mesto` (
  `idMesto` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idMesto`),
  UNIQUE INDEX `idMesto_UNIQUE` (`idMesto` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`MestoStanovanja`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`MestoStanovanja` ;

CREATE TABLE IF NOT EXISTS `baza`.`MestoStanovanja` (
  `idCoveka` INT UNSIGNED NOT NULL,
  `idMesta` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idCoveka`, `idMesta`),
  INDEX `Mesto_idx` (`idMesta` ASC) VISIBLE,
  CONSTRAINT `Covek`
    FOREIGN KEY (`idCoveka`)
    REFERENCES `baza`.`Korisnik` (`idKorisnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `Mesto`
    FOREIGN KEY (`idMesta`)
    REFERENCES `baza`.`Mesto` (`idMesto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`MestoPosla`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`MestoPosla` ;

CREATE TABLE IF NOT EXISTS `baza`.`MestoPosla` (
  `idPosla` INT UNSIGNED NOT NULL,
  `idMesta` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idPosla`, `idMesta`),
  INDEX `mes_idx` (`idMesta` ASC) VISIBLE,
  CONSTRAINT `mes`
    FOREIGN KEY (`idMesta`)
    REFERENCES `baza`.`Mesto` (`idMesto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `pos`
    FOREIGN KEY (`idPosla`)
    REFERENCES `baza`.`Oglas` (`idOglas`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `baza`.`Opis`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `baza`.`Opis` ;

CREATE TABLE IF NOT EXISTS `baza`.`Opis` (
  `idCovek` INT UNSIGNED NOT NULL,
  `Opis` VARCHAR(512) NOT NULL,
  PRIMARY KEY (`idCovek`),
  CONSTRAINT `IdCoek`
    FOREIGN KEY (`idCovek`)
    REFERENCES `baza`.`Korisnik` (`idKorisnik`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;