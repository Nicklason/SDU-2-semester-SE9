package sdu.se9.tv2.management.system.persistence;

import sdu.se9.tv2.management.system.domain.Producer;

/**
 * Interface for persistence for producers
 */
public interface IPersistenceProducer {
    /**
     * Create a new producer
     * @param producerName The name of the new producer
     * @return
     */
    Producer createProducer (String producerName);

    /**
     * Get a producer by ID
     * @param producerID The ID of the producer
     * @return
     */
    Producer getProducer (int producerID);

    Producer getProducer (String producerName);
}
