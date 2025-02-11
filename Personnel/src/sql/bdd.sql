CREATE TABLE Employe (
    id INT PRIMARY KEY AUTO_INCREMENT,
    isAdmin BOOLEAN NOT NULL,
    isSuperUser BOOLEAN NOT NULL,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    mail VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    dateArriver DATE NOT NULL,
    dateDepart DATE,
    ligue_id INT,
    FOREIGN KEY (ligue_id) REFERENCES Ligue(id)
);

CREATE TABLE Ligue (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL
);
