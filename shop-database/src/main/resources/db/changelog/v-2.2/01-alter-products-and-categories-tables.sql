ALTER TABLE `products`
CHANGE COLUMN `title` `name` varchar(32) not null;
GO

ALTER TABLE `products`
CHANGE COLUMN `cost` `price` decimal(19,2) not null;
GO

ALTER TABLE `categories`
CHANGE COLUMN `title` `name` varchar(32) not null;
GO


