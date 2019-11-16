package io.soprasteria.blockchain.compo;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockTest {

    private Block block;
    private String blockJson;

    @BeforeEach
    void setUp() {
        block = new Block(1, Collections.singletonList(new Transaction("testSender", "testRecipient", 1)), "testPreviousHash");
        blockJson = String.format("{\"index\":1,\"date\":%s,\"transactions\":[{\"sender\":\"testSender\",\"recipient\":\"testRecipient\",\"amount\":1}],\"previousHash\":\"testPreviousHash\",\"nonce\":0}", block.getDate().getTime());
    }

    @Test
    void jsonify() {
        Assert.notNull(block.jsonify(), "jsonify returns null value.");
        assertEquals(blockJson, block.jsonify(), "jsonify returns incorrect value.");

    }

    @Test
    void hash() {
        Assert.notNull(block.hash(), "hash method returns null value.");
        assertEquals(block.hash(), DigestUtils.sha256Hex(blockJson), "hash method returns incorrect value.");
    }

    @Test
    void proofOfWork() {
        Assert.isTrue(block.proofOfWork(0), "proofOfWork method doesn't validate the 0 difficulty level.");
        Assert.isTrue(!block.proofOfWork(18), "proofOfWork method validates 18 difficulty level but shouldn't.");
    }

    @Test
    void mineAndProofOfWork() {
        block.mine(4);
        Assert.isTrue(block.proofOfWork(4), "proofOfWork method doesn't validate the 2 difficulty level after mining.");
    }
}