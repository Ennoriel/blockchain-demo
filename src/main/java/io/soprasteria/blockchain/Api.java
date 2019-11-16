package io.soprasteria.blockchain;

import io.soprasteria.blockchain.compo.Block;
import io.soprasteria.blockchain.compo.Blockchain;
import io.soprasteria.blockchain.compo.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Api {

    private Blockchain blockchain = new Blockchain();

    @GetMapping("/chain")
    public ResponseEntity fullChain() {
        return new ResponseEntity(blockchain, HttpStatus.OK);
    }

    @PostMapping("/transactions")
    public ResponseEntity newTransaction(@RequestBody Transaction transaction) {

        blockchain.newTransaction(transaction);
        return new ResponseEntity("{\"message\": \"La transaction est en attente\"}", HttpStatus.CREATED);
    }

    @PostMapping("/blocks")
    public ResponseEntity newBlock() {

        Block block = blockchain.addBlock();
        return new ResponseEntity(block, HttpStatus.CREATED);
    }

    @PostMapping("/nodes/register")
    public ResponseEntity newBlock(@RequestBody List<URL> nodes) {

        if (nodes == null || nodes.isEmpty())
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        nodes = nodes.stream()
                .filter(node -> !blockchain.getNodes().contains(node.getAuthority()))
                .collect(Collectors.toList());

        if (nodes.size() == 0)
            return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);

        nodes.forEach(node -> blockchain.registerNode(node));

        return new ResponseEntity("{\"message\": \"Les noeuds ont été ajoutés\"}", HttpStatus.CREATED);
    }

    @GetMapping("/nodes/resolve")
    public ResponseEntity consensus() {
        String message = blockchain.resolveConflicts() ? "La chaine a été remplacée" : "La chaine est déjà la référence";
        return new ResponseEntity(String.format("{\"message\": \"%s\"}", message), HttpStatus.OK);
    }
}
