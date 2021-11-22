SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `manufacturer`;

CREATE TABLE IF NOT EXISTS `manufacturer`(
    `id_manufacturer`   int NOT NULL auto_increment,
    `name`      varchar(45) NOT NULL,
    `country`   varchar(45) NOT NULL,
    `email`     varchar(45) NOT NULL,
    `rating`    double  NOT NULL,
    `number_of_ratings` int  NOT NULL,
    PRIMARY KEY (`id_manufacturer`)
) DEFAULT CHARSET=utf8;

INSERT INTO `manufacturer` VALUES
    (1,'Toyota','Япония','Toyota@gmail.com',9.3,6),
    (2,'Ford','США','Ford@gmail.com',8.9,19),
    (3,'Honda','Япония','Honda@gmail.com',8.34,19),
    (4,'Volkswagen','Германия','Volkswagen@gmail.com',8.7,69),
    (5,'Chevrolet','США','Chevrolet@gmail.com',8.2,40),
    (6,'Tesla','США','Tesla@gmail.com',9.5,40);
SET FOREIGN_KEY_CHECKS = 1;

