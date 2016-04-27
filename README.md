# Interactive_Java_Book
Le but de ce projet est d'implanter un système de livre interactif, dans le même principe que les ipython notebooks.

Il permettra :
    de visualiser dans un browser Web des exercices écrit au format MarkDown.
    de permettre à un utilisateur de répondre aux questions d'un exercice dans le browser en écrivant du code Java avec une évaluation interactive en utilisant une interface simple AJAX/REST.

### Version : 1.0

Requirements :
* Java 9 installed
* A folder named “exercice” in the same folder as the executable jar file
* A web browser
* The “webroot” folder in the same folder as the executable jar file.

Your folder should look like this at the beginning :

* docs
* exercice
* libs
* vert.x-3.0.0
* webroot
* build.properties
* build.xml

You must build the projet using the command :  ant all
This will produce the compiled classes in the “classes folder” , the api in “docs/api” and executable jar file with his manifest file in a “dest” folder.

The folder should look like this once you have runned command .

* classes
* dest
* docs
* exercice
* libs
* vert.x-3.0.0
* webroot
* build.properties
* build.xml
* How to run the program :
* Go into the project folder.
* Run “ant” command line

To execute the jar file:
        <path to java 9>/java -jar ./dest/jshellbook.jar

You must run the program with Java 9, if it isn’t started with it, you won’t be able to ask to validate the exercise you’re working on.
