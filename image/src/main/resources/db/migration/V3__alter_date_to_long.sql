ALTER TABLE "public"."image" DROP COLUMN created_at;
ALTER TABLE "public"."image" DROP COLUMN latest_access;

ALTER TABLE "public"."image_history" DROP COLUMN created_at;

ALTER TABLE "public"."image"
    ADD COLUMN created_at BIGINT,
    ADD COLUMN latest_access BIGINT;

ALTER TABLE "public"."image_history"
    ADD COLUMN created_at BIGINT;