-- SCRIPT CREATION DES TABLES

CREATE TABLE Pizzas (
    id INT PRIMARY KEY,
    nom VARCHAR(100),
    prix_base DECIMAL(5, 2)
);

CREATE TABLE Ingrédients (
    id INT PRIMARY KEY,
    nom VARCHAR(100)
);

CREATE TABLE Pizzas_Ingrédients (
    pizza_id INT,
    ingredient_id INT,
    PRIMARY KEY (pizza_id, ingredient_id),
    FOREIGN KEY (pizza_id) REFERENCES Pizzas(id),
    FOREIGN KEY (ingredient_id) REFERENCES Ingrédients(id)
);

CREATE TABLE Clients (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100),
    solde_compte DECIMAL(10, 2)
);

CREATE TABLE Livreurs (
    id INT PRIMARY KEY,
    nom VARCHAR(100)
);

CREATE TABLE Véhicules (
    id INT PRIMARY KEY,
    type VARCHAR(50)
);

CREATE TABLE Commandes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT,
    livreur_id INT,
    vehicule_id INT,
    date DATE,
    retard BOOLEAN,
    FOREIGN KEY (client_id) REFERENCES Clients(id),
    FOREIGN KEY (livreur_id) REFERENCES Livreurs(id),
    FOREIGN KEY (vehicule_id) REFERENCES Véhicules(id)
);

CREATE TABLE Pizzas_Commandes (
    commande_id INT,
    pizza_id INT,
    taille VARCHAR(10),
    quantité INT,
    PRIMARY KEY (commande_id, pizza_id),
    FOREIGN KEY (commande_id) REFERENCES Commandes(id),
    FOREIGN KEY (pizza_id) REFERENCES Pizzas(id)
);


-- SCRIPT INSERTION DONNEES

INSERT INTO Pizzas (id, nom, prix_base) VALUES
(1, 'Margherita', 8.00),
(2, 'Pepperoni', 9.50),
(3, 'Hawaïenne', 10.00);

INSERT INTO Ingrédients (id, nom) VALUES
(1, 'Tomate'),
(2, 'Mozzarella'),
(3, 'Pepperoni'),
(4, 'Ananas'),
(5, 'Jambon');

INSERT INTO Pizzas_Ingrédients (pizza_id, ingredient_id) VALUES
(1, 1), (1, 2), 
(2, 1), (2, 2), (2, 3), 
(3, 1), (3, 2), (3, 4), (3, 5);

INSERT INTO Clients (id, nom, solde_compte) VALUES
(1, 'Alice', 50.00),
(2, 'Bob', 30.00);

INSERT INTO Livreurs (id, nom) VALUES
(1, 'John'),
(2, 'Doe');

INSERT INTO Véhicules (id, type) VALUES
(1, 'Voiture'),
(2, 'Moto');

INSERT INTO Commandes (id, client_id, livreur_id, vehicule_id, date, retard) VALUES
(1, 1, 1, 1, '2024-06-01', FALSE),
(2, 2, 2, 2, '2024-06-01', TRUE);

INSERT INTO Pizzas_Commandes (commande_id, pizza_id, taille, quantité) VALUES
(1, 1, 'humaine', 1),
(2, 2, 'ogresse', 2);


-- INTERROGATION BD

-- 2.1 Menu
SELECT P.nom AS pizza_nom, P.prix_base, GROUP_CONCAT(I.nom) AS ingredients
FROM Pizzas P
JOIN Pizzas_Ingrédients PI ON P.id = PI.pizza_id
JOIN Ingrédients I ON PI.ingredient_id = I.id
GROUP BY P.nom, P.prix_base;

-- 2.2 Fiche de livraison
SELECT L.nom AS livreur_nom, V.type AS vehicule_type, C.nom AS client_nom, Com.date, Com.retard, P.nom AS pizza_nom, P.prix_base
FROM Commandes Com
JOIN Livreurs L ON Com.livreur_id = L.id
JOIN Véhicules V ON Com.vehicule_id = V.id
JOIN Clients C ON Com.client_id = C.id
JOIN Pizzas_Commandes PC ON Com.id = PC.commande_id
JOIN Pizzas P ON PC.pizza_id = P.id;

-- Quels sont les véhicules n’ayant jamais servi ?
SELECT V.type
FROM Véhicules V
LEFT JOIN Commandes Com ON V.id = Com.vehicule_id
WHERE Com.id IS NULL;

-- Calcul du nombre de commandes par client ?
SELECT C.nom, COUNT(Com.id) AS nombre_commandes
FROM Clients C
LEFT JOIN Commandes Com ON C.id = Com.client_id
GROUP BY C.nom;

-- Calcul de la moyenne des commandes ?
SELECT AVG(nombre_commandes) AS moyenne_commandes
FROM (
    SELECT COUNT(Com.id) AS nombre_commandes
    FROM Clients C
    LEFT JOIN Commandes Com ON C.id = Com.client_id
    GROUP BY C.id
) AS sous_requete;

-- Extraction des clients ayant commandé plus que la moyenne ?
SELECT C.nom
FROM Clients C
JOIN (
    SELECT client_id, COUNT(id) AS nombre_commandes
    FROM Commandes
    GROUP BY client_id
) AS Com ON C.id = Com.client_id
WHERE Com.nombre_commandes > (
    SELECT AVG(nombre_commandes)
    FROM (
        SELECT COUNT(id) AS nombre_commandes
        FROM Commandes
        GROUP BY client_id
    ) AS sous_requete
);
