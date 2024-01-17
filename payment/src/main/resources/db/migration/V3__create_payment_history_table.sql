create table "public"."payment_history"
(
    "id"         character varying(36) primary key,
    "user_id"    character varying(36),
    "package_id" character varying(36),
    "pay_date"   timestamptz
)