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
    ('Седан'),
    ('Хэтчбек'),
    ('Универсал'),
    ('Купе'),
    ('Кабриолет'),
    ('Минивэн'),
    ('Кроссовер'),
    ('Пикап'),
    ('Родстер'),
    ('Фургон'),
    ('Лифтбек')
    returning *;

insert into country (name)
values
    ('Япония'),
    ('США'),
    ('Великобритания'),
    ('Корея'),
    ('Россия'),
    ('Германия'),
    ('Италия'),
    ('Китай'),
    ('Франция')
    returning *;

insert into maker (name, maker_country_id, birthday_year)
values
    ('Toyota', 1, 1937),
    ('Ford', 2, 1903),
    ('BMW', 6, 1916),
    ('Lada', 5, 1973),
    ('Audi', 6, 1909),
    ('Chevrolet', 2, 1911),
    ('Honda', 1, 1948),
    ('Volkswagen', 6, 1937),
    ('Nissan', 1, 1933),
    ('Hyundai', 4, 1967),
    ('Peugeot', 9, 1810),
    ('Porsche', 6, 1931),
    ('Mitsubishi', 1, 1870),
    ('Subaru', 1, 1953),
    ('Kia', 4, 1944),
    ('Mazda', 1, 1920),
    ('Renault', 9, 1899),
    ('Tesla', 2, 2003),
    ('Land Rover', 3, 1948),
    ('Geely', 8, 1986),
    ('Cherry', 8, 1997)
    returning *;

-- Вставка книг
insert into car (name, car_maker_id, year, car_body_id, mile)
values
    ('Camry', 1, 2020, 1, 20000),
    ('Corolla', 1, 2019, 1, 15000),
    ('Prius', 1, 2021, 1, 5000),
    ('Land Cruiser', 1, 2018, 7, 35000),
    ('Highlander', 1, 2020, 7, 17000),
    ('Mustang', 2, 2021, 4, 10000),
    ('F-150', 2, 2020, 8, 30000),
    ('Explorer', 2, 2019, 7, 25000),
    ('Escape', 2, 2021, 7, 18000),
    ('3 Series', 3, 2018, 1, 25000),
    ('X5', 3, 2019, 7, 18000),
    ('X3', 3, 2020, 7, 22000),
    ('X1', 3, 2021, 7, 14000),
    ('Granta', 4, 2020, 1, 20000),
    ('Vesta', 4, 2021, 1, 10000),
    ('Niva', 4, 2019, 7, 25000),
    ('A4', 5, 2020, 1, 12000),
    ('Q5', 5, 2019, 7, 22000),
    ('A6', 5, 2021, 1, 8000),
    ('Q7', 5, 2018, 7, 30000),
    ('Cruze', 6, 2018, 1, 40000),
    ('Malibu', 6, 2019, 1, 23000),
    ('Traverse', 6, 2020, 7, 20000),
    ('Tahoe', 6, 2021, 7, 15000),
    ('Civic', 7, 2020, 1, 15000),
    ('Accord', 7, 2019, 1, 22000),
    ('CR-V', 7, 2018, 7, 26000),
    ('Jazz', 7, 2021, 1, 5000),
    ('Pilot', 7, 2020, 7, 20000),
    ('Golf', 8, 2018, 2, 32000),
    ('Passat', 8, 2019, 1, 19000),
    ('Tiguan', 8, 2020, 7, 21000),
    ('Polo', 8, 2021, 1, 8000),
    ('Touareg', 8, 2019, 7, 18000),
    ('Altima', 9, 2020, 1, 13000),
    ('Sentra', 9, 2019, 1, 24000),
    ('Rogue', 9, 2021, 7, 12000),
    ('Pathfinder', 9, 2020, 7, 15000),
    ('Santa Fe', 10, 2019, 7, 25000),
    ('Elantra', 10, 2021, 1, 12000),
    ('Tucson', 10, 2020, 7, 20000),
    ('Sonata', 10, 2018, 1, 30000),
    ('Kona', 10, 2020, 7, 14000),
    ('508', 11, 2021, 1, 8000),
    ('3008', 11, 2020, 7, 14000),
    ('5008', 11, 2019, 7, 18000),
    ('Cayenne', 12, 2020, 7, 15000),
    ('Macan', 12, 2019, 7, 18000),
    ('Panamera', 12, 2021, 4, 9000),
    ('Outlander', 13, 2019, 7, 20000),
    ('Pajero', 13, 2020, 7, 17000),
    ('Lancer', 13, 2018, 1, 25000),
    ('ASX', 13, 2021, 7, 12000),
    ('Forester', 14, 2020, 7, 18000),
    ('Impreza', 14, 2019, 1, 24000),
    ('Outback', 14, 2021, 7, 8000),
    ('Legacy', 14, 2020, 1, 15000),
    ('Rio', 15, 2021, 1, 12000),
    ('Sportage', 15, 2020, 7, 19000),
    ('Sorento', 15, 2019, 7, 22000),
    ('Soul', 15, 2021, 1, 9000),
    ('Seltos', 15, 2020, 7, 13000),
    ('CX-5', 16, 2019, 7, 22000),
    ('Mazda3', 16, 2021, 1, 8000),
    ('Mazda6', 16, 2020, 1, 13000),
    ('BT-50', 16, 2018, 8, 30000),
    ('CX-9', 16, 2019, 7, 25000),
    ('Model 3', 18, 2020, 1, 10000),
    ('Model S', 18, 2019, 1, 15000),
    ('Model X', 18, 2021, 7, 5000),
    ('Model Y', 18, 2020, 7, 8000),
    ('Defender', 19, 2021, 7, 5000),
    ('Range Rover', 19, 2020, 7, 12000),
    ('Discovery', 19, 2019, 7, 15000),
    ('Evoque', 19, 2021, 7, 6000),
    ('Atlas', 20, 2019, 7, 30000),
    ('Coolray', 20, 2020, 7, 17000),
    ('Emgrand', 20, 2018, 1, 40000),
    ('Tiggo', 21, 2019, 7, 23000),
    ('Arrizo', 21, 2021, 1, 9000),
    ('Tiggo 7', 21, 2020, 7, 15000),
    ('Clio', 17, 2021, 1, 8000),
    ('Megane', 17, 2020, 1, 14000),
    ('Kadjar', 17, 2019, 7, 18000),
    ('Koleos', 17, 2021, 7, 7000),
    ('Sandero', 17, 2020, 1, 15000),
    ('Duster', 17, 2019, 7, 20000)
    returning *;

-- DATA SELECTIONS ------------------------------------------------------------------------

select * from body;
select * from country;
select * from maker;
select * from car;
