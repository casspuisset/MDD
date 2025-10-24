# P6-Full-Stack-reseau-dev

## Init DB

```sql
DROP DATABASE IF EXISTS `MDDDB`;

CREATE DATABASE `MDDDB`;

USE `MDDDB`;

CREATE TABLE `TOPICS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(40),
  `description` VARCHAR(2000)
);

CREATE TABLE `ARTICLES` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(50),
  `description` VARCHAR(2000),
  `topic_id` INT,
  `creator_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `USERS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255),
  `email` VARCHAR(255),
  `password` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `COMMENTS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT,
  `article_id` INT,
  `content` VARCHAR(1000),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `SUBSCRIPTION` (
  `user_id` INT,
  `topic_id` INT
);

ALTER TABLE `ARTICLES` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`id`);
ALTER TABLE `ARTICLES` ADD FOREIGN KEY (`creator_id`) REFERENCES `USERS` (`id`);

ALTER TABLE `SUBSCRIPTION` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `SUBSCRIPTION` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`id`);

ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`article_id`) REFERENCES `ARTICLES` (`id`);

INSERT INTO TOPICS (name, description)
VALUES ('JavaScript', 'Description : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus.'),
       ('Java', 'Description : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus.'),
       ('Python', 'Description : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus.'),
       ('Web3', 'Description : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus.'),
       ('Other', 'Description : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus.');

```

## Front

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 20.3.6.

Don't forget to install your node_modules before starting (`npm install`).

### Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

### Where to start

As you may have seen if you already started the app, a simple home page containing a logo, a title and a button is available. If you take a look at its code (in the `home.component.html`) you will see that an external UI library is already configured in the project.

This library is `@angular/material`, it's one of the most famous in the angular ecosystem. As you can see on their docs (https://material.angular.io/), it contains a lot of highly customizable components that will help you design your interfaces quickly.

Note: I recommend to use material however it's not mandatory, if you prefer you can get rid of it.

Good luck!
