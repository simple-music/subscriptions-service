CREATE TABLE IF NOT EXISTS "subscription" (
    "user" TEXT
        CONSTRAINT "subscription_user_not_null" NOT NULL,
    "subscriber" TEXT
        CONSTRAINT "subscription_subscriber_not_null" NOT NULL,
    CONSTRAINT "subscription_user_subscriber_pk" PRIMARY KEY("user","subscriber")
);

DO $$ BEGIN
    IF NOT EXISTS (SELECT * FROM "pg_type" WHERE "typname" = 'subscriptions_status') THEN
        CREATE TYPE "subscriptions_status" AS (
            "user" TEXT,
            "num_subscribers" BIGINT,
            "num_subscriptions" BIGINT
        );
    END IF;
END$$;

CREATE OR REPLACE FUNCTION get_subscriptions_status(_user_ TEXT)
    RETURNS SETOF "subscriptions_status"
AS $$
    DECLARE _subscriptions_status_ "subscriptions_status";
BEGIN
    SELECT
        _user_,
        (SELECT count(*) FROM "subscription" WHERE "user" = _user_),
        (SELECT count(*) FROM "subscription" WHERE "subscriber" = _user_)
    INTO
        _subscriptions_status_;

    IF _subscriptions_status_.num_subscribers != 0 OR _subscriptions_status_.num_subscriptions != 0 THEN
        RETURN NEXT _subscriptions_status_;
    END IF;
END;
$$ LANGUAGE PLPGSQL;
