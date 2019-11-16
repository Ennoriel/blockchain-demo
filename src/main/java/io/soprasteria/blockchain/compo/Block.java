package io.soprasteria.blockchain.compo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class Block {

    private int index;
    private Date date;
    private List<Transaction> transactions;
    private String previousHash;
    private int nonce;

    Block(@NotNull int index, @NotNull List<Transaction> transactions, String previousHash) {
        this.index = index;
        this.date = new Date();
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.nonce = 0;
    }

    @NotNull String hash() {
        // Calcule le hash d'un block. A compléter au cours de l'étape 2.3
        return jsonify();
    }

    @NotNull
    public String jsonify() {
        // retourne la représentation json d'un block. A compléter au cours de l'étape 2.3
        return "";
    }

    @NotNull boolean proofOfWork(@NotNull int difficulty) {
        return ("0".repeat(difficulty)).equals(hash().substring(0, difficulty));
    }

    void mine(@NotNull int difficulty) {
        while (!this.proofOfWork(difficulty)) {
            incrementNonce();
        }
    }

    public int getIndex() {
        return index;
    }

    public Date getDate() {
        return date;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Transaction getTransaction(int index) {
        return getTransactions().get(index);
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public int getNonce() {
        return nonce;
    }

    private void incrementNonce() {
        this.nonce++;
    }
}