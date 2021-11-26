CREATE TABLE IF NOT EXISTS currency_type
(
    currency_type_id BIGSERIAL   NOT NULL PRIMARY KEY,
    type             VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS currency
(
    currency_id      BIGSERIAL   NOT NULL PRIMARY KEY,
    code             VARCHAR(40) NOT NULL,
    name             VARCHAR(40) NOT NULL,
    currency_type_id BIGINT      NOT NULL,
    FOREIGN KEY (currency_type_id)
        REFERENCES currency_type (currency_type_id)
);

CREATE TABLE IF NOT EXISTS transaction_type
(
    transaction_type_id BIGSERIAL   NOT NULL PRIMARY KEY,
    type                VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS "transaction"
(
    transaction_id      BIGSERIAL   NOT NULL PRIMARY KEY,
    date                VARCHAR(40) NOT NULL,
    amount              VARCHAR(40) NOT NULL,
    value               VARCHAR(40) NOT NULL,
    price               VARCHAR(40) NOT NULL,
    currency_id         BIGINT      NOT NULL,
    transaction_type_id BIGINT      NOT NULL,
    FOREIGN KEY (currency_id)
        REFERENCES currency (currency_id),
    FOREIGN KEY (transaction_type_id)
        REFERENCES transaction_type (transaction_type_id)
);



