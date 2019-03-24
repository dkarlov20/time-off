create table if not exists position
(
  id   int auto_increment,
  name varchar(255) not null,
  primary key (id)
);

create table if not exists employee
(
  id          int auto_increment,
  position_id int,
  first_name  varchar(255) not null,
  last_name   varchar(255) not null,
  email       varchar(255) not null unique,
  primary key (id),
  foreign key (position_id) references position (id)
);

create table if not exists note
(
  id          int auto_increment,
  employee_id int,
  text        varchar(255) not null,
  primary key (id),
  foreign key (employee_id) references employee (id)
);

create table if not exists status
(
  id   int auto_increment,
  name varchar(255) not null,
  primary key (id)
);

create table if not exists type
(
  id   int auto_increment,
  name varchar(255) not null,
  primary key (id)
);

create table if not exists time_off_request
(
  id          int auto_increment,
  employee_id int,
  type_id     int,
  start       datetime not null,
  end         datetime not null,
  created     datetime not null,
  primary key (id),
  foreign key (employee_id) references employee (id),
  foreign key (type_id) references type (id),
);

create table if not exists time_off_request_status
(
  id                  int auto_increment,
  time_off_request_id int,
  status_id           int,
  employee_id         int,
  last_changed        datetime not null,
  primary key (id),
  foreign key (time_off_request_id) REFERENCES time_off_request (id),
  foreign key (status_id) REFERENCES status (id),
  foreign key (employee_id) REFERENCES employee (id)
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
