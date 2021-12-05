delete
from person_people_types;

insert into person_people_types(id, name)
    values (1, 'user');
insert into person_people_types(id, name)
    values (2, 'quest');

insert into person_people_types(id, name)
values (3, 'crm_company_employee');


delete
from user_role_types;

insert into user_role_types(id, name, description)
    values (1, 'root', 'the root role');
insert into user_role_types(id, name, description)
    values (2, 'admin', 'the admin role');
insert into user_role_types(id, name, description)
    values (3, 'responsible', 'the responsible role');
insert into user_role_types(id, name, description)
    values (4, 'guest', 'the guest not access role');