	---------- Le sujet ----------
	
	Gestion de l’attribution de stages en entreprise	

	---------- Pour exécuter le programme ----------
  
	Vous aurez besoin d'installer:
		- WAMP SERVER ou bien XAMP.
		- JavaFx la dernière version.
		- Eclipse 2022-12 au plus, puisque les versions de 2023 n'acceptent pas la liaison avec le logiciel SceneBuilder.
	
	Les paramètres de d'exécution doivent etre modifiés de telle façon qu'ils prennent en arguments la ligne suivante :
		--module-path="C:\JavaFx\lib" --add-modules=javafx.controls,javafx.fxml
		or: C:\JavaFx\lib est le chemin du dossier de la laibrairie JavaFx.
	
	Le fichier jar du Driver MySQL doit etre importé dans le projet.
	
	Le dossier extrait du fichier ZIP JavaFx doit etre ajouté comme une libraire externe en Eclipse.
	

	---------- Le contenu du répertoire ----------
	
	ApplicationJava: 	Ce dossier contient l'ensemble des fichiers qui constituent notre projet.
		- projet: 	le projet Eclipse.
		- toAddPro:	le driver de la base de donnée à ajouter dans le projet Eclipse et les fichier de la librairie JavaFx.
		- bddApp:	la base de données MySQL à importer dans le logiciel WAMP SERVER ou bien XAMP SERVER.
