DROP TABLE IF EXISTS `employe`;
CREATE TABLE IF NOT EXISTS `employe` (
  `id` int NOT NULL AUTO_INCREMENT,
  `isAdmin` boolean,
  `isSuperUser` boolean),
  `nom` varchar(300),
  `prenom` varchar(300),
  `mail` varchar(300),
  `password` varchar(50),
  `dateArriver` date,
  `Datedepart` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;
CREATE TABLE IF NOT EXISTS `ligue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
)