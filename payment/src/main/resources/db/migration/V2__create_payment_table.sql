CREATE TABLE "public"."payment"
(
    "id"             character varying(36) primary key,
    "user_id"        character varying(36),
    "email"          character varying(100),
    "package_id"     character varying(36),
    "payment_status" character varying(10),
    "pay_date"       timestamptz,
    "valid_date"     timestamptz,
    constraint fk_package foreign key ("package_id") references package ("id")
);