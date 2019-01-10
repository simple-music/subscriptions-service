CREATE TABLE IF NOT EXISTS "subscription" (
    "user" TEXT
        CONSTRAINT "subscription_user_not_null" NOT NULL,
    "subscriber" TEXT
        CONSTRAINT "subscription_subscriber_not_null" NOT NULL,
    CONSTRAINT "subscription_user_subscriber_pk" PRIMARY KEY("user","subscriber")
);

CREATE TABLE IF NOT EXISTS "subscribers_summary" (
    "user" TEXT
        CONSTRAINT "subscribers_summary_user_pk" PRIMARY KEY,
    "num_subscribers" BIGINT
        CONSTRAINT "subscribers_summary_num_subscribers_not_null" NOT NULL
);

CREATE TABLE IF NOT EXISTS "subscriptions_summary" (
    "user" TEXT
        CONSTRAINT "subscriptions_summary_user_pk" PRIMARY KEY,
    "num_subscriptions" BIGINT
        CONSTRAINT "subscriptions_summary_num_subscriptions_not_null" NOT NULL
);

CREATE OR REPLACE FUNCTION subscription_insert_trigger_function()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO "subscribers_summary"("user","num_subscribers")
    VALUES (NEW."user",1) ON CONFLICT("user") DO UPDATE SET
        "num_subscribers" = "subscribers_summary"."num_subscribers" + 1;
    INSERT INTO "subscriptions_summary"("user","num_subscriptions")
    VALUES (NEW."subscriber",1) ON CONFLICT("user") DO UPDATE SET
        "num_subscriptions" = "subscriptions_summary"."num_subscriptions" + 1;
    RETURN NEW;
END;
$$ LANGUAGE PLPGSQL;

DROP TRIGGER IF EXISTS "subscription_insert_trigger" ON "subscription";

CREATE TRIGGER "subscription_insert_trigger"
    AFTER INSERT ON "subscription"
    FOR EACH ROW
EXECUTE PROCEDURE subscription_insert_trigger_function();
