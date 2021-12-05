create table crm_company
(
    id          BIGSERIAL primary key,
    name        VARCHAR(1024),
    acronym     VARCHAR(100),
    address     VARCHAR(2048) NULL,
    create_at   TIMESTAMP without time zone default current_timestamp,
    active      BIT(1) DEFAULT '1',
    deleted     BIT(1) DEFAULT '0',
    added_by_id BIGINT NULL REFERENCES user_accounts(id) ON DELETE NO ACTION
);

create table crm_company_employee
(
    company_id BIGINT NOT NULL REFERENCES crm_company (id) ON DELETE NO ACTION,
    person_id  BIGINT NOT NULL REFERENCES person_people (id) ON DELETE NO ACTION,
    PRIMARY KEY (company_id, person_id)
);