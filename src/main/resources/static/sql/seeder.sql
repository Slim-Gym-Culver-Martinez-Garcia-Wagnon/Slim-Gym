USE slim_gym;

INSERT INTO users(first_name, last_name, username, email, password, profile_pic, emergency_first_name, emergency_last_name, emergency_phone_number)
values ('David', 'Wagnon', 'Tymei', 'david.wagnon@gmail.com' ,'password', 'https://twitter.com/Tymeitv/photo', 'Bridget',  'Lawrence', '2106666666'),
       ('David', 'Culver', 'DCulver', 'david.culver@gmail.com' ,'password', 'https://twitter.com/Tymeitv/photo', 'Dustin',  'Martinez', '2105555555'),
       ('Dustin', 'Martinez', 'Tymei', 'dustin.martinez@gmail.com' ,'password', 'https://twitter.com/Tymeitv/photo', 'David',  'Wagnon', '2104444444'),
       ('Juan', 'Garcia', 'Turbo', 'juan.garcia@gmail.com' ,'password', 'https://twitter.com/Tymeitv/photo', 'David',  'Culver', '2103333333');


INSERT INTO gym(user_id, name, address, description, equipment)
VALUES ('1', 'Square Space',  '600 Navarro St #600', 'Perfect gym for Olympic', 'Weights, Treadmills'),
       ('2', 'Home Fitness',  '14610 Huebner Rd #110', 'Perfect gym for powerlifters', 'Weights, Treadmills'),
       ('3', 'Rock Hard',  '7427 Northwest Loop 410', 'Perfect gym for Weightlifter', 'Weights, Treadmills'),
       ('4', 'Pump You Up',  '6727 Northwest Loop 410', 'Perfect gym for Yoga', 'Weights, Treadmills');

INSERT INTO reviews(user_id, gym_id, rating, body, picture)
VALUES ('1', '1', '5', 'This gym was fantastic! Had all the equipment I needed to get a nice workout in.', 'https://www.lifefitness.com/resource/image/1302124/landscape_ratio4x3/1140/855/6831569116c580e1ad804834617a5a34/GV/custom-gym-gym-home-gym-example.jpg'),
('1', '2', '4', 'This gym was great! Perfect 7/5.', 'https://www.lifefitness.com/resource/image/1302124/landscape_ratio4x3/1140/855/6831569116c580e1ad804834617a5a34/GV/custom-gym-gym-home-gym-example.jpg'),
('1', '3', '2', 'This gym was not great. Not enough yoga mats.', 'https://www.lifefitness.com/resource/image/1302124/landscape_ratio4x3/1140/855/6831569116c580e1ad804834617a5a34/GV/custom-gym-gym-home-gym-example.jpg'),
('1', '4', '1', 'This gym was terrible! No squat rack at the yoga gym.', 'https://www.lifefitness.com/resource/image/1302124/landscape_ratio4x3/1140/855/6831569116c580e1ad804834617a5a34/GV/custom-gym-gym-home-gym-example.jpg');

INSERT INTO pictures(gym_id, url)
VALUES ('1','https://classpass-res.cloudinary.com/image/upload/q_auto,f_auto/sedot2xnfrii7rdoekl5.jpg'),
       ('2','https://chgadmin.cartershomegym.com/uploads/home-gym-reflection.jpg'),
       ('3','https://chgadmin.cartershomegym.com/uploads/home-gym-reflection.jpg'),
       ('4','https://cdn.shopify.com/s/files/1/0049/4272/files/benoit-2_grande.jpg?v=1523249070');

INSERT INTO equipment(name)
VALUES ('bench press'),
       ('treadmill'),
       ('reverse-hyper'),
       ('dumbells'),
       ('yoga-balls');