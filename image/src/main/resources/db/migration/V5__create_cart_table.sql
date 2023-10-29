drop table if exists user_cart;
create table "public".user_cart
(
    "id"         character varying(36) PRIMARY KEY,
    "image_id"   character varying(36) NOT NULL,
    "user_id"    character varying(36) NOT NULL,
    "created_at" timestamptz DEFAULT current_timestamp,
    "is_active"  boolean     default true,

    constraint fk_image_history
        foreign key (image_id) references "public"."image" (id)
            on delete no action
);

