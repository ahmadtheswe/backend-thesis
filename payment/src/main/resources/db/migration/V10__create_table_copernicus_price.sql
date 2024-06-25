create table "public".copernicus_price
(
    id         varchar(20) primary key,
    name       varchar(200),
    price      numeric(10, 2) not null,
    updated_at timestamp
);

insert into "public".copernicus_price (id, name, price) values ('sentinel-1-grd','S1GRD', 20000);
insert into "public".copernicus_price (id, name, price) values ('sentinel-2-l1c', 'S2L1C', 20000);
insert into "public".copernicus_price (id, name, price) values ('sentinel-2-l2a', 'S2L2A', 20000);
insert into "public".copernicus_price (id, name, price) values ('sentinel-3-olci', 'S2OLCI', 20000);
insert into "public".copernicus_price (id, name, price) values ('sentinel-3-slstr', 'S3SLSTR', 20000);
insert into "public".copernicus_price (id, name, price) values ('sentinel-5p-l2', 'S5PL2', 20000);