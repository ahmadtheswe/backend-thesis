CREATE TABLE "public"."package"
(
    "id"           character varying(36) primary key,
    "package_name" character varying(40),
    "price"        integer,
    is_active      boolean
);