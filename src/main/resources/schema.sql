create table mpa_ratings
(
    id   serial
        constraint mpa_ratings_pk
            primary key,
    name varchar not null
);

comment on table mpa_ratings is 'Возрастной рейтинг';

comment on column mpa_ratings.id is 'Идентификатор';

comment on column mpa_ratings.name is 'Название';

alter table mpa_ratings
    owner to "user";

create unique index mpa_ratings_id_uindex
    on mpa_ratings (id);

create unique index mpa_ratings_name_uindex
    on mpa_ratings (name);

create table genres
(
    id   serial
        constraint genres_pk
            primary key,
    name varchar not null
);

comment on table genres is 'Жанры';

comment on column genres.id is 'Идентификатор';

comment on column genres.name is 'Название';

alter table genres
    owner to "user";

create unique index genres_id_uindex
    on genres (id);

create unique index genres_name_uindex
    on genres (name);

create table films
(
    id           serial
        constraint films_pk
            primary key,
    name         varchar not null,
    description  varchar(200),
    release_date date    not null,
    duration     integer not null,
    rating       integer
        constraint films_mpa_ratings_id_fk
            references mpa_ratings
);

comment on table films is 'Фильмы';

comment on column films.id is 'Идентификатор';

comment on column films.name is 'Название';

comment on column films.description is 'Описание';

comment on column films.release_date is 'Дата релиза';

comment on column films.duration is 'Длительность';

comment on column films.rating is 'Рейтинг';

alter table films
    owner to "user";

create unique index films_id_uindex
    on films (id);

create table films_genres
(
    film_id  integer not null
        constraint films_genres_films_id_fk
            references films
            on update restrict on delete restrict,
    genre_id integer not null
        constraint films_genres_genres_id_fk
            references genres
            on update restrict on delete restrict,
    constraint films_genres_pk
        primary key (film_id, genre_id)
);

comment on column films_genres.film_id is 'Идентификатор фильма';

comment on column films_genres.genre_id is 'Идентификатор жанра';

alter table films_genres
    owner to "user";

create table users
(
    id       serial
        constraint users_pk
            primary key,
    email    varchar not null,
    login    varchar not null,
    name     varchar not null,
    birthday date    not null,
    friends  integer
);

comment on table users is 'Пользователи';

comment on column users.id is 'Идентификатор';

comment on column users.email is 'Email';

comment on column users.login is 'Логин';

comment on column users.name is 'Имя';

comment on column users.birthday is 'Дата рождения';

comment on column users.friends is 'Друзья';

alter table users
    owner to "user";

create unique index users_id_uindex
    on users (id);

create unique index users_login_uindex
    on users (login);

create table friends
(
    user_id   integer not null
        constraint friends_users_id_fk
            references users,
    friend_id integer not null
        constraint friends_users_id_fk_2
            references users,
    constraint friends_pk
        primary key (user_id, friend_id)
);

comment on column friends.user_id is 'Идентификатор пользователя';

comment on column friends.friend_id is 'Идентификатор друга';

alter table friends
    owner to "user";

create table likes
(
    film_id integer not null
        constraint likes_films_id_fk
            references films,
    user_id integer not null
        constraint likes_users_id_fk
            references users,
    constraint likes_pk
        primary key (film_id, user_id)
);

comment on table likes is 'Лайки';

comment on column likes.film_id is 'Идентификатор фильма';

comment on column likes.user_id is 'Идентификатор пользователя';

alter table likes
    owner to "user";

