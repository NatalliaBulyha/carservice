DROP SCHEMA IF EXISTS `car_service`;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


CREATE SCHEMA IF NOT EXISTS `car_service` DEFAULT CHARACTER SET utf8 ;
USE `car_service` ;

CREATE TABLE IF NOT EXISTS `masters` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `qualification` ENUM("HIGH", "LOW", "MEDIUM") NOT NULL,
  `age` INT NOT NULL,
  `status` ENUM("DELETE", "WORK") NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `places` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `status` ENUM("DELETE", "REPAIR", "USED") NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `submission_date` DATE NOT NULL,
  `planned_completion_date` DATE NOT NULL,
  `completion_date` DATE NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `status` ENUM("CANCELLED", "CLOSED", "DELETED", "PROCESSED") NOT NULL,
  `master_id` INT NOT NULL,
  `place_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_masters_orders_idx` (`master_id` ASC) VISIBLE,
  INDEX `id_places_orders_idx` (`place_id` ASC) VISIBLE,
  CONSTRAINT `fk_id_masters_orders`
    FOREIGN KEY (`master_id`)
    REFERENCES `masters` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_places_orders`
    FOREIGN KEY (`place_id`)
    REFERENCES `places` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(250) NOT NULL,
  `status` ENUM("ACTIVE", "NOT_ACTIVE") NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `users_has_roles` (
  `users_id` INT NOT NULL,
  `roles_id` INT NOT NULL,
  PRIMARY KEY (`users_id`, `roles_id`),
  INDEX `fk_users_has_roles_roles1_idx` (`roles_id` ASC) VISIBLE,
  INDEX `fk_users_has_roles_users1_idx` (`users_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_has_roles_users1`
    FOREIGN KEY (`users_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_roles_roles1`
    FOREIGN KEY (`roles_id`)
    REFERENCES `roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `tokens` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `masters` (`name`,`qualification`,`age`,`status`)
VALUES ('John ','LOW',18,'WORK'),
('Anton','HIGH',32,'WORK'),
('Alex','HIGH',28,'WORK'),
('Evan','MEDIUM',22,'DELETE');

INSERT INTO `places` (`name`,`status`)
VALUES ('1d','USED'),
('1e','USED'),
('1a','USED'),
('2d','REPAIR'),
('2e','DELETE');

INSERT INTO `orders` (`submission_date`,`planned_completion_date`,`completion_date`,`price`,`status`,`master_id`,`place_id`)
VALUES ('2022-04-29','2022-04-29','2022-04-29',123.45,'CLOSED',3,3),
('2022-04-29','2022-04-30','2022-04-30',324.44,'CANCELLED',4,4),
('2022-04-29','2022-04-30','2022-04-30',431.12,'CLOSED',1,1),
('2022-04-29','2022-04-30','2022-04-30',78.23,'DELETED',2,2),
('2022-04-30','2022-04-30','2022-04-30',387.92,'CLOSED',3,3),
('2022-04-30','2022-04-30','2022-04-30',54.88,'CLOSED',2,2),
('2022-04-30','2022-05-01','2022-05-01',243.48,'CLOSED',2,2),
('2022-04-30','2022-05-01','2022-05-01',783.93,'PROCESSED',1,1),
('2022-04-30','2022-05-01','2022-05-01',253.84,'PROCESSED',3,3),
('2022-04-30','2022-05-02','2022-05-02',237.48,'PROCESSED',3,3),
('2022-04-30','2022-05-03','2022-05-03',257.83,'PROCESSED',1,1),
('2022-04-30','2022-05-02','2022-05-02',1220.16,'PROCESSED',1,1);

INSERT INTO `users` (`id`,`login`,`password`,`status`)
VALUES (1,'egor','$2a$10$dlDeGnqcFgtEPtB.ExU1UuAImpxbsDFH6aPA1qgeB0LztiIgToigW','ACTIVE'),
(2,'gena','$2a$10$nnZkw1.g9krRA8hdEOdWuerZ3IrNPSzZfu4FRv0LYj91G8wlQkmqu','ACTIVE'),
(3,'lena','$2a$10$CWkCC5PyZeWuvyZGYMy.KO9ilxnYXB11jAJPvYSguqxr72CMEHCLy','ACTIVE');

INSERT INTO `roles` (`id`,`role`)
VALUES (1,'ROLE_MASTER'),
(2,'ROLE_ADMIN');

INSERT INTO `users_has_roles` (`users_id`,`roles_id`)
VALUES (1,1),
(2,1),
(3,2);