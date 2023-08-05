ALTER TABLE "public"."image" ADD COLUMN filename VARCHAR(100);
ALTER TABLE "public"."image" ADD COLUMN media_type VARCHAR(10);
ALTER TABLE "public"."image" ADD COLUMN is_public BOOLEAN;