ALTER TABLE "public"."preorder" RENAME COLUMN "top_left_latitude" TO "max_latitude";
ALTER TABLE "public"."preorder" RENAME COLUMN "top_left_longitude" TO "max_longitude";
ALTER TABLE "public"."preorder" RENAME COLUMN "top_right_latitude" TO "min_latitude";
ALTER TABLE "public"."preorder" RENAME COLUMN "top_right_longitude" TO "min_longitude";
ALTER TABLE "public"."preorder" DROP COLUMN "bottom_right_latitude";
ALTER TABLE "public"."preorder" DROP COLUMN "bottom_right_longitude";
ALTER TABLE "public"."preorder" DROP COLUMN "bottom_left_latitude";
ALTER TABLE "public"."preorder" DROP COLUMN "bottom_left_longitude";