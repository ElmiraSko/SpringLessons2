create table categories (
   id bigint not null auto_increment,
    title varchar(32) not null,
    primary key (id)
) engine=InnoDB;
GO

alter table categories
   add constraint UK_ofx67 unique (title);
GO

create table products (
   id bigint not null auto_increment,
    cost decimal(19,2) not null,
    title varchar(32) not null,
    category_id bigint,
    primary key (id)
) engine=InnoDB;
GO

alter table products
   add constraint FKdb05hy88hgf0tk37
   foreign key (category_id)
   references categories (id);
GO