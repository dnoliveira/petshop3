
DROP SCHEMA if exists `petshop` ;
CREATE SCHEMA if not exists `petshop` ;
USE `petshop`;


DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dono` varchar(60) NOT NULL,
  `nome` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `pet` VALUES (1,'Monica','Bidu'),(2,'Gabriella','Spike');


DROP TABLE IF EXISTS `ordem`;
CREATE TABLE `ordem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_ordem` datetime(6) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  `id_pet` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ordemservico_pet` (`id_pet`),
  CONSTRAINT `fk_ordemservico_pet` FOREIGN KEY (`id_pet`) REFERENCES `pet` (`id`)
);
INSERT INTO `ordem` VALUES (1,'2022-05-12 21:00:00.000000','',1);


DROP TABLE IF EXISTS `servico`;
CREATE TABLE `servico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(60) NOT NULL,
  `valor` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `servico` VALUES (1,'Tosa',50.00),(2,'Banho',30.00);



DROP TABLE IF EXISTS `ordem_servico`;
CREATE TABLE `ordem_servico` (
  `valor` decimal(19,2) NOT NULL,
  `servico_id` bigint(20) NOT NULL,
  `ordem_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ordem_id`,`servico_id`),
  KEY `FKohi8bbf8qk5e5mu96w6j015is` (`servico_id`),
  CONSTRAINT `FKfhjpuc98xvbmeyiusy6n80ee4` FOREIGN KEY (`ordem_id`) REFERENCES `ordem` (`id`),
  CONSTRAINT `FKohi8bbf8qk5e5mu96w6j015is` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`)
);
INSERT INTO `ordem_servico` VALUES (50.00,1,1),(30.00,2,1);
