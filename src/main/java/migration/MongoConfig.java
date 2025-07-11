package migration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Provides a singleton MongoDatabase instance.
 */
public final class MongoConfig {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "sakila";
    private static final MongoClient CLIENT = MongoClients.create(URI);

    private MongoConfig() {}

    public static MongoDatabase getMongoDatabase() {
        return CLIENT.getDatabase(DB_NAME);
    }
}
