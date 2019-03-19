create table if not exists employee
(
  id   int auto_increment,
  name varchar(255) not null,
  primary key (id)
);

create table if not exists user
(
  id          int auto_increment,
  employee_id int,
  first_name  varchar(255) not null,
  last_name   varchar(255) not null,
  email       varchar(255) not null unique,
  last_login  datetime     not null,
  logged_in   boolean      not null,
  primary key (id),
  foreign key (employee_id) references employee (id)
);

create table if not exists note
(
  id     int auto_increment,
  "from" varchar(255) not null,
  text   varchar(255) not null,
  primary key (id)
);

create table if not exists time_off_date
(
  id     int auto_increment,
  ymd    datetime not null,
  amount int      not null,
  primary key (id)
);

create table if not exists amount
(
  id     int auto_increment,
  unit   varchar(255) not null,
  number int          not null,
  primary key (id)
);

create table if not exists status
(
  id           int auto_increment,
  user_id      int,
  last_changed datetime     not null,
  name         varchar(255) not null,
  primary key (id),
  foreign key (user_id) REFERENCES user (id)
);

create table if not exists type
(
  id   int auto_increment,
  icon varchar(255) not null,
  name varchar(255) not null,
  primary key (id)
);

create table if not exists time_off_request
(
  id          int auto_increment,
  employee_id int,
  status_id   int,
  type_id     int,
  amount_id   int,
  start       datetime not null,
  end         datetime not null,
  created     datetime not null,
  primary key (id),
  foreign key (employee_id) references employee (id),
  foreign key (status_id) references status (id),
  foreign key (type_id) references type (id),
  foreign key (amount_id) references amount (id)
);

create table if not exists time_off_request_note
(
  id                  int auto_increment,
  time_off_request_id int,
  note_id             int,
  primary key (id),
  foreign key (time_off_request_id) references time_off_request (id),
  foreign key (note_id) references note (id),
);

create table if not exists time_off_request_time_off_date
(
  id                  int auto_increment,
  time_off_request_id int,
  time_off_date_id    int,
  primary key (id),
  foreign key (time_off_request_id) references time_off_request (id),
  foreign key (time_off_date_id) references time_off_date (id),
);
