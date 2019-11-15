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

    @Test
    void newTransaction() {
        assertEquals(0, blockchain.getPendingTransactions().size(), "Pending Transaction should be 0 after chain initialization");

        blockchain.newTransaction(transaction);

        assertEquals(1, blockchain.getPendingTransactions().size(), "Pending Transaction should be 1 after adding 1 transaction");
        assertEquals(transaction, blockchain.getPendingTransactions().get(0), "Pending Transaction should be made of the one added");
    }
}