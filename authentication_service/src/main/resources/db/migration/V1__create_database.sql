CREATE TABLE permission_of_role
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    is_lock       BIT DEFAULT 0 NOT NULL,
    create_by     VARCHAR(255) NULL,
    create_at     datetime NULL,
    update_by     VARCHAR(255) NULL,
    update_at     datetime NULL,
    permission_id BIGINT NULL,
    role_id       BIGINT NULL,
    CONSTRAINT pk_permission_of_role PRIMARY KEY (id)
);

CREATE TABLE permissions
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0 NOT NULL,
    create_by  VARCHAR(255) NULL,
    create_at  datetime NULL,
    update_by  VARCHAR(255) NULL,
    update_at  datetime NULL,
    name       VARCHAR(255) NULL,
    `describe` VARCHAR(255) NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

CREATE TABLE role_of_user
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    is_lock   BIT DEFAULT 0 NOT NULL,
    create_by VARCHAR(255) NULL,
    create_at datetime NULL,
    update_by VARCHAR(255) NULL,
    update_at datetime NULL,
    user_id   BIGINT NULL,
    role_id   BIGINT NULL,
    CONSTRAINT pk_role_of_user PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    is_lock    BIT DEFAULT 0 NOT NULL,
    create_by  VARCHAR(255) NULL,
    create_at  datetime NULL,
    update_by  VARCHAR(255) NULL,
    update_at  datetime NULL,
    name       VARCHAR(255) NULL,
    `describe` VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    is_lock     BIT DEFAULT 0 NOT NULL,
    create_by   VARCHAR(255) NULL,
    create_at   datetime NULL,
    update_by   VARCHAR(255) NULL,
    update_at   datetime NULL,
    email       VARCHAR(255) NULL,
    password    VARCHAR(255) NULL,
    facebook_id VARCHAR(255) NULL,
    active      BIT DEFAULT 0 NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_74165e195b2f7b25de690d14a UNIQUE (email);

ALTER TABLE role_of_user
    ADD CONSTRAINT uc_b8bba79049d2724e7e3b0d1f1 UNIQUE (user_id, role_id);

ALTER TABLE permission_of_role
    ADD CONSTRAINT uc_d4678ce06af6cf3054ade8580 UNIQUE (permission_id, role_id);

ALTER TABLE permissions
    ADD CONSTRAINT uc_e7409dbdcee9059862a6222dc UNIQUE (name);

ALTER TABLE roles
    ADD CONSTRAINT uc_fd322f7161fc2fbfed95c3840 UNIQUE (name);

ALTER TABLE permission_of_role
    ADD CONSTRAINT FK_PERMISSION_OF_ROLE_ON_PERMISSION FOREIGN KEY (permission_id) REFERENCES permissions (id);

ALTER TABLE permission_of_role
    ADD CONSTRAINT FK_PERMISSION_OF_ROLE_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE role_of_user
    ADD CONSTRAINT FK_ROLE_OF_USER_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE role_of_user
    ADD CONSTRAINT FK_ROLE_OF_USER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);