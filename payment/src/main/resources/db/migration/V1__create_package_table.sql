drop table if exists "public"."package";

create table "public"."package"
(
    "id"           character varying(10) primary key,
    "package_name" character varying(10),
    "price"        integer,
    "is_active"    boolean
);

insert into "public"."package" (id, package_name, price, is_active)
values ('PRO', 'PRO', 70000, true),
       ('PREMIUM', 'PREMIUM', 100000, true);
