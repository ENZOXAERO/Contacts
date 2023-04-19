
create database android;

use android;

create table contacts
(
    code  int auto_increment
        primary key,
    name  varchar(200) not null,
    phone varchar(12)  null
);

