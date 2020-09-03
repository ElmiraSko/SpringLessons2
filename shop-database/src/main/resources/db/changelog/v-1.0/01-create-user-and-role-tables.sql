create table roles (
   id bigint not null auto_increment,
    name varchar(25) not null,
    primary key (id)
) engine=InnoDB;
GO

create table users (
   id bigint not null auto_increment,
    age integer not null,
    email varchar(64) not null,
    name varchar(32) not null,
    password varchar(128) not null,
    phone varchar(12),
    primary key (id)
) engine=InnoDB;
GO

create table users_roles (
   user_id bigint not null,
    role_id bigint not null
) engine=InnoDB;
GO

alter table roles
   add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name);
GO

alter table users_roles
   add constraint FKj6m8fwv7oqv74fcehir1a9ffy
   foreign key (role_id)
   references roles (id);
GO

alter table users_roles
   add constraint FK2o0jvgh89lemvvo17cbqvdxaa
   foreign key (user_id)
   references users (id);
GO