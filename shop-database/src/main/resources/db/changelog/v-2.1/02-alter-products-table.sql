ALTER TABLE `products`
ADD COLUMN `brand_id` BIGINT(20) NOT NULL default 1 AFTER `category_id`;
GO

ALTER TABLE `products`
ADD CONSTRAINT `FKdb077t35yt55`
FOREIGN KEY (`brand_id`)
REFERENCES `brands` (`id`);
GO