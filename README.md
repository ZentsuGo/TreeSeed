# TreeSeed

TreeSeed est un projet élémentaire que j'ai débuté en 2019.

C'est un logiciel qui permet de générer des arbres plus ou moins aléatoires en utilisant une grammaire générative nommée L-System.
Selon un ensemble de règles dépendantes d'une grammaire et d'une génération aléatoire d'une séquence de caractères de cette grammaire,
on obtient une génération de branches successives donnant un arbre en 2D.

La bibliothèque Graphics2D de java.awt a été utilisé dans ce projet et est codé entièrement en Java.

Exemple d'utilisation :

Pour la grammaire générative suivante :

![alt text](https://github.com/zentsugo/TreeSeed/blob/main/treeseed_3.PNG?raw=true)

L'axiome (axiom) représente l'initialisation de la séquence de caractères de la grammaire générative.
Les variables représentent les caractères qui composent la grammaire générative.
Les règles (rules) représentent un ensemble qui étant données un caractère vont renvoyer une séquence de caractères, ce sont des fonctions qui permettent la génération successive des arbres.


Premières générations (2-3 clics sur le bouton génération) :

![alt text](https://github.com/zentsugo/TreeSeed/blob/main/treeseed_1.PNG?raw=true)


Après quelques générations :

![alt text](https://github.com/zentsugo/TreeSeed/blob/main/treeseed_2.PNG?raw=true)
