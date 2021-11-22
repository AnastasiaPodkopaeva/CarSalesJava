SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user`(
    `id_user`    int         NOT NULL auto_increment,
    `login`      varchar(45) NOT NULL,
    `password`   varchar(45) NOT NULL,
    `role`       int,
    `first_name` varchar(45) NOT NULL,
    `last_name`  varchar(45) NOT NULL,
    `money`      int         NOT NULL,
    `address`    varchar(45) NOT NULL,
    `phone`      varchar(45) NOT NULL,
    PRIMARY KEY (`id_user`)
) DEFAULT CHARSET=utf8;

INSERT INTO `user` VALUES
    (1,'admin','12345',1,'Анастасия','Подкопаева',200000,'Минск, Беларусь','+375333242911'),
    (2,'kitty','kit12',0,'Виктория', 'Чечко',   100000, 'Гомель, Беларусь','+375447653678'),
    (3,'philipka','634ccve',0,'Филипп', 'Самойлов',   80000, 'Гродно, Беларусь','+375442634746'),
    (4,'ahes','9876d',0,'Александр', 'Ивушкин',   78000, 'Евпатория, Россия','+79787452378'),
    (5,'midzuka', 'kl123', 0,'Валерий', 'Стрельцов', 1500, 'Киев, Украина','+3806546785');

SET FOREIGN_KEY_CHECKS = 1;
