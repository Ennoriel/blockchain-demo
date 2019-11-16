package io.soprasteria.blockchain.compo;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<Block> blocks;
    private List<Transaction> pendingTransactions;

    public Blockchain() {
        this.blocks = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();

        this.blocks.add(new Block(1, new ArrayList<>(this.pendingTransactions)));
    }

    public void newTransaction(@NotNull Transaction transaction) {

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
}
