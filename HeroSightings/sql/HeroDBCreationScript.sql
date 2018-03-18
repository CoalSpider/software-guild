drop database if exists Heros;
create database Heros;

use Heros;

create table Hero(
	heroId int primary key auto_increment,
    `name` varchar(60) not null,
    description varchar(500) not null
);

create table Superpower(
	superpowerId int primary key auto_increment,
    `name` varchar(50) not null,
    description varchar(500) not null
);

create table HeroSuperpower(
	heroId int not null,
    superpowerId int not null,
    primary key (heroId, superpowerId),
    foreign key (heroId) references Hero(heroId),
    foreign key (superpowerId) references Superpower(superpowerId)
);

create table Location(
	locationId int primary key auto_increment,
    `name` varchar(50) not null,
    description varchar(500) not null,
    address varchar(100) not null,
    -- 5 deciaml places will give us gps accuracy to about 1,1 meters similar to google maps assisted by wifi
    -- as the user will most likely use our website or mobile app to report sightings this will be plenty accurate
    latitude decimal(8,5) not null,
    longitude decimal(8,5) not null
);

create table Sighting(
	sightingId int primary key auto_increment,
    dateAndTime datetime not null,
    locationId int not null,
    foreign key (locationId) references Location(locationId)
);

create table HeroSighting(
	heroId int not null,
    sightingId int not null,
    primary key (heroId, sightingId),
    foreign key (heroId) references Hero(heroId),
    foreign key (sightingId) references Sighting(sightingId)
);

create table Organization(
	organizationId int primary key auto_increment,
    `name` varchar(100) not null,
    description varchar(500) not null,
    address varchar(100) not null,
    phoneNumber varchar(30) not null,
    email varchar(100) not null
);

create table HeroOrganization(
	heroId int not null,
    organizationId int not null,
    primary key (heroId, organizationId),
    foreign key (heroId) references Hero(heroId),
    foreign key (organizationId) references Organization(organizationId)
);
SELECT s.* FROM herosuperpower AS hs JOIN superpower AS s ON s.superpowerId = hs.superpowerid WHERE hs.heroId=1;