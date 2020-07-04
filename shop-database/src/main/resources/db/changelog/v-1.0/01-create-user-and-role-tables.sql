create table products (
   id bigint not null auto_increment,
    cost decimal(19,2) not null,
    title varchar(32) not null,
    user_id bigint,
    category_id bigint,
    primary key (id)
) engine=InnoDB;
GO

create table categories (
   id bigint not null auto_increment,
    title varchar(32) not null,
    primary key (id)
) engine=InnoDB;
GO

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


alter table categories
   add constraint UK_ofx67 unique (title);
GO

alter table roles
   add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name);
GO

alter table products
   add constraint FKdb050tk37qryv15hd932626th
   foreign key (user_id)
   references users (id);
GO

alter table products
   add constraint FKdb050tk37
   foreign key (category_id)
   references categories (id);
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