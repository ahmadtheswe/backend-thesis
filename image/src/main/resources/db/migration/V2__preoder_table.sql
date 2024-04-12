CREATE TABLE "public"."preorder"
(
    "id"                 character varying(36) PRIMARY KEY,
    "requester_id"       character varying(36) NOT NULL,
    "center_latitude"    numeric(9, 6),
    "center_longitude"   numeric(9, 6),
    "top_left_latitude"  numeric(9, 6),
    "top_left_longitude" numeric(9, 6),
    "top_right_latitude"  numeric(9, 6),
    "top_right_longitude" numeric(9, 6),
    "bottom_right_latitude"  numeric(9, 6),
    "bottom_right_longitude" numeric(9, 6),
    "bottom_left_latitude"  numeric(9, 6),
    "bottom_left_longitude" numeric(9, 6),
    is_active bool default true,
    created_at bigint,
    delivered_at bigint
);