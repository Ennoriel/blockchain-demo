package io.soprasteria.blockchain.compo;

public class Transaction {

    private String sender;
    private String recipient;
    private int amount;

    Transaction(String sender, String recipient, int amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getAmount() {
        return amount;
    }
}
