drop database if exists HotelReservation;

create database HotelReservation;

use HotelReservation;

create table Hotel(
	hotelId int primary key not null auto_increment,
    name varchar(128) not null,
    address varchar(128) not null
);

create table RoomType(
	roomTypeId int primary key not null auto_increment,
    `name` varchar(45) not null
);

create table Room(
	roomId int primary key not null auto_increment,
    roomNumber int not null,
    floorNumber int not null,
    occupancyLimit int not null,
    roomTypeId int not null,
    hotelId int not null,
    foreign key (roomTypeId) references RoomType(roomTypeId),
    foreign key (hotelId) references Hotel(hotelId)
);

create table Amenity(
	amenityId int primary key not null auto_increment,
    `name` varchar(45) not null
);

create table RoomAmenity(
	roomId int not null,
    amenityId int not null,
    primary key (roomId, amenityId),
    foreign key (roomId) references Room(roomId),
    foreign key (amenityId) references Amenity(amenityId)
);

create table RoomRate(
    roomId int not null,
    `date` date not null,
    rate decimal(12,2) not null,
    primary key (roomId, `date`),
    foreign key (roomId) references Room(roomId)
);

create table Customer(
	customerId int not null primary key auto_increment,
    firstName varchar(45) not null,
    lastName varchar(45) null,
    phoneNumber char(10) null,
    email varchar(45) null
);

create table Reservation(
	reservationId int primary key not null auto_increment,
    customerId int not null,
    startDate date not null,
    endDate date not null,
    canceled boolean default 0 not null,
    foreign key (customerId) references Customer(customerId)
);

create table RoomReservation(
	roomId int not null,
    reservationId int not null,
    upgradedFrom int null,
    waived boolean default 0 not null,
    primary key (roomId, reservationId),
    foreign key (roomId) references Room(roomId),
    foreign key (reservationId) references Reservation(reservationId),
    foreign key (upgradedFrom) references Room(roomId)
);

create table Guest(
	guestId int primary key not null auto_increment,
    `name` varchar(45) not null,
    age int not null
);

create table ReservationGuest(
	reservationId int not null,
    guestId int not null,
    primary key (reservationId, guestId),
    foreign key (reservationId) references Reservation(reservationId),
    foreign key (guestId) references Guest(guestId)
);

create table Addon(
	addonId int primary key not null auto_increment,
    `name` varchar(45) not null,
    hotelId int not null,
    foreign key (hotelId) references Hotel(hotelId)
);

create table AddonRate(
	addonId int not null,
    rate decimal(12,2) not null,
    `date` date not null,
    primary key (addonId, `date`),
    foreign key (addonId) references Addon(addonId)
);

create table ReservationAddon(
	reservationId int not null,
    addonId int not null,
    `date` date not null,
    waived boolean default 0 not null,
    primary key (reservationId, addonId),
    foreign key (reservationId) references Reservation(reservationId),
    foreign key (addonId) references Addon(addonId)
);

create table PromotionType(
	promotionTypeId int primary key not null auto_increment,
    `type` varchar(45)
);

create table Promotion(
	promotionId int primary key not null auto_increment,
    startDate date not null,
    endDate date not null,
    promotionTypeId int not null,
    amount decimal(12,2),
    foreign key (promotionTypeId) references PromotionType(promotionTypeId)
);

create table ReservationPromotion(
	reservationId int not null,
    promotionId int not null,
    primary key (reservationId, promotionId),
    foreign key (reservationId) references Reservation(reservationId),
    foreign key (promotionId) references Promotion(promotionId)
);

create table InvoiceHeader(
	invoiceHeaderId int primary key not null auto_increment,
    reservationId int not null,
    total decimal(12,2) not null,
    tax decimal(12,2) null,
    totalAndTax decimal(12,2) not null,
    foreign key (reservationId) references Reservation(reservationId)
);

create table InvoiceDetails(
	invoiceDetailsId int primary key not null auto_increment,
    invoiceHeaderId int not null,
    itemDescription varchar(128) not null,
    amount decimal(12,2) not null,
    foreign key (invoiceHeaderId) references InvoiceHeader(invoiceHeaderId)
);