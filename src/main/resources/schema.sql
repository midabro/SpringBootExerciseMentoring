create table if not exists person
(
    id              varchar(255) not null,
    first_name      varchar(255),
    last_name       varchar(255),
    address_id      varchar(255),
    phone_number_id varchar(255),
    user_id varchar(255),
    primary key (id),
    foreign key (address_id) references address (address_id),
    foreign key (user_id) references user (id)
);

create table if not exists address
(
    address_id  varchar(255),
    city        varchar(255),
    postal_code varchar(255),
    street      varchar(255),
    primary key (address_id)
);

create table if not exists phone_number
(
    phone_number_id varchar(255),
    number          varchar(255),
    primary key (phone_number_id)
);

create table if not exists user
(
    id varchar(255) PRIMARY KEY not null,
    name varchar(255)

);

create table if not exists session
(
    id varchar(255) primary key not null,
    token varchar(255),
    expirationTime timestamp,
    user_id varchar(255),
    foreign key (user_id) references user (id)
);