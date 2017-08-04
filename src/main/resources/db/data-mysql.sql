USE `sevendb`;
INSERT INTO `company` (name, email, address) VALUES
  ("ATB company", "atb.cmp@test.com", "address of ATB company"),
  ("Рога и копыта", "rik@test.com", "address of 'Рога и копыта' company"),
  ("Apollo", "apollo@test.com", "address of 'Apollo' company");

INSERT INTO `users` (companyid, name, lastname, email, phone, password) VALUES
  (null, 'Ivan', 'Petrov', 'admin@gmail.com', '+380971234567', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  (100001, 'Sidor', 'Ivanov', 'ivanov@gmail.com', '+380509876543', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  (100002, 'Petr', 'Sidoroff', 'sid@gmail.com', '+14084567890', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  (100002, 'Petr', 'Smirnoff', 'Smir@gmail.com', '+14087896410', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  (100002, 'Andre', 'Tan', 'tan@gmail.com', '+14081111154', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  (100001, 'Paul', 'Furman', 'fur@gmail.com', '+12354654', '$2a$10$Habgs3AQoWMKcMbIlLLbM.pV2DdPxrRSItlbeySrdHAdzqQEl7vee');

/*INSERT INTO `role` (role) VALUES
  ("ADMIN"),
  ("COMPANY_OWNER"),
  ("COMPANY_EMPLOYER");*/

INSERT INTO `user_roles` (user_id, role) VALUES
  (200000, "ADMIN"),
  (200000, "ACTUATOR"),
  (200001, "COMPANY_OWNER"),
  (200004, "COMPANY_OWNER"),
  (200002, "COMPANY_EMPLOYER"),
  (200003, "COMPANY_EMPLOYER"),
  (200005, "COMPANY_EMPLOYER");

INSERT INTO `report` (companyid, name, data, time) VALUES
  (100002, 'Report2 for ''Apollo''', 'I like this solution because it is remember me solution with Play Framework', '2017-07-01 12:36:53'),
  (100001, 'Report for ''Рога и копыта''', 'ublic void configAuthentication(AuthenticationManagerBuilder auth) throws Exception', '2017-07-03 12:30:00'),
  (100002, 'Report for ''Apollo''', 'uth.jdbcAuthentication().dataSource(dataSource)', '2017-07-03 12:40:00'),
  (100002, 'Repo3 Apollo', 'asdgasfas', '2017-08-02 12:00:00');