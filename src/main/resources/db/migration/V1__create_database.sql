CREATE TABLE bill_status
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    location  VARCHAR(255)          NULL,
    note      VARCHAR(255)          NULL,
    status    VARCHAR(255)          NULL,
    bill_id   BIGINT                NULL,
    CONSTRAINT pk_bill_status PRIMARY KEY (id)
);

CREATE TABLE bills
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    is_lock       BIT DEFAULT 0         NOT NULL,
    create_by     VARCHAR(255)          NULL,
    create_at     datetime              NULL,
    update_by     VARCHAR(255)          NULL,
    update_at     datetime              NULL,
    full_name     VARCHAR(255)          NULL,
    location      VARCHAR(255)          NULL,
    name_location VARCHAR(255)          NULL,
    email         VARCHAR(255)          NULL,
    phone         VARCHAR(255)          NULL,
    pay_type      VARCHAR(255)          NULL,
    user_id       BIGINT                NULL,
    CONSTRAINT pk_bills PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    name      VARCHAR(255)          NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE colors
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0         NOT NULL,
    create_by  VARCHAR(255)          NULL,
    create_at  datetime              NULL,
    update_by  VARCHAR(255)          NULL,
    update_at  datetime              NULL,
    name       VARCHAR(255)          NULL,
    color_code VARCHAR(255)          NULL,
    CONSTRAINT pk_colors PRIMARY KEY (id)
);

CREATE TABLE material_of_product
(
    material_id BIGINT NOT NULL,
    product_id  BIGINT NOT NULL
);

CREATE TABLE permission_of_role
(
    permission_id BIGINT NOT NULL,
    role_id       BIGINT NOT NULL
);

CREATE TABLE permissions
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0         NOT NULL,
    create_by  VARCHAR(255)          NULL,
    create_at  datetime              NULL,
    update_by  VARCHAR(255)          NULL,
    update_at  datetime              NULL,
    name       VARCHAR(255)          NULL,
    `describe` VARCHAR(255)          NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

CREATE TABLE product_details
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    is_lock           BIT DEFAULT 0         NOT NULL,
    create_by         VARCHAR(255)          NULL,
    create_at         datetime              NULL,
    update_by         VARCHAR(255)          NULL,
    update_at         datetime              NULL,
    price             DOUBLE                NOT NULL,
    quantity          INT                   NOT NULL,
    promotion         DOUBLE                NOT NULL,
    product_option_id BIGINT                NULL,
    bill_id           BIGINT                NULL,
    CONSTRAINT pk_product_details PRIMARY KEY (id)
);

CREATE TABLE product_images
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0         NOT NULL,
    create_by  VARCHAR(255)          NULL,
    create_at  datetime              NULL,
    update_by  VARCHAR(255)          NULL,
    update_at  datetime              NULL,
    url        VARCHAR(255)          NULL,
    is_default BIT(1)                NOT NULL,
    product_id BIGINT                NULL,
    CONSTRAINT pk_product_images PRIMARY KEY (id)
);

CREATE TABLE product_materials
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    name      VARCHAR(255)          NULL,
    CONSTRAINT pk_product_materials PRIMARY KEY (id)
);

CREATE TABLE product_options
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    is_lock      BIT DEFAULT 0         NOT NULL,
    create_by    VARCHAR(255)          NULL,
    create_at    datetime              NULL,
    update_by    VARCHAR(255)          NULL,
    update_at    datetime              NULL,
    price        DOUBLE                NOT NULL,
    is_default   BIT(1)                NOT NULL,
    color_id     BIGINT                NULL,
    size_id      BIGINT                NULL,
    product_id   BIGINT                NULL,
    promotion_id BIGINT                NULL,
    CONSTRAINT pk_product_options PRIMARY KEY (id)
);

CREATE TABLE product_types
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    name      VARCHAR(255)          NULL,
    CONSTRAINT pk_product_types PRIMARY KEY (id)
);

CREATE TABLE products
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    is_lock     BIT DEFAULT 0         NOT NULL,
    create_by   VARCHAR(255)          NULL,
    create_at   datetime              NULL,
    update_by   VARCHAR(255)          NULL,
    update_at   datetime              NULL,
    name        VARCHAR(255)          NULL,
    brand       VARCHAR(255)          NULL,
    `describe`  VARCHAR(255)          NULL,
    category_id BIGINT                NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE promotions
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    name      VARCHAR(255)          NULL,
    percent   INT                   NOT NULL,
    start     datetime              NULL,
    end       datetime              NULL,
    CONSTRAINT pk_promotions PRIMARY KEY (id)
);

CREATE TABLE reviews
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0         NOT NULL,
    create_by  VARCHAR(255)          NULL,
    create_at  datetime              NULL,
    update_by  VARCHAR(255)          NULL,
    update_at  datetime              NULL,
    content    VARCHAR(255)          NULL,
    star       DOUBLE                NOT NULL,
    product_id BIGINT                NULL,
    user_id    BIGINT                NULL,
    CONSTRAINT pk_reviews PRIMARY KEY (id)
);

CREATE TABLE role_of_user
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    user_id   BIGINT                NULL,
    role_id   BIGINT                NULL,
    CONSTRAINT pk_role_of_user PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    name      VARCHAR(255)          NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE sizes
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    is_lock     BIT DEFAULT 0         NOT NULL,
    create_by   VARCHAR(255)          NULL,
    create_at   datetime              NULL,
    update_by   VARCHAR(255)          NULL,
    update_at   datetime              NULL,
    name        VARCHAR(255)          NULL,
    size_number INT                   NOT NULL,
    CONSTRAINT pk_sizes PRIMARY KEY (id)
);

