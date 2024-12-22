-- DROP ALL ------------------------------------------------------------------------------------

drop table car;
drop table maker;
drop table country;
drop table body;

-- TABLE CREATIONS -----------------------------------------------------------------------------

create table body(
                     id serial not null primary key,
                     name varchar not null
);

--

create table country(
                        id serial not null primary key,
                        name varchar not null
);

--

create table maker(
                      id serial not null primary key,
                      name varchar not null,
                      maker_country_id integer not null references country (id) ON UPDATE NO ACTION ON DELETE CASCADE,
                      birthday_year integer not null
);

--

create table car(
                    id serial not null primary key,
                    name varchar not null,
                    car_maker_id integer not null references maker (id) ON UPDATE NO ACTION ON DELETE CASCADE,
                    year integer not null,
                    car_body_id integer not null references body (id) ON UPDATE NO ACTION ON DELETE CASCADE,
                    mile integer not null
);

-- DATA INSERTIONS ------------------------------------------------------------------------

insert into body (name)
values
    ('Кузов 1'),
    ('Кузов 2'),
    ('Кузов 3')
    returning *;

insert into country (name)
values
    ('Россия'),
    ('США'),
    ('Великобритания'),
    ('Австралия')
    returning *;

insert into maker (name, maker_country_id, birthday_year)
values
    ('Компания 1', 2, 2000),
    ('Компания 2', 3, 2001),
    ('Компания 3', 1, 2002)
    returning *;

-- Вставка книг
insert into car (name, car_maker_id, year, car_body_id, mile)
values
    ('Машина 1', 1, 2005, 1, 688),
    ('Машина 2', 2, 2006, 1, 223),
    ('Машина 3', 3, 2007, 3, 1225)
    returning *;

-- DATA SELECTIONS ------------------------------------------------------------------------

select * from body;
select * from country;
select * from maker;
select * from car;
