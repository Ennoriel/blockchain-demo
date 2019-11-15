package io.soprasteria.blockchain.compo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class BlockchainTest {

    private Blockchain blockchain;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        blockchain = new Blockchain();
        transaction = new Transaction("testSender", "testRecipient", 1);
    }
}