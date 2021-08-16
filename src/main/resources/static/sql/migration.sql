CREATE DATABASE IF NOT EXISTS slim_gym;

USE slim_gym;


DROP TABLE IF EXISTS gym_equipment;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS pictures;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS gym;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(240) NOT NULL,
    last_name VARCHAR(240) NOT NULL,
    username VARCHAR(240) NOT NULL,
    email    VARCHAR(240) NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_pic VARCHAR(240),
    emergency_first_name VARCHAR(240) NOT NULL,
    emergency_last_name VARCHAR(240) NOT NULL,
    emergency_phone_number LONG UNSIGNED NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reviews
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    gym_id INT UNSIGNED NOT NULL,
    rating INT UNSIGNED NOT NULL,
    body TEXT NOT NULL,
    picture VARCHAR(500) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE,
    FOREIGN KEY (gym_id) REFERENCES users (id)
        ON DELETE CASCADE
);


CREATE TABLE schedule
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    gym_id INT UNSIGNED NOT NULL,
    date DATE,
    start_time TIME,
    end_time TIME,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE,
    FOREIGN KEY (gym_id) REFERENCES users (id)
        ON DELETE CASCADE
);

CREATE TABLE gym
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id INT UNSIGNED NOT NULL,
    name VARCHAR(240) NOT NULL,
    address VARCHAR(240) NOT NULL,
    description TEXT  NOT NULL,
    equipment VARCHAR(240) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE
);

CREATE TABLE pictures
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    gym_id INT UNSIGNED NOT NULL,
    url VARCHAR(240) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (gym_id) REFERENCES gym (id)
        ON DELETE CASCADE
);

CREATE TABLE equipment
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(240) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE gym_equipment
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    gym_id INT UNSIGNED NOT NULL,
    equipment_type_id INT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (gym_id) REFERENCES gym (id)
        ON DELETE CASCADE,
    FOREIGN KEY (equipment_type_id) REFERENCES equipment (id)
        ON DELETE CASCADE
);

