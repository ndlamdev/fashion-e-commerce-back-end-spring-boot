CREATE TABLE addresses
(
    id            BIGINT AUTO_INCREMENT  NOT NULL,
    is_lock       BIT      DEFAULT 0     NOT NULL,
    create_by     VARCHAR(255)           NULL,
    create_at     datetime DEFAULT NOW() NULL,
    update_by     VARCHAR(255)           NULL,
    update_at     datetime               NULL,
    customer_id   BIGINT                 NULL,
    full_name     VARCHAR(255)           NULL,
    phone         VARCHAR(255)           NULL,
    street        VARCHAR(255)           NULL,
    ward          VARCHAR(255)           NOT NULL,
    ward_code     VARCHAR(255)           NOT NULL,
    district      VARCHAR(255)           NOT NULL,
    district_code VARCHAR(255)           NOT NULL,
    city          VARCHAR(255)           NOT NULL,
    city_code     VARCHAR(255)           NOT NULL,
    country_code  VARCHAR(255)           NOT NULL,
    active        BIT(1)                 NOT NULL,
    CONSTRAINT pk_addresses PRIMARY KEY (id)
);

CREATE TABLE customers
(
    id           BIGINT AUTO_INCREMENT  NOT NULL,
    is_lock      BIT      DEFAULT 0     NOT NULL,
    create_by    VARCHAR(255)           NULL,
    create_at    datetime DEFAULT NOW() NULL,
    update_by    VARCHAR(255)           NULL,
    update_at    datetime               NULL,
    user_id      BIGINT                 NOT NULL,
    full_name    VARCHAR(255)           NOT NULL,
    email        VARCHAR(255)           NULL,
    phone        VARCHAR(255)           NOT NULL,
    avatar       VARCHAR(255)           NULL,
    country_code VARCHAR(255)           NOT NULL,
    birthday     date                   NULL,
    height       DOUBLE                 NULL,
    weight       DOUBLE                 NULL,
    gender       VARCHAR(255)           NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

ALTER TABLE customers
    ADD CONSTRAINT uc_customers_email UNIQUE (email);

ALTER TABLE addresses
    ADD CONSTRAINT FK_ADDRESSES_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);