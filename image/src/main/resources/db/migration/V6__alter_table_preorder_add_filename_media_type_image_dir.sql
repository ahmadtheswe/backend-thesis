alter table "public".preorder
    add column "filename" character varying(100);
alter table "public".preorder
    add column "media_type" character varying(6);
alter table "public".preorder
    add column "original_image_dir" text;