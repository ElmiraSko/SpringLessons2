ALTER TABLE `products`
CHANGE COLUMN `name` `title` varchar(32) not null;
GO

ALTER TABLE `products`
CHANGE COLUMN `price` `cost` decimal(19,2) not null;
GO

ALTER TABLE `categories`
CHANGE COLUMN `name` `title` varchar(32) not null;
GO