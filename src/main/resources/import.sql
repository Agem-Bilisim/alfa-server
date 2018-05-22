INSERT INTO c_role (ID, CREATED_DATE, CREATED_BY, NAME) VALUES(1, '2018-03-15 13:08:22', 'SYSTEM', 'admin');
INSERT INTO c_role (ID, CREATED_DATE, CREATED_BY, NAME) VALUES(2, '2018-03-15 13:08:22', 'SYSTEM', 'user');

INSERT INTO c_user (ID, USER_NAME, EMAIL, PASSWORD_HASH, CREATED_DATE, CREATED_BY, ROLE_ID) VALUES(1, 'admin','admin@agem.com.tr', '$2a$11$Zj8kayzITcw/aK7os2dQYOfDOsW4BOwSZJpH7pxeW94N0ufkdKGCm', '2018-03-15 13:08:22', 'SYSTEM', 1);
INSERT INTO c_user (ID, USER_NAME, EMAIL, PASSWORD_HASH, CREATED_DATE, CREATED_BY, ROLE_ID) VALUES(2, 'test','test@agem.com.tr', '$2a$11$ebcZe7Qat4WZ9cEMVPoUcu0TCYeFVfro.XCHWGnvuwXF3hk.3GhdS', '2018-03-15 13:08:22', 'SYSTEM', 2);
