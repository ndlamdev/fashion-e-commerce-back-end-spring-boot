INSERT INTO permissions (id, name)
SELECT 1, 'AUTH_API_TEST';

INSERT INTO roles (id, name)
SELECT 2, 'BASE';

INSERT INTO roles (id, name)
SELECT 1, 'ADMIN';

INSERT INTO permission_of_role (permission_id, role_id)
SELECT 1, 2;