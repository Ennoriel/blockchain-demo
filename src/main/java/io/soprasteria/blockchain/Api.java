package io.soprasteria.blockchain;

import io.soprasteria.blockchain.compo.Block;
import io.soprasteria.blockchain.compo.Blockchain;
import io.soprasteria.blockchain.compo.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
