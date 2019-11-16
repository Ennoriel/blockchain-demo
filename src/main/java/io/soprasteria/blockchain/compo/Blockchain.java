package io.soprasteria.blockchain.compo;

import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URL;
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

    private boolean isFirstBlockValid(@NotNull Blockchain blockchain) {
        Block firstBlock = blockchain.getBlocks().get(0);

        if (firstBlock.getIndex() != 1)
            return false;

        return firstBlock.getPreviousHash() == null;
    }

    private boolean isBlockValid(@NotNull Block previousBlock, @NotNull Block currentBlock) {
        if (currentBlock.getIndex() != previousBlock.getIndex() + 1)
            return false;

        if (!currentBlock.getPreviousHash().equals(previousBlock.hash()))
            return false;

        return currentBlock.proofOfWork(DIFFICULTY);
    }

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

    public void registerNode(URL address) {
        getNodes().add(address.getAuthority());
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
