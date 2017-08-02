CREATE DATABASE IF NOT EXISTS `sevendb`
  DEFAULT CHARACTER SET utf8;
USE `sevendb`;

DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `report`;
DROP TABLE IF EXISTS `company`;
DROP TABLE IF EXISTS `role`;

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `id`      INT(11)      NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(255) NOT NULL,
  `email`   VARCHAR(100) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_company_name_email` (`name`, `email`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 100000
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `id`        INT(11)      NOT NULL AUTO_INCREMENT,
  `companyid` INT(11)      NOT NULL,
  `name`      VARCHAR(255) NOT NULL,
  `data`      TEXT         NOT NULL,
  `time`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `par_idx` (`companyid`),
  CONSTRAINT `report_ibfk_1` FOREIGN KEY (`companyid`) REFERENCES `company` (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 300000
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id`        INT(11)      NOT NULL AUTO_INCREMENT,
  `companyid` INT(11),
  `name`      VARCHAR(255) NOT NULL,
  `lastname`  VARCHAR(255) NOT NULL,
  `email`     VARCHAR(100) NOT NULL,
  `phone`     VARCHAR(30)  NOT NULL,
  `password`  VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_unique_email_idx` (`email`),
  KEY `par_idx` (`companyid`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`companyid`) REFERENCES `company` (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 200000
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id`   INT(11) NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(255)     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` INT(11) NOT NULL,
  `role_id` INT(11) NOT NULL,
  UNIQUE KEY `user_roles_idx` (`user_id`, `role_id`),
  KEY `role_id_key` (`role_id`),
  CONSTRAINT `user_fk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `role_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;