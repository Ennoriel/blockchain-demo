package io.soprasteria.blockchain.compo;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private final static int DIFFICULTY = 3;

    private List<Block> blocks;
    private List<Transaction> pendingTransactions;
    private List<String> nodes;

    public Blockchain() {
        this.blocks = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        this.nodes = new ArrayList<>();

        this.blocks.add(new Block(1, new ArrayList<>(this.pendingTransactions), null));
    }

    public void newTransaction(@NotNull Transaction transaction) {
        getPendingTransactions().add(transaction);
    }

    @NotNull public Block addBlock() {
        Block block = new Block(lastIndex() + 1, new ArrayList<>(pendingTransactions), lastBlock().hash());
        block.mine(DIFFICULTY);
        getBlocks().add(block);
        getPendingTransactions().clear();
        return block;
    }

    private Block lastBlock() {
        return getBlocks().get(getBlocks().size() - 1);
    }

    private int lastIndex() {
        return blocks.size();
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Block getBlock(int index) {
        return getBlocks().get(index);
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public List<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(List<Transaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    public List<String> getNodes() {
        return nodes;
    }
}
