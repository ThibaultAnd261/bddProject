# bddProject

Clonez le projet 
```bash
git clone https://github.com/ThibaultAnd261/bddProject.git
```

Importez le fichier sql dans votre gestionnaire de base de données
```bash
Fichier dans sql/projet.sql
```

Compilez le projet, avec <mysql_connector> étant le chemin relatif à votre fichier .jar 
```bash
javac -cp <mysql_connector> -d . .\ihm\*.java
```

Lancez le projet
```bash
java -cp <mysql_connector> ihm.Main
```

Supprimer les .class ? 
```bash
rm .\ihm\*.class
rm .\ihm\View\Component\*.class
rm .\ihm\View\*.class
rm .\ihm\Model\*.class
rm .\ihm\DB\*.class
rm .\ihm\Controller\*.class
```


