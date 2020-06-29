INSERT INTO `users` (`name`, `age`, `email`, `password`, `phone`)
VALUE ('admin', 28, 'admin@yandex.ru', '$1a$10admin', '+79113567762'),
        ('guest', 43, 'guest@yandex.ru', '$2a$11guest', '+79018567762');
GO

INSERT INTO `roles` (`name`)
VALUE ('ROLE_ADMIN'), ('ROLE_GUEST');
GO

INSERT INTO `users_roles` (`user_id`, `role_id`)
SELECT (SELECT id FROM `users` WHERE `name` = 'admin'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM `users` WHERE `name` = 'guest'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_GUEST');
GO

INSERT INTO `products` (`title`, `cost`)
VALUE ('dress', 2800);
GO

INSERT INTO `categories` (`title`)
VALUE ('Men'), ('Women');
GO