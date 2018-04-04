CREATE TABLE IF NOT EXISTS `user` (
    `id` INT(20) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(64) NOT NULL,
    `email` VARCHAR(128) NOT NULL,
    `password` VARCHAR(256) NOT NULL,
    `status` TINYINT(2) DEFAULT 0 NOT NULL,
    `register_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_username` (`username`)
)ENGINE=InnoDB AUTO_INCREMENT=65487 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `role` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=65487 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `user_role` (
    `user_id` INT(20) NOT NULL,
    `role_id` INT(11) NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`)
)ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `topic` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(256) NOT NULL,
    `url` VARCHAR(512) NOT NULL,
    `domain` VARCHAR(128) NOT NULL,
    `topic_type` TINYINT NOT NULL COMMENT '0 - new, 1 - show',
    `created_by` INT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (id),
    UNIQUE KEY `uk_url` (`url`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ask` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(256) NOT NULL,
    `content` TEXT NOT NULL,
    `created_by` INT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `job` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(256) NOT NULL,
    `content` TEXT NOT NULL,
    `url` VARCHAR(256) NOT NULL,
    `redirect` TINYINT NOT NULL,
    `created_by` INT NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
