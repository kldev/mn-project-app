create view meeting_view_person_host_allowed as
    select id, first_name, last_name, car_plate from person_people where person_type_id = 1
;