create table "public"."preorder"
(
    "id"         character varying(36) primary key,
    "user_id"    character varying(36),
    "user_email" character varying(36),
    "price"      numeric,
    "is_paid"    boolean,
    "created_at" timestamptz,
    "paid_at"    timestamptz
);