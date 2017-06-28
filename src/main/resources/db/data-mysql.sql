USE `sevendb`;
INSERT INTO `company` (name, email, address) VALUES
  ("TEST_CMP1", "name.cmp1@test.com", "address of TEST_CMP1 company"),
  ("TEST_CMP2", "name.cmp2@test.com", "address of TEST_CMP2 company"),
  ("TEST_CMP3", "name.cmp3@test.com", "address of TEST_CMP3 company");

INSERT INTO `users` (companyid, name, lastname, email, phone, password) VALUES
  (100000, "FName1", "LName1", "admin@gmail.com", "111111", "$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju"),
  (100001, "FName2", "LName2", "FName2@gmail.com", "222222", "$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju"),
  (100002, "FName3", "LName3", "FName3@gmail.com", "333333", "$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju");

INSERT INTO `role` (role) VALUES
  ("ADMIN"),
  ("COMPANY_OWNER"),
  ("COMPANY_EMPLOYER");

  INSERT INTO `user_roles` (user_id, role_id) VALUES
    (100003, 100009),
    (100004, 100010),
    (100005, 100011);

INSERT INTO `report` (companyid, name, data) VALUES
  (100000, "Report 1", "I like this solution because it is remember me solution with Play Framework"),
  (100001, "Report 2", "ublic void configAuthentication(AuthenticationManagerBuilder auth) throws Exception"),
  (100002, "Report 3", "uth.jdbcAuthentication().dataSource(dataSource)");