package ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB;

import org.jongo.Jongo;

import com.mongodb.MongoClient;


public class MongoConnection {

    private static MongoConnection INSTANCE;

    private MongoClient client;
    private Jongo jongo;

    public static synchronized MongoConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MongoConnection();
        }
        return INSTANCE;
    }

    @SuppressWarnings("deprecation")
    private MongoConnection() {
        this.client = new MongoClient("localhost", 27017);
        this.jongo = new Jongo(this.client.getDB("bichomonDB"));
    }

    public Jongo getJongo() {
        return this.jongo;
    }
}