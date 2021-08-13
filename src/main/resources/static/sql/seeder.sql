USE slim_gym;

INSERT INTO users(first_name, last_name, username, email, password, profile_pic, emergency_first_name, emergency_last_name, emergency_phone_number)
values ('David', 'Wagnon', 'Tymei', 'david.wagnon02@gmail.com' ,'password', 'https://twitter.com/Tymeitv/photo', 'Bridget',  'Lawrence', '2106666666');


INSERT INTO gym(user_id, name, address, description, equipment)
VALUES ('1', 'Square Space',  '600 Navarro St #600', 'Perfect gym for powerlifters', 'Weights, Treadmills'),
       ('1', 'Home Fitness',  '14610 Huebner Rd #110', 'Perfect gym for powerlifters', 'Weights, Treadmills');

INSERT INTO reviews(user_id, gym_id, rating, body, picture)
VALUES ('1', '1', '4', 'This gym was fantastic! Had all the equipment I needed to get a nice workout in.', 'https://www.lifefitness.com/resource/image/1302124/landscape_ratio4x3/1140/855/6831569116c580e1ad804834617a5a34/GV/custom-gym-gym-home-gym-example.jpg');

INSERT INTO schedule(user_id, gym_id, start_time, end_time)
VALUES ('1', '1', '1:00', '2:00');

INSERT INTO pictures(gym_id, url)
VALUES ('1','https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.lifefitness.com%2Fen-us%2Fblog%2Fhow-build-home-gym-your-garage&psig=AOvVaw37_LcMwNeoi3_Sv8UNpMeX&ust=1628564912800000&source=images&cd=vfe&ved=0CAoQjRxqFwoTCLC86Jz7ovICFQAAAAAdAAAAABAD');

INSERT INTO equipment(name)
VALUES ('bench press'),
       ('treadmill'),
       ('reverse-hyper'),
       ('dumbells'),
       ('yoga-balls');

INSERT INTO gym_equipment(gym_id, equipment_type_id)
VALUES ('1', '1');