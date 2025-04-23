CREATE TABLE medias
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    is_lock      BIT DEFAULT 0         NOT NULL,
    create_by    VARCHAR(255)          NULL,
    create_at    datetime              NULL,
    update_by    VARCHAR(255)          NULL,
    update_at    datetime              NULL,
    `path`       VARCHAR(255)          NULL,
    display_name VARCHAR(255)          NULL,
    parent_path  VARCHAR(255)          NULL,
    extend       VARCHAR(255)          NULL,
    file_name    VARCHAR(255)          NULL,
    CONSTRAINT pk_medias PRIMARY KEY (id)
);