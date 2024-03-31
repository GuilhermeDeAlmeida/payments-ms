create table IF NOT EXISTS user(
    id bigint not null auto_increment,
    name varchar(50) not null,
    username varchar(50) not null,
    email varchar(50) not null,
    password varchar(200) not null,
    primary key(id)
);

insert into user values(1, 'Ana da Silva','ana_da_silva', 'ana@email.com', '$2a$12$CcQYpo7JyJAjquB9CwZtTeZeE4RvRDif.TtgVgGk0UtZyBJZDUFU6');