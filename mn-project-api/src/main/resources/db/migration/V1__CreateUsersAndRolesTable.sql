create table user_role_types
(
    id          INT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(1024) NULL,
    CONSTRAINT UQ_user_role_types__name UNIQUE (name)
);

create table user_accounts
(
    id             BIGSERIAL PRIMARY KEY,
    email          varchar(255) NOT NULL,
    password       varchar(255) NULL,
    active         BIT(1) DEFAULT '1',
    deleted        BIT(1) DEFAULT '0',
    create_at      TIMESTAMP without time zone default current_timestamp,
    google_user_id varchar(100) NULL,
    openid_user_id varchar(100) NULL,
    CONSTRAINT UQ_user_accounts__email UNIQUE (email)
);

create table user_roles
(
    user_id     BIGINT NOT NULL REFERENCES user_accounts (id) ON DELETE NO ACTION,
    role_id     BIGINT NOT NULL REFERENCES user_role_types (id) ON DELETE NO ACTION,
    added_by_id BIGINT NULL REFERENCES user_accounts(id) ON DELETE NO ACTION,
    create_at   TIMESTAMP without time zone default current_timestamp,
    PRIMARY KEY (user_id, role_id)
);