CREATE TABLE cart_items
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0         NOT NULL,
    create_by  VARCHAR(255)          NULL,
    create_at  datetime              NULL,
    update_by  VARCHAR(255)          NULL,
    update_at  datetime              NULL,
    variant_id VARCHAR(255)          NULL,
    product_id VARCHAR(255)          NULL,
    quantity   INT DEFAULT 1         NOT NULL,
    cart_id    BIGINT                NULL,
    CONSTRAINT pk_cart_items PRIMARY KEY (id)
);

CREATE TABLE carts
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    user_id   BIGINT                NULL,
    CONSTRAINT pk_carts PRIMARY KEY (id)
);

ALTER TABLE cart_items
    ADD CONSTRAINT uc_899d35e0503cdfaee872ae679 UNIQUE (variant_id, cart_id);

ALTER TABLE carts
    ADD CONSTRAINT uc_carts_userid UNIQUE (user_id);

ALTER TABLE cart_items
    ADD CONSTRAINT FK_CART_ITEMS_ON_CART FOREIGN KEY (cart_id) REFERENCES carts (id);