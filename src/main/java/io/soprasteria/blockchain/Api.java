package io.soprasteria.blockchain;

import io.soprasteria.blockchain.compo.Blockchain;
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
}
