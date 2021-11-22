SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `purchase`;

CREATE TABLE IF NOT EXISTS `purchase`(
    `id_purchase`     int NOT NULL auto_increment,
    `id_user`         int NOT NULL,
    `id_product`      int NOT NULL,
    PRIMARY KEY (`id_purchase`),
    FOREIGN KEY (`id_user`)
        REFERENCES user(id_user)
        ON DELETE CASCADE,
    FOREIGN KEY (`id_product`)
        REFERENCES car(id_product)
        ON DELETE CASCADE
) DEFAULT CHARSET=utf8;

INSERT INTO `purchase` VALUES (1,5,1),(2,5,8),(3,5,5),(4,5,11);

SET FOREIGN_KEY_CHECKS = 1;
