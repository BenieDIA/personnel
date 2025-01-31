Create table Employe
(
	id integer primary key AUTO_INCREMENT,
	isAdmin boolean,
	isSuperAdmin boolean,
	nom varchar(155),
	prenom varchar(155),
	mail varchar(155),
	password varchar(155),
	dateArriver date,
	dateDepart date
);

Create table Ligue
(
	id integer primary key AUTO_INCREMENT,
	nom varchar(155)
);

