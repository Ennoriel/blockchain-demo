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

Solution :

``` java
this.blockChain.add(new Block(1, this.pendingTransactions));
```

L'instanciation du premier block est réalisée simplement dans le constructeur de la blochain. Cette initialisation est un peu arbitraire.

#### Etape 1.2 : Ajouter une transaction

Un endpoint a été rajouté dans la classe Api :

``` java
@PostMapping("/transactions")
public ResponseEntity newTransaction(@RequestBody Transaction transaction) {

    blockchain.newTransaction(transaction);
    return new ResponseEntity("{\"message\": \"La transaction est en attente\"}", HttpStatus.CREATED);
}
```

Un TU a également été ajouté. On remarquera d'ailleurs que celui-ci ne plante pas alors que la méthode n'est pas implémenté...

Complétez la méthode newTransaction(). N'hésitez pas à réflechir pour compléter le TU.

Solution :

``` java
getPendingTransactions().add(transaction);
```

Les nouvelles transactions sont intégrées aux transactions en attente. Elles ne sont pas ajoutées à un bloc tout de suite. Par contre, cela signifie qu'elles ne sont pas encore effectives. Elles ne seront effectivés qu'une fois le block "validé".

#### Etape 1.3 : Ajouter un block

Un endpoint a été rajouté pour "miner" (valider) un nouveau block dans la classe Api :

``` java
@PostMapping("/blocks")
public ResponseEntity newBlock() {

    Block block = blockchain.addBlock();
    return new ResponseEntity(block.jsonify(), HttpStatus.CREATED);
}
```

Un TU a également été ajouté. On remarquera encore une fois que celui-ci ne plante pas alors que la méthode n'est pas implémenté...

Complétez la méthode addBlock(). L'indice du nouveau block doit être incrémenté par rapport au bloc précédent. Le block doit contenir les transactions en attente. Ces dernières doivent être réinitialisées.

Solution :

``` java
Block block = new Block(lastIndex() + 1, new ArrayList<>(pendingTransactions));
getBlocks().add(block);
getPendingTransactions().clear();
```

Cet ajout de block constitue le minage du block. Nous n'avons pas encore de preuve de travail, mais on a une chaine fonctionnelle.

Le fait de miner le block par api est arbitraire. Dans la vie réelle, les règles déclenchant le minage d'un block sont souvent différentes (tous les X blocks, X minutes, etc.).

### Etape 2 : et le minage dans tout ça ?

Notre blockchain est fonctionnelle mais... tout le monde peut l'usurper ! Elle est où la preuve de travail inviolable et si polluante du bitcoin ?

Pour réaliser une preuve de travail, il faut compléter notre blockchain avec deux attributs supplémentaires.

Le hash est une empreinte numérique servant à identifier rapidement la donnée initiale, au même titre qu'une signature pour identifier une personne :

``` java
private String previousHash;
```

``` java
@NotNull String hash() {
    // Calcule le hash d'un block. A compléter au cours de l'étape 2.3
    return jsonify();
}

@NotNull
public String jsonify() {
    // retourne la représentation json d'un block. A compléter au cours de l'étape 2.3
    return "";
}
```

Le nonce est un **nombre arbitraire** d'un block destiné à être utilisé pour valider une preuve de travail.

``` java
this.nonce = 0;
```

La preuve de travail est une fonction mathématique/algorithmique qui doit être difficilement réalisable pour le demandeur, mais facilement vérifiable pour un tiers. Dans notre cas, nous allons trouver un hash de notre block qui commence par n zéros. Nous pouvons réaliser ce travail en faisant varier la valeur du nonce.

En d'autres termes, un block validé par la preuve de travail sera un block dont sa fonction de hashage retournera un hash commençant par n zéros. "n" constitue la difficulté de la preuve de travail. Nous la fixons arbitrairement à 3 pour notre exemple.

``` java
private final static int DIFFICULTY = 3;
```

#### Etape 2.1 : le minage

L'api déclanchant le minage a été ajouté dans l'étape 1.2. Cette méthode ne réalisait pas de preuve de travail.

La ligne suivante a été ajoutée à la méthode addBlock(). C'est elle qui constitue le minage :

``` java
block.mine(DIFFICULTY);
```

Complétez les appels au constructeur Block() qui se voit modifié avec le hash du block précédant.

Complétez également la méthode mine(). Celle-ci doit permettre d'augmenter la valeur nonce jusqu'à ce que la preuve de travail (méthode proofOfWork() à utiliser) nous renvoie une valeur vraie.

Solution :

``` java
while (!this.proofOfWork(difficulty)) {
    incrementNonce();
}
```

#### Etape 2.2 : la méthode proofOfWork()

