CREATE TABLE order_items
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0 NOT NULL,
    is_delete  BIT DEFAULT 0 NOT NULL,
    create_by  VARCHAR(255) NULL,
    create_at  datetime NULL,
    update_by  VARCHAR(255) NULL,
    update_at  datetime NULL,
    product_id VARCHAR(255) NULL,
    variant_id VARCHAR(255) NULL,
    quantity   INT           NOT NULL,
    compare_price DOUBLE NOT NULL,
    regular_price DOUBLE NOT NULL,
    order_id   BIGINT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    is_lock        BIT DEFAULT 0 NOT NULL,
    is_delete      BIT DEFAULT 0 NOT NULL,
    create_by      VARCHAR(255) NULL,
    create_at      datetime NULL,
    update_by      VARCHAR(255) NULL,
    update_at      datetime NULL,
    user_id        BIGINT        NOT NULL,
    name           VARCHAR(255) NULL,
    phone          VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    address_detail VARCHAR(255) NULL,
    ward           VARCHAR(255) NULL,
    district       VARCHAR(255) NULL,
    province       VARCHAR(255) NULL,
    note           VARCHAR(255) NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE statuses
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0 NOT NULL,
    is_delete BIT DEFAULT 0 NOT NULL,
    create_by VARCHAR(255) NULL,
    create_at datetime NULL,
    update_by VARCHAR(255) NULL,
    update_at datetime NULL,
    status    SMALLINT NULL,
    note      VARCHAR(255) NULL,
    order_id  BIGINT NULL,
    CONSTRAINT pk_statuses PRIMARY KEY (id)
);

ALTER TABLE order_items
    ADD CONSTRAINT FK_ORDER_ITEMS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE statuses
    ADD CONSTRAINT FK_STATUSES_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);