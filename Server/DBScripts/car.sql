SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `car`;

CREATE TABLE IF NOT EXISTS `car`(
    `id_product` int        NOT NULL auto_increment,
    `name`      varchar(45) NOT NULL,
    `type`      varchar(45) NOT NULL,
    `cost`      int         NOT NULL,
    `count`     int         NOT NULL,
    `id_manufacturer` int   NOT NULL,
    PRIMARY KEY (`id_product`),
    FOREIGN KEY (`id_manufacturer`)
        REFERENCES manufacturer(id_manufacturer)
        ON DELETE CASCADE
) DEFAULT CHARSET=utf8;

INSERT INTO `car` VALUES
      (1,'Toyota Corolla','бензин',41000,2,1),
      (2,'Ford F-150 XIII','бензин',168966,1,2),
      (3,'Ford F-150 X','бензин (метан)',36700,1,2),
      (4,'Toyota RAV4 III','дизель',28000,2,1),
      (5,'Honda Civic','дизель',17000,3,3),
      (6,'Honda CR-V','бензин (гибрид)',18940,2,3),
      (7,'Volkswagen Tiguan','бензин',43300,2,4),
      (8,'Ram','дизель',58900,2,5),
      (9,'Toyota Camry','бензин',89900,3,1),
      (10,'Volkswagen Golf','бензин',33400,3,4),
      (11,'Chevrolet Silverado','дизель',79980,2,5),
      (12,'Tesla Model 3','электро',120000,2,6),
      (13,'Tesla Model X','электро',118900,2,6),
      (14,'Volkswagen Jetta','бензин',36780,2,4),
      (15,'Chevrolet Bolt','бензин',65500,1,5);

SET FOREIGN_KEY_CHECKS = 1;
