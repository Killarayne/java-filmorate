DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS mpa_rating CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS film_genres CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_friends CASCADE;
DROP TABLE IF EXISTS film_likes CASCADE;


create table if not exists mpa_rating
(
    mpa_id int
    primary key,
    mpa_name   varchar
);

create table if not exists films
(
    film_id       int auto_increment
    primary key,
    name          varchar,
    description   varchar,
    releaseDate date,
    duration      int,
    mpa_id        int,
    constraint FILM__FK
    foreign key (mpa_id) references MPA_RATING
    );


create table if not exists genres
(
    genre_id int
    primary key,
    name     varchar
);

create table if not exists film_genres
(
    film_id  int,
    genre_id int,
    constraint FILM_GENRES_FILMS_FILM_ID_FK
    foreign key (film_id) references FILMS,
    constraint FILM_GENRES_GENRES_GENRE_ID_FK
    foreign key (genre_id) references GENRES
    );

create table if not exists users
(
    user_id  int auto_increment
    primary key,
    name     varchar,
    email    varchar,
    login    varchar,
    birthday date
);

create table if not exists film_likes
(
    user_id int,
    film_id int,
    constraint FILM_LIKES_FILMS_FILM_ID_FK
    foreign key (film_id) references FILMS,
    constraint FILM_LIKES_USERS_USER_ID_FK
    foreign key (user_id) references USERS
    );

create table if not exists user_friends
(
    user_id   int,
    friend_id int,
    status boolean,
    constraint USER_FRIENDS_USERS_USER_ID_FK
    foreign key (user_id) references USERS,
    constraint USER_FRIENDS_USERS_USER_ID_FK_2
    foreign key (friend_id) references USERS
    );











