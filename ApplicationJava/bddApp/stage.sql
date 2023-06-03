-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 28 mai 2023 à 13:41
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `stage`
--

-- --------------------------------------------------------

--
-- Structure de la table `candidat`
--

DROP TABLE IF EXISTS `candidat`;
CREATE TABLE IF NOT EXISTS `candidat` (
  `IdCandidat` int NOT NULL AUTO_INCREMENT,
  `Nom` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `Prenom` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `Telephone` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `Courriel` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `IdEcole` int NOT NULL,
  PRIMARY KEY (`IdCandidat`),
  KEY `IdEcole` (`IdEcole`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `candidat`
--

INSERT INTO `candidat` (`IdCandidat`, `Nom`, `Prenom`, `Telephone`, `Courriel`, `IdEcole`) VALUES
(1, 'CHERAIBI', 'KHALID', '0648707874', 'Derb Marrakech bloc 16 n°43 ElAlia, 28830', 3),
(2, 'FATIMA', 'SAADAOUI', '0681775292', '1, av 14 Août, hay Hassani, INEZGANE', 6),
(3, 'AKHENNOUCH', 'AZIZ', '0788239525', '83, rue Bachir El Ibrahimi-ex Quinconces, Grand Casablanca', 1),
(4, 'FAREHAT', 'AMINA', '0680258832', 'bd Panoramique, ang. Rte. Bouskoura', 5),
(15, 'FILALI', 'Redwane', '0615478923', 'filred15@gmail.com', 5);

-- --------------------------------------------------------

--
-- Structure de la table `candidature`
--

DROP TABLE IF EXISTS `candidature`;
CREATE TABLE IF NOT EXISTS `candidature` (
  `idC` int NOT NULL,
  `idS` int NOT NULL,
  `priorite` int DEFAULT NULL,
  `etat` enum('en attente','validé','refusé','retenu') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'en attente',
  PRIMARY KEY (`idC`,`idS`),
  KEY `idS` (`idS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `candidature`
--

INSERT INTO `candidature` (`idC`, `idS`, `priorite`, `etat`) VALUES
(1, 1, 3, 'retenu'),
(1, 5, 2, 'validé'),
(1, 7, 1, 'retenu'),
(3, 7, 3, 'en attente'),
(15, 5, 1, 'retenu');

-- --------------------------------------------------------

--
-- Structure de la table `compters`
--

DROP TABLE IF EXISTS `compters`;
CREATE TABLE IF NOT EXISTS `compters` (
  `IdCompte` int NOT NULL AUTO_INCREMENT,
  `NomUtilisateur` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `MotDePasse` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `IdR` int NOT NULL,
  PRIMARY KEY (`IdCompte`),
  KEY `IdR` (`IdR`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `compters`
--

INSERT INTO `compters` (`IdCompte`, `NomUtilisateur`, `MotDePasse`, `IdR`) VALUES
(1, '1', '1', 1),
(2, '2', '2', 2),
(3, '3', '3', 3);

-- --------------------------------------------------------

--
-- Structure de la table `comptesc`
--

DROP TABLE IF EXISTS `comptesc`;
CREATE TABLE IF NOT EXISTS `comptesc` (
  `IDCC` int NOT NULL AUTO_INCREMENT,
  `NomUtilisateur` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `MotDePasse` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `IDC` int NOT NULL,
  PRIMARY KEY (`IDCC`),
  KEY `IDC` (`IDC`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `comptesc`
--

INSERT INTO `comptesc` (`IDCC`, `NomUtilisateur`, `MotDePasse`, `IDC`) VALUES
(1, '123', '123', 1),
(2, 'fatimaSaad96', 'fatima0507092', 2),
(3, 'azizAkh_25', 'aziz212223', 3),
(4, 'farAmin6897', 'aminamina2325', 4);

-- --------------------------------------------------------

--
-- Structure de la table `ecole`
--

DROP TABLE IF EXISTS `ecole`;
CREATE TABLE IF NOT EXISTS `ecole` (
  `IdEcole` int NOT NULL AUTO_INCREMENT,
  `Nom` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `AdressePostale` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`IdEcole`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ecole`
--

INSERT INTO `ecole` (`IdEcole`, `Nom`, `AdressePostale`) VALUES
(1, 'ENSA BERRECHID', 'B.P 218, Berrechid 26100'),
(2, 'ENCG SETTAT', 'Km 3 Rte de Casablanca, Settat BP 658'),
(3, 'EST BENI MELLAL', 'Campus universitaire M\'ghila BP:591, Béni Mellal 23000'),
(4, 'FS BENMSIK', 'Bd Commandant Driss Al Harti, Casablanca 20670'),
(5, '1337 KHOURIBGA', 'Mail Central, 25000, Khouribga'),
(6, 'FSJES TANGER', ' B.P 1373 Tanger principale, Tanger');

-- --------------------------------------------------------

--
-- Structure de la table `responsable`
--

DROP TABLE IF EXISTS `responsable`;
CREATE TABLE IF NOT EXISTS `responsable` (
  `IdR` int NOT NULL AUTO_INCREMENT,
  `Nom` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `Prenom` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `Telephone` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `Courrier` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `Fonction` enum('personnel','stage') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`IdR`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `responsable`
--

INSERT INTO `responsable` (`IdR`, `Nom`, `Prenom`, `Telephone`, `Courrier`, `Fonction`) VALUES
(1, 'EL ALAMI', 'MOHAMED', '0661264455', '84, rue Mohamed Bahi -ex Meissonnier, Grand Casablanca', 'personnel'),
(2, 'IDRISSI', 'JAOUAD', '0661744722', 'Zone Industrielle, lot. n°15, Souss-Massa-Drâa', 'stage'),
(3, 'FEHMI', 'SAMIR', '0717954652', 'bd Mohamed Zerktouni, 4°et. appt.7, Grand Casablanca', 'stage');

-- --------------------------------------------------------

--
-- Structure de la table `stage`
--

DROP TABLE IF EXISTS `stage`;
CREATE TABLE IF NOT EXISTS `stage` (
  `IdS` int NOT NULL AUTO_INCREMENT,
  `Titre` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `Sujet` varchar(300) COLLATE utf8mb4_general_ci NOT NULL,
  `Duree` int NOT NULL,
  `IdR` int NOT NULL,
  PRIMARY KEY (`IdS`),
  KEY `IdR` (`IdR`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `stage`
--

INSERT INTO `stage` (`IdS`, `Titre`, `Sujet`, `Duree`, `IdR`) VALUES
(1, 'Stage en Génie Logiciel', 'Designing, coding, and debugging Java applications based on provided software requirements and design specifications.\r\nCollaborating with cross-functional teams, including software architects, designers, and product managers, to define project requirements and deliver high-quality software solutions', 90, 2),
(5, 'Stage en Développement informatique', 'Pour un poste basé à Casablanca, notre cabinet d’assurance recherche un stagiaire informatique, qui sera en charge de :\r\n• Monter, installer et mettre en service les nouveaux matériels informatiques\r\n• Former les utilisateurs\r\n• Intervenir en assistance et réparation\r\n• Ordonnancer le déroulement de', 45, 3),
(7, 'Technico-commercial e - stage pré-embauche', 'Son rôle consiste à : 1. traiter les leads et les qualifier 2. assurer la démonstration de la solution 3. supporter les prospects jusqu\'à l\'achat le la solution.Critères pour le poste: Technico-commercial e - Stage pré-embauche', 60, 2);

-- --------------------------------------------------------

--
-- Structure de la table `stagiaire`
--

DROP TABLE IF EXISTS `stagiaire`;
CREATE TABLE IF NOT EXISTS `stagiaire` (
  `IdStagiaire` int NOT NULL AUTO_INCREMENT,
  `DateArrivee` date DEFAULT NULL,
  `DateDepart` date DEFAULT NULL,
  `JourCongePris` int DEFAULT NULL,
  `NumBadge` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `NoteTravail` double DEFAULT NULL,
  `NoteComportement` double DEFAULT NULL,
  `NoteRapport` double DEFAULT NULL,
  `IdCandidat` int NOT NULL,
  `IdResponsable` int DEFAULT NULL,
  PRIMARY KEY (`IdStagiaire`),
  KEY `IdCandidat` (`IdCandidat`),
  KEY `IdResponsable` (`IdResponsable`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `stagiaire`
--

INSERT INTO `stagiaire` (`IdStagiaire`, `DateArrivee`, `DateDepart`, `JourCongePris`, `NumBadge`, `NoteTravail`, `NoteComportement`, `NoteRapport`, `IdCandidat`, `IdResponsable`) VALUES
(7, '2020-01-01', '2020-02-01', 10, '123456', 7.99, 6.99, 13.5, 1, 3),
(8, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 3),
(9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 2),
(10, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 2);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `candidat`
--
ALTER TABLE `candidat`
  ADD CONSTRAINT `candidat_ibfk_1` FOREIGN KEY (`IdEcole`) REFERENCES `ecole` (`IdEcole`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `candidature`
--
ALTER TABLE `candidature`
  ADD CONSTRAINT `candidature_ibfk_1` FOREIGN KEY (`idC`) REFERENCES `candidat` (`IdCandidat`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `candidature_ibfk_2` FOREIGN KEY (`idS`) REFERENCES `stage` (`IdS`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `compters`
--
ALTER TABLE `compters`
  ADD CONSTRAINT `compters_ibfk_1` FOREIGN KEY (`IdR`) REFERENCES `responsable` (`IdR`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `comptesc`
--
ALTER TABLE `comptesc`
  ADD CONSTRAINT `comptesc_ibfk_1` FOREIGN KEY (`IDC`) REFERENCES `candidat` (`IdCandidat`);

--
-- Contraintes pour la table `stage`
--
ALTER TABLE `stage`
  ADD CONSTRAINT `stage_ibfk_1` FOREIGN KEY (`IdR`) REFERENCES `responsable` (`IdR`);

--
-- Contraintes pour la table `stagiaire`
--
ALTER TABLE `stagiaire`
  ADD CONSTRAINT `stagiaire_ibfk_1` FOREIGN KEY (`IdCandidat`) REFERENCES `candidat` (`IdCandidat`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `stagiaire_ibfk_2` FOREIGN KEY (`IdResponsable`) REFERENCES `responsable` (`IdR`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
