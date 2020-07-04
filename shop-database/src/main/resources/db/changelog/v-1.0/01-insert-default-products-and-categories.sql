INSERT INTO `categories` (`title`)
VALUE ('Men'), ('Women');
GO

INSERT INTO `products` (`title`, `cost`, category_id)
VALUE ('dress', 2800, 2);
GO
