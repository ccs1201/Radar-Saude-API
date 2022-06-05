create sequence public.person_generator start 1 increment 50;

create table public.person (
                        id int8 not null,
                        birth_date date not null,
                        creation_date timestamp,
                        email varchar(50) not null,
                        excluded boolean not null,
                        gender varchar(6) not null,
                        last_update_date timestamp,
                        name varchar(200) not null,
                        phone_number varchar(11) not null,
                        primary key (id)
);

alter table person
    owner to postgres;
