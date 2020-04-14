package sdu.se9.tv2.management.system;

public interface IPersistenceProducer {
    Producer createProducer (String producerName);
    Producer getProducer (int producerID);
}
