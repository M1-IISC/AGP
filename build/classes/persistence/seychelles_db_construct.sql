-- Path: seychelles_db_construct.sql
-- For MYSQL
CREATE DATABASE IF NOT EXISTS seychelles;

-- Drop all tables in Seychelles if they exists
DROP TABLE IF EXISTS Seychelles.Transport;
DROP TABLE IF EXISTS Seychelles.Hotel;
DROP TABLE IF EXISTS Seychelles.Site;
DROP TABLE IF EXISTS Seychelles.Place;

-- Create the table Place with the following fields:
-- name (varchar(150), primary key) The name of the place
-- comfort (float) The comfort of the place
CREATE TABLE Seychelles.Place (
    name varchar(150) PRIMARY KEY,
    comfort float
) ENGINE=InnoDB;

-- Create the table Site wich is in relation 1-1 from Place with the following fields:
-- name (varchar(150), primary key) The name of the site
-- price (float) The price of the site
-- duration (float) The duration of the activity
-- category (enum ["HISTORIC", "LEISURE"]) The category of the site
-- name is a foreign key from Place
CREATE TABLE Seychelles.Site (
    name varchar(150) PRIMARY KEY,
    price float,
    duration float,
    category enum("HISTORIC", "LEISURE"),
    CONSTRAINT FOREIGN KEY (name) REFERENCES Place(name)
) ENGINE=InnoDB;

-- Create the table Hotel which is in relation 1-1 from Place with the following fields:
-- name (varchar(150), primary key) The name of the hotel
-- night_price (float) The Night price of the hotel
-- lunch_price (float) The launch price of the hotel
-- name is a foreign key from Place
CREATE TABLE Seychelles.Hotel (
    name varchar(150) PRIMARY KEY,
    night_price float,
    lunch_price float,
    CONSTRAINT FOREIGN KEY (name) REFERENCES Place(name)
) ENGINE=InnoDB;

-- Create the N-N relationship between Place and Place named Transport with the following fields:
-- start_place (varchar(150), primary key) The name of the first place
-- end_place (varchar(150), primary key) The name of the second place
-- type (enum ["WALK", "BOAT", "BUS"]) The type of transport
-- distance (float) The distance between the two places
-- start_place, end_place, type are a composite primary key
CREATE TABLE Seychelles.Transport (
    start_place varchar(150),
    end_place varchar(150),
    type enum("WALK", "BOAT", "BUS"),
    distance float,
    PRIMARY KEY (start_place, end_place, type),
    CONSTRAINT FOREIGN KEY (start_place) REFERENCES Place(name),
    CONSTRAINT FOREIGN KEY (end_place) REFERENCES Place(name)
) ENGINE=InnoDB;

-- Insert 2 fictive data into place
INSERT INTO Seychelles.Place (name, comfort) VALUES ("Hotel1", 0.3);
INSERT INTO Seychelles.Place (name, comfort) VALUES ("Site1", 0.7);

-- Insert 1 fictive data into site
INSERT INTO Seychelles.Site (name, price, duration, category) VALUES ("Site1", 10, 1, "HISTORIC");

-- Insert 1 fictive data into hotel
INSERT INTO Seychelles.Hotel (name, night_price, lunch_price) VALUES ("Hotel1", 100, 20);

-- Insert 2 fictive data into transport
INSERT INTO Seychelles.Transport (start_place, end_place, type, distance) VALUES ("Hotel1", "Site1", "WALK", 10);
INSERT INTO Seychelles.Transport (start_place, end_place, type, distance) VALUES ("Site1", "Hotel1", "WALK", 10);