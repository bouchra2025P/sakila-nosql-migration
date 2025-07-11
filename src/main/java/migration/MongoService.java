package migration;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;
import java.util.stream.Collectors;

public class MongoService {

    private final MongoDatabase db;

    public MongoService() {
        this.db = MongoConfig.getMongoDatabase();
    }

    public void saveLanguage(Language language) {
        MongoCollection<Document> col = db.getCollection("languages");
        Document doc = new Document()
                .append("language_id", language.getLanguageId())
                .append("name", language.getName())
                .append("last_update", language.getLastUpdate());
        col.insertOne(doc);
    }

    public void saveCategory(Category category) {
        MongoCollection<Document> col = db.getCollection("categories");
        Document doc = new Document()
                .append("category_id", category.getCategoryId())
                .append("name", category.getName())
                .append("last_update", category.getLastUpdate());
        col.insertOne(doc);
    }

    public void saveActor(Actor actor) {
        MongoCollection<Document> col = db.getCollection("actors");
        Document doc = new Document()
                .append("actor_id", actor.getActorId())
                .append("first_name", actor.getFirstName())
                .append("last_name", actor.getLastName())
                .append("last_update", actor.getLastUpdate());
        col.insertOne(doc);
    }

    public void saveFilm(Film film) {
        MongoCollection<Document> col = db.getCollection("films");

        Document langDoc = new Document()
                .append("language_id", film.getLanguage().getLanguageId())
                .append("name", film.getLanguage().getName());

        List<Document> catDocs = film.getCategories().stream()
                .map(c -> new Document("category_id", c.getCategoryId())
                        .append("name", c.getName()))
                .collect(Collectors.toList());

        List<Document> actorDocs = film.getActors().stream()
                .map(a -> new Document()
                        .append("actor_id", a.getActorId())
                        .append("first_name", a.getFirstName())
                        .append("last_name", a.getLastName()))
                .collect(Collectors.toList());

        Document filmDoc = new Document()
                .append("film_id", film.getFilmId())
                .append("title", film.getTitle())
                .append("description", film.getDescription())
                .append("release_year", film.getReleaseYear())
                .append("language", langDoc)
                .append("categories", catDocs)
                .append("actors", actorDocs)
                .append("last_update", film.getLastUpdate());

        col.insertOne(filmDoc);
    }
}
