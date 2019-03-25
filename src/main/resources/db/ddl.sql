create table if not exists employee_position
(
  id       int auto_increment,
  position varchar(255) not null,
  primary key (id)
);

create table if not exists employee
(
  id                   int auto_increment,
  employee_position_id int,
  first_name           varchar(255) not null,
  last_name            varchar(255) not null,
  email                varchar(255) not null unique,
  primary key (id),
  foreign key (employee_position_id) references employee_position (id)
);

create table if not exists note
(
  id          int auto_increment,
  employee_id int,
  text        varchar(255) not null,
  primary key (id),
  foreign key (employee_id) references employee (id)
);

create table if not exists request_status
(
  id     int auto_increment,
  status varchar(255) not null,
  primary key (id)
);

create table if not exists request_type
(
  id   int auto_increment,
  type varchar(255) not null,
  primary key (id)
);

create table if not exists time_off_request
(
  id              int auto_increment,
  employee_id     int,
  request_type_id int,
  start           date not null,
  end             date not null,
  created         date not null,
  primary key (id),
  foreign key (employee_id) references employee (id),
  foreign key (request_type_id) references request_type (id),
);

create table if not exists time_off_request_status
(
  id                  int auto_increment,
  time_off_request_id int,
  request_status_id   int,
  employee_id         int,
  last_changed        datetime not null,
  primary key (id),
  foreign key (time_off_request_id) REFERENCES time_off_request (id),
  foreign key (request_status_id) REFERENCES request_status (id),
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
