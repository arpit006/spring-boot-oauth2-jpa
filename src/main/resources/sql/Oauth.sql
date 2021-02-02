CREATE TABLE IF NOT EXISTS oauth_client_details
(
    client_id               VARCHAR(255) PRIMARY KEY,
    resource_ids            VARCHAR(255),
    client_secret           VARCHAR(255),
    scope                   VARCHAR(255),
    authorized_grant_types  VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities             VARCHAR(255),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096),
    autoapprove             VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS oauth_access_token
(
    token_id          VARCHAR(255),
    token             BYTEA,
    authentication_id VARCHAR(255) PRIMARY KEY,
    user_name         VARCHAR(255),
    client_id         VARCHAR(255),
    authentication    BYTEA,
    refresh_token     VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS oauth_refresh_token
(
    token_id       VARCHAR(255),
    token          BYTEA,
    authentication BYTEA
);
--
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1;

--
insert into role (id, role) values (1, 'ROLE_USER');
insert into role (id, role) values (2, 'ROLE_ADMIN');
