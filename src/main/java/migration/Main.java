package migration;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("🚀 Starting Migration from PostgreSQL to Redis and MongoDB...");

            PostgresService pgService = new PostgresService();
            RedisService redisService = new RedisService("localhost", 6379);
            MongoService mongoService = new MongoService();

            // Countries to Redis
            List<Country> countries = pgService.getCountries();
            countries.forEach(redisService::saveCountry);
            System.out.println("✅ Countries migrated to Redis: " + countries.size());

            // Cities to Redis
            List<City> cities = pgService.getCities();
            cities.forEach(redisService::saveCity);
            System.out.println("✅ Cities migrated to Redis: " + cities.size());

            // Languages to MongoDB
            List<Language> languages = pgService.getLanguages();
            languages.forEach(mongoService::saveLanguage);
            System.out.println("✅ Languages migrated to MongoDB: " + languages.size());

            // Categories to MongoDB
            List<Category> categories = pgService.getCategories();
            categories.forEach(mongoService::saveCategory);
            System.out.println("✅ Categories migrated to MongoDB: " + categories.size());

            // Actors to MongoDB
            List<Actor> actors = pgService.getActors();
            actors.forEach(mongoService::saveActor);
            System.out.println("✅ Actors migrated to MongoDB: " + actors.size());

            // Films to MongoDB
            System.out.println("📽️ Fetching films from PostgreSQL...");
            List<Film> films = pgService.getFilms();
            System.out.println("🎞️ Number of films fetched: " + films.size());

            for (Film film : films) {
                try {
                    mongoService.saveFilm(film);
                } catch (Exception ex) {
                    System.err.println("❌ Failed to save film ID: " + film.getFilmId());
                    ex.printStackTrace();
                }
            }
            System.out.println("✅ Films migrated to MongoDB: " + films.size());

            redisService.close();
            System.out.println("✅ All data migrated successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error during migration: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
