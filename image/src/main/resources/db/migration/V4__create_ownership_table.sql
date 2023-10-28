CREATE TABLE "public"."ownership"
(
    "id"          character varying(36) PRIMARY KEY,
    "image_id"    character varying(36) NOT NULL,
    "owner_id" character varying(36) NOT NULL,

    constraint fk_image_history
        foreign key (image_id) references "public"."image" (id)
);