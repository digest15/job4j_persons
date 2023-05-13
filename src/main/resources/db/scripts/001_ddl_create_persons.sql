create table person (
    id serial primary key not null,
    login varchar(2000) NOT NULL unique,
    password varchar(2000) NOT NULL
);