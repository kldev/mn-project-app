create table person_people_types
(
    id   INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_person_people_type_name UNIQUE (name)
);

create table person_people
(
    id             BIGSERIAL PRIMARY KEY,
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    car_plate      VARCHAR(100) NULL,
    field0         VARCHAR(1024) NULL,
    field1         VARCHAR(1024) NULL,
    field2         VARCHAR(1024) NULL,
    field3         VARCHAR(1024) NULL,
    added_by_id    BIGINT NULL REFERENCES user_accounts(id) ON DELETE NO ACTION,
    person_type_id INT REFERENCES person_people_types (id) ON DELETE NO ACTION,
    CONSTRAINT UQ_person_people__car_plate UNIQUE (car_plate)
);

create table user_persons
(
    user_id   BIGINT NOT NULL REFERENCES user_accounts (id) ON DELETE NO ACTION,
    person_id BIGINT NOT NULL REFERENCES person_people (id) ON DELETE NO ACTION,
    PRIMARY KEY (user_id, person_id)
);
