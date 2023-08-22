ALTER TABLE "public"."image"
    ALTER COLUMN created_at TYPE timestamptz USING created_at AT TIME ZONE 'UTC+7',
    ALTER COLUMN created_at SET DEFAULT now();

ALTER TABLE "public"."image"
    ALTER COLUMN latest_access TYPE timestamptz USING created_at AT TIME ZONE 'UTC+7';

ALTER TABLE "public"."image_history"
    ALTER COLUMN created_at TYPE timestamptz USING created_at AT TIME ZONE 'UTC+7',
    ALTER COLUMN created_at SET DEFAULT now();

ALTER TABLE "public"."image_history" ALTER COLUMN activity TYPE varchar(15);