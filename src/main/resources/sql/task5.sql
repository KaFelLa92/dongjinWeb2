drop database if exists springweb2;
create database springweb2;
use springweb2;

create table members (
	mno int auto_increment primary key,
    name varchar(20) not null,
	phone varchar(20) not null unique,
    age int not null
);

insert into members (name, phone, age) values 
('신동엽' , '010-7894-7894' , 50) , 
('강호동' , '010-4321-4321' , 40) ,
('유재석' , '010-1234-1234' , 30);

select * from members;