CREATE TABLE customers
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    is_lock       BIT DEFAULT 0 NOT NULL,
    create_by     VARCHAR(255) NULL,
    create_at     datetime NULL,
    update_by     VARCHAR(255) NULL,
    update_at     datetime NULL,
    full_name VARCHAR(255) NOT NULL,
    email       VARCHAR(255) UNIQUE NULL,
    phone       VARCHAR(255) NOT NULL,
    birthday      datetime NULL,
    height       INT NULL,
    weight       INT NULL,
    gender       ENUM('MALE', 'FEMALE', 'OTHER') NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

CREATE TABLE addresses
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0 NOT NULL,
    create_by  VARCHAR(255) NULL,
    create_at  datetime NULL,
    update_by  VARCHAR(255) NULL,
    update_at  datetime NULL,
    customer_id BIGINT NOT NULL,
    street       VARCHAR(255) NOT NULL,
    ward       VARCHAR(255) NOT NULL,
    district       VARCHAR(255) NOT NULL,
    city       VARCHAR(255) NOT NULL,
    country       VARCHAR(255) NOT NULL,
    active       BIT NOT NULL,
    wardCode       VARCHAR(255) NOT NULL,
    districtCode       VARCHAR(255) NOT NULL,
    cityCode VARCHAR(255) NOT NULL,
    CONSTRAINT pk_addresses PRIMARY KEY (id),
    CONSTRAINT FK_CUSTOMER_ADDRESS FOREIGN KEY (customer_id) REFERENCES customers (id)
);
