package sdu.se9.tv2.management.system.persistence;

import org.json.simple.parser.ParseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import sdu.se9.tv2.management.system.domain.Producer;

/**
 * Implementation of the IPersistenceProducer interface
 */
public class PersistenceProducer implements IPersistenceProducer {

    private static PersistenceProducer instance = null;

    public static PersistenceProducer getInstance() {
        if (instance == null) {
            instance = new PersistenceProducer();
        }

        return instance;
    }

    /**
     * Creates a new instance of the PersistenceProducer class
     */
    private PersistenceProducer() {

    }

    /**
     * Creates a new producer and saves it to file
     * @param producerName The name of the new producer
     * @return
     */
    public Producer createProducer (String producerName) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("INSERT INTO Producer (name) VALUES (?) RETURNING id;");
        stmt.setString(1,producerName);

        ResultSet rs = stmt.executeQuery();

        rs.next();

        int id = rs.getInt(1);


        return new Producer(id,producerName);
    }

    /**
     * Gets a producer by ID
     * @param producerID The ID of the producer
     * @return
     */
    public Producer getProducer (int producerID) throws SQLException{
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM producer WHERE id = ?");
        stmt.setInt(1, producerID);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String name = rs.getString("name");
            return new Producer(producerID, name);
        }
        return null;
    }

    public Producer getProducer (String producerName) throws SQLException{
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM producer WHERE name = ?");
        stmt.setString(1, producerName);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            return new Producer(id, producerName);
        }
        return null;
    }
}