CREATE TABLE type_of_product
(
    product_id BIGINT NOT NULL,
    type_id    BIGINT NOT NULL
);

CREATE TABLE user_address
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    email     VARCHAR(255)          NULL,
    phone     VARCHAR(255)          NULL,
    location  VARCHAR(255)          NULL,
    user_id   BIGINT                NULL,
    CONSTRAINT pk_user_address PRIMARY KEY (id)
);

CREATE TABLE user_details
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    full_name VARCHAR(255)          NULL,
    phone     VARCHAR(255)          NULL,
    sex       VARCHAR(255)          NULL,
    avatar    VARCHAR(255)          NULL,
    birthday  date                  NULL,
    user_id   BIGINT                NULL,
    CONSTRAINT pk_user_details PRIMARY KEY (id)
);

CREATE TABLE users
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    email     VARCHAR(255)          NULL,
    password  VARCHAR(255)          NULL,
    active    BIT DEFAULT 0         NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE voucher_of_category
(
    category_id BIGINT NOT NULL,
    voucher_id  BIGINT NOT NULL
);

CREATE TABLE voucher_of_user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0         NOT NULL,
    create_by  VARCHAR(255)          NULL,
    create_at  datetime              NULL,
    update_by  VARCHAR(255)          NULL,
    update_at  datetime              NULL,
    voucher_id BIGINT                NULL,
    user_id    BIGINT                NULL,
    used       BIT(1)                NOT NULL,
    CONSTRAINT pk_voucher_of_user PRIMARY KEY (id)
);

CREATE TABLE vouchers
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0         NOT NULL,
    create_by VARCHAR(255)          NULL,
    create_at datetime              NULL,
    update_by VARCHAR(255)          NULL,
    update_at datetime              NULL,
    name      VARCHAR(255)          NULL,
    percent   INT                   NOT NULL,
    quantity  INT                   NOT NULL,
    start     datetime              NULL,
    end       datetime              NULL,
    CONSTRAINT pk_vouchers PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_74165e195b2f7b25de690d14a UNIQUE (email);

ALTER TABLE role_of_user
    ADD CONSTRAINT uc_b8bba79049d2724e7e3b0d1f1 UNIQUE (user_id, role_id);

ALTER TABLE permissions
    ADD CONSTRAINT uc_e7409dbdcee9059862a6222dc UNIQUE (name);

ALTER TABLE roles
    ADD CONSTRAINT uc_fd322f7161fc2fbfed95c3840 UNIQUE (name);

ALTER TABLE user_details
    ADD CONSTRAINT uc_user_details_user UNIQUE (user_id);

ALTER TABLE bills
    ADD CONSTRAINT FK_BILLS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE bill_status
    ADD CONSTRAINT FK_BILL_STATUS_ON_BILL FOREIGN KEY (bill_id) REFERENCES bills (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE product_details
    ADD CONSTRAINT FK_PRODUCT_DETAILS_ON_BILL FOREIGN KEY (bill_id) REFERENCES bills (id);

ALTER TABLE product_details
    ADD CONSTRAINT FK_PRODUCT_DETAILS_ON_PRODUCT_OPTION FOREIGN KEY (product_option_id) REFERENCES product_options (id);

ALTER TABLE product_images
    ADD CONSTRAINT FK_PRODUCT_IMAGES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE product_options
    ADD CONSTRAINT FK_PRODUCT_OPTIONS_ON_COLOR FOREIGN KEY (color_id) REFERENCES colors (id);

ALTER TABLE product_options
    ADD CONSTRAINT FK_PRODUCT_OPTIONS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE product_options
    ADD CONSTRAINT FK_PRODUCT_OPTIONS_ON_PROMOTION FOREIGN KEY (promotion_id) REFERENCES promotions (id);

ALTER TABLE product_options
    ADD CONSTRAINT FK_PRODUCT_OPTIONS_ON_SIZE FOREIGN KEY (size_id) REFERENCES sizes (id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE role_of_user
    ADD CONSTRAINT FK_ROLE_OF_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE role_of_user
    ADD CONSTRAINT FK_ROLE_OF_USER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_address
    ADD CONSTRAINT FK_USER_ADDRESS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_details
    ADD CONSTRAINT FK_USER_DETAILS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE voucher_of_user
    ADD CONSTRAINT FK_VOUCHER_OF_USER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE voucher_of_user
    ADD CONSTRAINT FK_VOUCHER_OF_USER_ON_VOUCHER FOREIGN KEY (voucher_id) REFERENCES vouchers (id);

ALTER TABLE material_of_product
    ADD CONSTRAINT fk_matofpro_on_product FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE material_of_product
    ADD CONSTRAINT fk_matofpro_on_product_material FOREIGN KEY (material_id) REFERENCES product_materials (id);

ALTER TABLE permission_of_role
    ADD CONSTRAINT fk_perofrol_on_permission FOREIGN KEY (permission_id) REFERENCES permissions (id);

ALTER TABLE permission_of_role
    ADD CONSTRAINT fk_perofrol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE type_of_product
    ADD CONSTRAINT fk_typofpro_on_product FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE type_of_product
    ADD CONSTRAINT fk_typofpro_on_product_type FOREIGN KEY (type_id) REFERENCES product_types (id);

ALTER TABLE voucher_of_category
    ADD CONSTRAINT fk_vouofcat_on_category FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE voucher_of_category
    ADD CONSTRAINT fk_vouofcat_on_voucher FOREIGN KEY (voucher_id) REFERENCES vouchers (id);