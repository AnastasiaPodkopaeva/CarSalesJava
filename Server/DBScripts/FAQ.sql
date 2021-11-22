SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `faq`;

CREATE TABLE IF NOT EXISTS `faq`(
     `id_faq`     int         NOT NULL auto_increment,
     `question`   varchar(100) NOT NULL,
     `answer`     varchar(100) NOT NULL,
     PRIMARY KEY (`id_faq`)
) DEFAULT CHARSET=utf8;

INSERT INTO `faq`
VALUES (1,'где просмотреть графики?','вкладка производители'),
       (2,'где пополнить счет?','вкладка профиль'),
       (3,'что отображает график?','процент продуктов производителя от всех на рынке'),
       (4,'сколько всего тем в приложении?','пока что 2: темная и светлая');
SET FOREIGN_KEY_CHECKS = 1;