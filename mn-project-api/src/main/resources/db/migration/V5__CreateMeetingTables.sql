create table meeting_status
(
    id   INT PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_meeting_status_code UNIQUE (code)
);

create table meeting_main
(
    id          BIGSERIAL PRIMARY KEY,
    day_value   DATE NOT NULL,
    start_hour  INT  NOT NULL,
    end_hour    INT NULL,
    create_at   TIMESTAMP without time zone default current_timestamp,
    added_by_id BIGINT NULL REFERENCES user_accounts(id) ON DELETE NO ACTION,
    status_id   INT REFERENCES meeting_status (id) ON DELETE NO ACTION
);

create table meeting_hosts
(
    id         BIGSERIAL PRIMARY key,
    meeting_id BIGINT NULL REFERENCES meeting_main(id) ON DELETE NO ACTION,
    person_id  BIGINT NULL REFERENCES person_people(id) ON DELETE NO ACTION
);

create table meeting_participant
(
    id         BIGSERIAL PRIMARY key,
    meeting_id BIGINT NULL REFERENCES meeting_main(id) ON DELETE NO ACTION,
    person_id  BIGINT NULL REFERENCES person_people(id) ON DELETE NO ACTION
);