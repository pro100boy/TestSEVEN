USE `sevendb`;
INSERT INTO `company` (name, email, address) VALUES
  ("ATB company", "atb.cmp@test.com", "address of ATB company"),
  ("Рога и копыта", "rik@test.com", "address of 'Рога и копыта' company"),
  ("Apollo", "apollo@test.com", "address of 'Apollo' company");

INSERT INTO `users` (companyid, name, lastname, email, phone, password) VALUES
  (null, 'Ivan', 'Petrov', 'admin@gmail.com', '+380971234567', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju'),
  (100001, 'Sidor', 'Ivanov', 'ivanov@gmail.com', '+380509876543', '$2a$10$mbhgIjEUPHS9ro3PuCemueFQwM3Y7lHo/RAF0kTRK0fTVl0PMGS5K'),
  (100002, 'Petr', 'Sidoroff', 'sid@gmail.com', '+14084567890', '$2a$10$mbhgIjEUPHS9ro3PuCemueFQwM3Y7lHo/RAF0kTRK0fTVl0PMGS5K'),
  (100000, 'Petr', 'Smirnoff', 'Smir@gmail.com', '+14087896410', '$2a$10$mbhgIjEUPHS9ro3PuCemueFQwM3Y7lHo/RAF0kTRK0fTVl0PMGS5K'),
  (100002, 'Andre', 'Tan', 'tan@gmail.com', '+14081111154', '$2a$10$mbhgIjEUPHS9ro3PuCemueFQwM3Y7lHo/RAF0kTRK0fTVl0PMGS5K'),
  (100001, 'Paul', 'Furman', 'fur@gmail.com', '+12354654', '$2a$10$mbhgIjEUPHS9ro3PuCemueFQwM3Y7lHo/RAF0kTRK0fTVl0PMGS5K');

INSERT INTO `user_roles` (user_id, role) VALUES
  (200000, "ADMIN"),
  (200000, "ACTUATOR"),
  (200001, "COMPANY_OWNER"),
  (200004, "COMPANY_OWNER"),
  (200002, "COMPANY_EMPLOYER"),
  (200003, "COMPANY_OWNER"),
  (200005, "COMPANY_EMPLOYER");

INSERT INTO `report` (companyid, name, data, time) VALUES
  (100002, 'Report 2 for ''Apollo''', 'Content of report 2 for ''Apollo''', '2017-07-01 12:30:00'),
  (100001, 'Report for ''Рога и копыта''', 'Content of report for ''Рога и копыта''', '2017-06-01 12:30:00'),
  (100002, 'Report 1 for ''Apollo''', 'Content of report 1 for ''Apollo''', '2017-01-01 12:30:00'),
  (100002, 'Report 3 for Apollo', 'Content of report 3 for Apollo', '2017-02-02 12:00:00');