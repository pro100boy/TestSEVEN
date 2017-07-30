USE `sevendb`;
INSERT INTO `company` (name, email, address) VALUES
  ("Admin's company", "adm.cmp@test.com", "address of Admin's company"),
  ("Рога и копыта", "rik@test.com", "address of 'Рога и копыта' company"),
  ("Apollo", "apollo@test.com", "address of 'Apollo' company");

INSERT INTO `users` (companyid, name, lastname, email, phone, password) VALUES
  (100000, "Ivan", "Petrov", "admin@gmail.com", "+380971234567", "$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju"),
  (100001, "Sidor", "Ivanov", "ivanov@gmail.com", "+380509876543", "$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju"),
  (100002, "Petr", "Sidoroff", "sid@gmail.com", "+14084567890", "$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju");

INSERT INTO `role` (role) VALUES
  ("ADMIN"),
  ("COMPANY_OWNER"),
  ("COMPANY_EMPLOYER");

  INSERT INTO `user_roles` (user_id, role_id) VALUES
    (100003, 100009),
    (100004, 100010),
    (100005, 100011);

INSERT INTO `report` (companyid, name, data, time) VALUES
  (100000, "Report 1", "I like this solution because it is remember me solution with Play Framework", "2017-07-01 12:36:53"),
  (100001, "Report 2", "ublic void configAuthentication(AuthenticationManagerBuilder auth) throws Exception", "2017-07-03 12:30:00"),
  (100002, "Report 3", "uth.jdbcAuthentication().dataSource(dataSource)", "2017-07-03 12:40:00");