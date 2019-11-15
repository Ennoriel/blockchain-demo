package io.soprasteria.blockchain.compo;

import org.junit.jupiter.api.BeforeEach;

import java.util.Collections;

class BlockTest {

    private Block block;
    private String blockJson;

    @BeforeEach
    void setUp() {
        block = new Block(1, Collections.singletonList(new Transaction("testSender", "testRecipient", 1)));
        blockJson = String.format("{\"index\":1,\"date\":%s,\"transactions\":[{\"sender\":\"testSender\",\"recipient\":\"testRecipient\",\"amount\":1}],\"previousHash\":\"testPreviousHash\",\"nonce\":0}", block.getDate().getTime());
    }
}