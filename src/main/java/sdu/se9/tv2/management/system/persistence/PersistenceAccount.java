package sdu.se9.tv2.management.system.persistence;

import java.sql.PreparedStatement;

import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.domain.accounts.AdminAccount;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;
import sdu.se9.tv2.management.system.domain.accounts.SystemAdminAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersistenceAccount implements IPersistenceAccount {
    public PersistenceAccount() {}

    public Account getMatchingAccount(String username, String password) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM Account WHERE username = ? AND password = ? LIMIT 1;");
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            // No match
            return null;
        }

        int id = rs.getInt("id");
        int producerID =  rs.getInt("producerID");
        String type = rs.getString("type");

        if (type.equals("producer")) {
            return new ProducerAccount(id, username, password, producerID);
        } else if (type.equals("admin")) {
            return new AdminAccount(id, username, password);
        } else if (type.equals("systemadmin")) {
            return new SystemAdminAccount(id, username, password);
        }
        return null;
    }

    public AdminAccount createAdminAccount(String username, String password) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("INSERT INTO Account (username, password, type, producerID) VALUES (?, ?, 'admin', NULL) RETURNING id;");
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        int id = rs.getInt(1);


        return new AdminAccount(id, username, password);
    }

    public SystemAdminAccount createSystemAdminAccount(String username, String password) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("INSERT INTO Account (username, password, type, producerID) VALUES (?, ?, 'systemadmin', NULL) RETURNING id;");
        stmt.setString(1, username);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        int id = rs.getInt(1);


        return new SystemAdminAccount(id, username, password);
    }

    public ProducerAccount createProducerAccount(String username, String password, int producerId) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("INSERT INTO Account (username, password, type, producerID) VALUES (?, ?, 'producer', ?) RETURNING id;");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setInt(3, producerId);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        int id = rs.getInt(1);


        return new ProducerAccount(id, username, password, producerId);
    }

    /**
     * Gets the aomunt of accounts for the specific producer
     *
     * @param producerId
     * @return
     */
    public int getProducerAccountCount(int producerId) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM Account WHERE producerID = ?");
        stmt.setInt(1, producerId);

        ResultSet rs = stmt.executeQuery();
        int count = 0;
        while (rs.next()) {
            count++;
        }

        return count;
    }
}

