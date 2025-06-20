CREATE TABLE payments
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0 NOT NULL,
    create_by  VARCHAR(255) NULL,
    create_at  datetime NULL,
    update_by  VARCHAR(255) NULL,
    update_at  datetime NULL,
    order_id   BIGINT        NOT NULL,
    order_code BIGINT        NOT NULL,
    status     SMALLINT NULL,
    method     SMALLINT NULL,
    CONSTRAINT pk_payments PRIMARY KEY (id)
);