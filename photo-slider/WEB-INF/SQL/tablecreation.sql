
CREATE TABLE IF NOT EXISTS `photoslider`.`theme` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `on` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

--


CREATE TABLE IF NOT EXISTS `photoslider`.`media` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `filePath` VARCHAR(150) NULL,
  `active` TINYINT(1) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- 

CREATE TABLE IF NOT EXISTS `photoslider`.`photo` (
  `id` INT NOT NULL,
  `Soundeffect_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Photo_Media1_idx` (`id` ASC),
  INDEX `fk_Photo_Soundeffect1_idx` (`Soundeffect_id` ASC),
  CONSTRAINT `fk_Photo_Media1`
    FOREIGN KEY (`id`)
    REFERENCES `photoslider`.`media` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

--


CREATE TABLE IF NOT EXISTS `photoslider`.`soundeffect` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `media_soundeffect_id` (`id` ASC),
  CONSTRAINT `fk_Soundeffect_media1`
    FOREIGN KEY (`id`)
    REFERENCES `photoslider`.`media` (`id`))
ENGINE = InnoDB;

-- 

CREATE TABLE IF NOT EXISTS `photoslider`.`song` (
  `id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Song_Media1_idx` (`id` ASC),
  CONSTRAINT `fk_Song_Media1`
    FOREIGN KEY (`id`)
    REFERENCES `photoslider`.`media` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- 

CREATE TABLE IF NOT EXISTS `photoslider`.`theme_has_media` (
  `theme_id` INT NOT NULL,
  `media_id` INT NOT NULL,
  PRIMARY KEY (`theme_id`, `media_id`),
  INDEX `fk_Theme_has_Media_Media1_idx` (`media_id` ASC),
  INDEX `fk_Theme_has_Media_Theme1_idx` (`theme_id` ASC),
  CONSTRAINT `fk_Theme_has_Media_Theme1`
    FOREIGN KEY (`theme_id`)
    REFERENCES `photoslider`.`theme` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Theme_has_Media_Media1`
    FOREIGN KEY (`media_id`)
    REFERENCES `photoslider`.`media` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

