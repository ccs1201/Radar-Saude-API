create table person
(
    id               bigint                not null
        primary key,
    birth_date       date                  not null,
    creation_date    timestamp,
    email            varchar(50)           not null,
    gender           varchar(6)            not null,
    last_update_date timestamp,
    name             varchar(200)          not null,
    phone_number     varchar(11)           not null,
    excluded         boolean default false not null
);

alter table person
    owner to postgres;
