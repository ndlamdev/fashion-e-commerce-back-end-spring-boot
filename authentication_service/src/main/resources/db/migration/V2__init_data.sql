INSERT INTO permissions (id, name)
VALUES (1, 'AUTH_API_TEST'),
       (2, 'USER_SAVE_PROFILE'),
       (3, 'USER_GET_PROFILE'),
       (4, 'USER_GET_BY_ADDRESS_ID'),
       (5, 'USER_SAVE_ADDRESS'),
       (6, 'USER_ADD_ADDRESS'),
       (7, 'USER_DELETE_ADDRESS'),
       (8, 'USER_SET_DEFAULT_ADDRESS')
;

INSERT INTO roles (id, name)
VALUES (2, 'BASE');

INSERT INTO roles (id, name)
VALUES (1, 'ADMIN');

INSERT INTO permission_of_role (permission_id, role_id)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 2),
       (8, 2)
;