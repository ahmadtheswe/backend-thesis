DROP TABLE IF EXISTS "image";
DROP TABLE IF EXISTS "image_history";

CREATE TABLE "public"."image"
(
    "id"                 character varying(36) PRIMARY KEY,
    "uploader_id"        character varying(36) NOT NULL,
    "title"              character varying(40),
    "format"             character varying(6),
    "original_image_dir" text,
    "created_at"         timestamp(0),
    "latest_access"      timestamp(0)
);

CREATE TABLE "public"."image_history"
(
    "id"          character varying(36) PRIMARY KEY,
    "image_id"    character varying(36) NOT NULL,
    "accessor_id" character varying(36) NOT NULL,
    "activity"    character varying(10),
    "created_at"  timestamp(0)
);