Complétez la méthode proofOfWork(). Elle permet de valider que le hash de la représentation json d'un block (méthode hash() à utiliser) est valide (qu'il commence par 3 zéros).

Solution :

``` java
return ("0".repeat(difficulty)).equals(this.hash().substring(0, difficulty));
```

#### Etape 2.3 : représentation json d'un block

La méthode hash() prendra pour attribut la représentation json du block. Cette représentation sera retournée par la méthode jsonify(). Complétez la méthode jsonify() qui transformera le block en json :

``` java
@NotNull public String jsonify() {
    String json = "";

    // conversion de l'objet en json

    return json;
}
```

Remplacez la méthode methodeDeHashashage par une méthode de hashage de type sha 256 en hexadécimal.

``` java
@NotNull String hash() {
    return methodeDeHashashage(jsonify());
}
```

Solution :

``` java
@NotNull
public String jsonify() {
    ObjectMapper mapper = new ObjectMapper();
    String json = "";
    try {
        json = mapper.writeValueAsString(this);
    } catch (JsonProcessingException e) {
        e.printStackTrace();
    }
    return json;
}
```

``` java
return DigestUtils.sha256Hex(jsonify());
```

### Etape 3 : Je suis tout seul, c'est trop nul !

#### Etape 3.1 : connaitre les autres noeuds

La liste des noeuds a été ajoutée à la blockchain :

``` java
private List<String> nodes;
```

L'ajout de noeud a été fait arbitrairement par api. Complétez l'api /nodes/register.

Solution :

``` java
@PostMapping("/nodes/register")
public ResponseEntity newBlock(@RequestBody List<URL> nodes) {

    // si la liste est vide, on arrête le traitement

    // sinon, on vérifie que les noeuds ne sont pas déjà connus de la blockchain

    // on ajoute les noeuds à la blockchain

}
```

#### Etape 3.2 : remplacer la blockchain :(

Le principe de la blockchain de type bitcoin repose dans le fait d'une preuve de travail complexe à résoudre. Lorsque le voisin réussit à résoudre la preuve de travail avant soi, il faut prendre sa représentation de la blockchain pour remplacer la nôtre. Dans ce cas, il est néanmoins nécessaire de vérifier que sa blockchaine est valide (d'ou le besoin pour une preuve de travail d'être difficilement réalisable pour le demandeur, mais facilement vérifiable pour un tiers)

L'api suivante a été ajouté :

``` java
@GetMapping("/nodes/resolve")
public ResponseEntity consensus() {
    String message = blockchain.resolveConflicts() ? "La chaine a été remplacée" : "La chaine est déjà la référence";
    return new ResponseEntity(String.format("{\"message\": \"%s\"}", message), HttpStatus.OK);
}
```

Complétez la méthode resolveConflicts() pour résoudre les conflits ainsi que les méthodes de vérification de la blockchain et des blocks : 

Solution :

``` java
public boolean resolveConflicts() {

    List<String> neighbours = getNodes();
    Blockchain newBlockchain = null;
    int maxLength = getBlocks().size();

    RestTemplate restTemplate = new RestTemplate();

    for (String node : neighbours) {
        Blockchain neighbourBlockChain = restTemplate.getForObject(String.format("http://%s/chain", node), Blockchain.class);
        if (neighbourBlockChain != null) {
            int neighbourBlockChainLength = neighbourBlockChain.getBlocks().size();

            if (neighbourBlockChainLength > maxLength && isBlockchainValid(neighbourBlockChain)) {
                maxLength = neighbourBlockChainLength;
                newBlockchain = neighbourBlockChain;
            }
        }
    }

    if (newBlockchain != null) {
        setBlocks(blocks);
        return true;
    }
    return false;
}
```

``` java
private boolean isFirstBlockValid(@NotNull Blockchain blockchain) {
    Block firstBlock = blockchain.getBlocks().get(0);

    if (firstBlock.getIndex() != 1)
        return false;

    return firstBlock.getPreviousHash() == null;
}
```

``` java
private boolean isBlockValid(@NotNull Block previousBlock, @NotNull Block currentBlock) {
    if (currentBlock.getIndex() != previousBlock.getIndex() + 1)
        return false;

    if (!currentBlock.getPreviousHash().equals(previousBlock.hash()))
        return false;

    return currentBlock.proofOfWork(DIFFICULTY);
}
```

``` java
private boolean isBlockchainValid(@NotNull Blockchain blockchain) {
    if (!blockchain.isFirstBlockValid(blockchain))
        return false;

    int index = 1;
    while (index < getBlocks().size()) {
        if (!isBlockValid(getBlock(index), getBlock(index - 1)))
            return false;
        index++;
    }
    return true;
}
```
