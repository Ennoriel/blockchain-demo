# Read Me

## Licence

Projet traduit du projet [Blockchain4dev](https://github.com/pagesjaunes/blockchain4dev).

## Coding DOJO

### Intro

Ce coding dojo est découpé en étapes et sous-étapes. Pour chaque sous-étape, la session sera ainsi organisée :
  1. se placer sur le tag git relatif à l'étape
  2. lire la description de l'étape sur ce fichier et résoudre l'étape.
  3. passer à l'étape suivante (validation de l'animateur) en changeant de tag git. La solution sera expliquée sur ce fichier.

Ne pas hésiter à lire le contenu de toute l'étape avant de commencer !

### Démo

Une petite démo de la bockchain finale avant de démarrer !

### Etape 0 : J'aime git !

passer à l'étape 1.1 (tag git V.1.1)

``` java
git checkout V.1.1.
```

### Etape 1 : je veux ma blockchain !

On va construire notre blockchain avec des transactions !

Hypothèses de notre super blockchain :

  - La blockchain est constituée de blocks et de transaction en attente.
  - Un block est constitué d'un index, d'une date et de transactions.
  - Une transaction est constituée d'un émetteur, d'un récepteur et d'une somme.

De manière générale, les opérations sur notre blockchain sera réalisé par appel rest. Un fichier de configuration Insomnia est présent dans les sources. N'hésitez pas à tester votre blockchain tout au long du coding dojo.

Vous disposez d'ailleurs au début du coding dojo d'un endpoint pour retourner la blockchain :

``` java
@GetMapping("/chain")
public ResponseEntity fullChain() {
    return new ResponseEntity(blockchain, HttpStatus.OK);
}
```

Le fonctionnement par api est purement arbitraire et ce n'est pas le cas pour certaines étapes dans la vie réelle.

#### Etape 1.1 : un début

Observez le code source à votre disposition, tests unitaires compris. ;)

La blochain (initialisé dans la classe Api()) a disposition est aujourd'hui bien incomplète. Pour fonctionner, toute blockchain a besoin d'un block initial. Initialisez donc un nouveau block à l'initialisation de la blockchain.
