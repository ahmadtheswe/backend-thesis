CREATE TABLE "public"."userinfo"
(
    "id"         character varying(36) primary key,
    "username"   character varying(40) not null,
    "email"      character varying(100) not null,
    "role"       character varying(40),
    "created_at" timestamp(0),
    is_active    boolean
);
