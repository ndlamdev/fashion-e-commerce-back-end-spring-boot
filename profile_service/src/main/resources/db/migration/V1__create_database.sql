CREATE TABLE addresses
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    is_lock       BIT DEFAULT 0 NOT NULL,
    create_by     VARCHAR(255) NULL,
    create_at     datetime NULL,
    update_by     VARCHAR(255) NULL,
    update_at     datetime NULL,
    customer_id   BIGINT NULL,
    street        VARCHAR(255) NULL,
    ward          VARCHAR(255) NULL,
    ward_code     VARCHAR(255) NULL,
    district      VARCHAR(255) NULL,
    district_code VARCHAR(255) NULL,
    city          VARCHAR(255) NULL,
    city_code     VARCHAR(255) NULL,
    country       VARCHAR(255) NULL,
    active        BIT(1) NULL,
    CONSTRAINT pk_addresses PRIMARY KEY (id)
);

CREATE TABLE customers
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0 NOT NULL,
    create_by VARCHAR(255) NULL,
    create_at datetime NULL,
    update_by VARCHAR(255) NULL,
    update_at datetime NULL,
    full_name VARCHAR(255) NULL,
    email     VARCHAR(255) NULL,
    phone     VARCHAR(255) NULL,
    birthday  date NULL,
    height DOUBLE NULL,
    weight DOUBLE NULL,
    gender    VARCHAR(255) NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

ALTER TABLE customers
    ADD CONSTRAINT uc_customers_email UNIQUE (email);

ALTER TABLE addresses
    ADD CONSTRAINT FK_ADDRESSES_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);