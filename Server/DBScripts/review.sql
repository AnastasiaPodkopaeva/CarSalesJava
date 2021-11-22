SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `review`;
CREATE TABLE IF NOT EXISTS `review`(
   `id_review`        int NOT NULL auto_increment,
   `id_user`          int NOT NULL,
   `id_product`       int NOT NULL,
   `review_text`      varchar(45) NOT NULL,
   PRIMARY KEY (`id_review`),
   FOREIGN KEY (`id_user`)
       REFERENCES user(id_user)
       ON DELETE CASCADE,
   FOREIGN KEY (`id_product`)
       REFERENCES car(id_product)
       ON DELETE CASCADE
) DEFAULT CHARSET=utf8;

INSERT INTO `review` VALUES
       (1,3,6,'хороший автомобиль'),
       (2,2,6,'не превзошел ожиданий'),
       (3,4,6,'ничего необычного'),
       (4,1,6,'безумно рады, что выбрали именно это авто');

SET FOREIGN_KEY_CHECKS = 1;
