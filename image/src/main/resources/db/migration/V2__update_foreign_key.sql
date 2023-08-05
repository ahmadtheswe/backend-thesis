ALTER TABLE "public"."image_history" ADD CONSTRAINT fk_image_history
    FOREIGN KEY (image_id) REFERENCES "public"."image" (id);