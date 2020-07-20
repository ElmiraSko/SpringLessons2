alter table `products` drop FOREIGN KEY `FKdb077t35yt55`;
GO

alter table `brands` drop key `UC_BRANDSNAME_COL`;
GO

drop table  `brands`;
GO
