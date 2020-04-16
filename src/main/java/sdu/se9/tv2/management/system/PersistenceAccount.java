package sdu.se9.tv2.management.system;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sdu.se9.tv2.management.system.exceptions.UsernameAlreadyExistsException;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.Iterator;

public class PersistenceAccount implements IPersistenceAccount {

    private Persistence persistence = new Persistence("account.json");

    private int lastID = -1;

    private ArrayList<Account> accounts = new ArrayList<Account>();

    public PersistenceAccount() {
        this.read();
    }

    public Account getMatchingAccount(String username, String password) {
        for (int i = 0; i < this.accounts.size(); i++) {
            Account element = this.accounts.get(i);

            if (element.getUsername().equals(username) && element.getPassword().equals(password)) {
                return element;
            }
        }

        return null;
    }

    public boolean usernameTaken(String username) {
        for (int i = 0; i < this.accounts.size(); i++) {
            Account element = this.accounts.get(i);

            if (element.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    public AdminAccount createAdminAccount(String username, String password) throws UsernameAlreadyExistsException {
        if (this.usernameTaken(username)) {
            throw new UsernameAlreadyExistsException("An account with username already exists");
        }

        lastID++;
        AdminAccount account = new AdminAccount(lastID, username, password);
        accounts.add(account);

        this.write();

        return account;
    }

    public ProducerAccount createProducerAccount(String username, String password, int producerId) throws UsernameAlreadyExistsException {
        if (this.usernameTaken(username)) {
            throw new UsernameAlreadyExistsException("An account with username already exists");
        }

        lastID++;
        ProducerAccount account = new ProducerAccount(lastID, username, password, producerId);
        accounts.add(account);

        this.write();

        return account;
    }

    /**
     * Gets the aomunt of accounts for the specific producer
     *
     * @param producerId
     * @return
     */
    public int getProducerAccountCount(int producerId) {
        int count = 0;
        for (int i = 0; i < this.accounts.size(); i++) {
            Account element = this.accounts.get(i);
            if (!element.getType().equals("producer")) {
                continue;
            }
            ProducerAccount account = (ProducerAccount)element;
            if (account.getProducerId() == producerId) {
                count++;
            }
        }
        return count;
    }

    private void read() {
        JSONObject obj = null;

        // Try and read the file
        try {
            obj = this.persistence.read();
        } catch (ParseException err) {
            err.printStackTrace();
        }

        if (obj != null) {
            // The file exists and has correct formatting, get lastID and list of producers

            // obj.get("lastID") returns Long
            this.lastID = Math.toIntExact((Long)obj.get("lastID"));

            JSONArray objList = (JSONArray)obj.get("list");

            ArrayList<Account> parsedList = new ArrayList<Account>();

            // Parse producer list
            Iterator<JSONObject> iterator = objList.iterator();
            while (iterator.hasNext()) {
                // Get element of the list array
                JSONObject element = iterator.next();
                // Parse the JSONObject using Producer.parseJSON

                try {
                    parsedList.add(Account.parseJSON(element));
                } catch (InvalidClassException err) {
                    // If a check for the account type does not exist then an exception will be thrown
                    err.printStackTrace();
                }
            }

            // Set producer list
            accounts = parsedList;
        }
    }

    private void write() {
        // Create JSONObject to save
        JSONObject obj = new JSONObject();

        // JSONArray that contains the producers
        JSONArray list = new JSONArray();

        // Go through producer list and parse as JSON objects
        for (int i = 0; i < this.accounts.size(); i++) {
            list.add(Account.parseJSON(this.accounts.get(i)));
        }

        // Add lastID to JSON object
        obj.put("lastID", lastID);
        // Add list to JSON object
        obj.put("list", list);

        // Overwrite the file with new JSON object
        this.persistence.write(obj);
    }
}

