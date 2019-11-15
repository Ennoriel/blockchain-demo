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

    Block(@NotNull int index, @NotNull List<Transaction> transactions) {
        this.index = index;
        this.date = new Date();
        this.transactions = transactions;
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
}