package sdu.se9.tv2.management.system;

public class ProducerAccount extends Account {
    private int producerId;

    ProducerAccount(int accountId, String accountUsername, String accountPassword, int producerId) {
        super(accountId, accountUsername, accountPassword, "producer");

        this.producerId = producerId;
    }

    public int getProducerId() {
        return this.producerId;
    }

    @Override
    public String toString() {
        return "ProducerAccount{" +
                "id='" + getId() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", type='" + getType() + '\'' +
                ", producerId='" + getProducerId() + '\'' +
                '}';
    }
}
