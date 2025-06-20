insert into carts(id, user_id, is_lock, create_by, update_by)
values (1, 1, false, 'kiminonawa1305@gmail.com', 'kiminonawa1305@gmail.com');

insert into cart_items(id, cart_id, variant_id, product_id, quantity, is_lock, create_by, update_by)
values
    (1, 1, '6825f1241bd0a01c950ab5a3', '6825f124213ef202c0fd1eb7', 10, false, 'kiminonawa1305@gmail.com', 'kiminonawa1305@gmail.com'),
    (2, 1, '6825f12d1bd0a01c950ab5c1', '6825f12c213ef202c0fd1eb8', 10, false, 'kiminonawa1305@gmail.com', 'kiminonawa1305@gmail.com'),
    (3, 1, '6825fc331bd0a01c950ac02c', '6825fc30213ef202c0fd1f3e', 10, false, 'kiminonawa1305@gmail.com', 'kiminonawa1305@gmail.com'),
    (4, 1, '6825fddf1bd0a01c950ac2e9', '6825fdde213ef202c0fd1f6f', 10, false, 'kiminonawa1305@gmail.com', 'kiminonawa1305@gmail.com'),
    (5, 1, '6825ff241bd0a01c950ac609', '6825ff23213ef202c0fd1fa2', 10, false, 'kiminonawa1305@gmail.com', 'kiminonawa1305@gmail.com');