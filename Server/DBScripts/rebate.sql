SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `rebate`;
CREATE TABLE IF NOT EXISTS `rebate`(
    `id_rebate`        int NOT NULL auto_increment,
    `id_user`          int NOT NULL,
    `percent`          int NOT NULL,
    `id_manufacturer`  int NOT NULL,
    PRIMARY KEY (`id_rebate`),
    FOREIGN KEY (`id_manufacturer`)
        REFERENCES manufacturer(id_manufacturer)
        ON DELETE CASCADE,
    FOREIGN KEY (`id_user`)
        REFERENCES user(id_user)
        ON DELETE CASCADE
) DEFAULT CHARSET=utf8;

INSERT INTO `rebate` VALUES (1,5,100,1),(2,5,50,2);

SET FOREIGN_KEY_CHECKS = 1;
