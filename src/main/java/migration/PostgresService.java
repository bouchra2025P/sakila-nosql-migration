package migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service utilitaire pour lire la base PostgreSQL SAKILA et renvoyer des POJOs.
 * Toutes les méthodes utilisent {@link DatabaseConfig#getConnection()} et des try‑with‑resources
 * pour garantir la fermeture des ressources JDBC.
 */
public class PostgresService {

    /* ===============================  PAYS  =============================== */
    public List<Country> getCountries() throws SQLException {
        List<Country> list = new ArrayList<>();
        String sql = "SELECT country_id, country, last_update FROM country";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Country c = new Country();
                c.setCountryId(rs.getInt("country_id"));
                c.setCountry(rs.getString("country"));
                c.setLastUpdate(rs.getString("last_update"));
                list.add(c);
            }
        }
        return list;
    }

    /* ===============================  VILLES  =============================== */
    public List<City> getCities() throws SQLException {
        List<City> list = new ArrayList<>();
        String sql = "SELECT city_id, city, country_id, last_update FROM city";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                City c = new City();
                c.setCityId(rs.getInt("city_id"));
                c.setCity(rs.getString("city"));
                c.setCountryId(rs.getInt("country_id"));
                c.setLastUpdate(rs.getString("last_update"));
                list.add(c);
            }
        }
        return list;
    }

    /* ===============================  LANGUES  =============================== */
    public List<Language> getLanguages() throws SQLException {
        List<Language> list = new ArrayList<>();
        String sql = "SELECT language_id, name, last_update FROM language";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Language lang = new Language();
                lang.setLanguageId(rs.getInt("language_id"));
                lang.setName(rs.getString("name"));
                lang.setLastUpdate(rs.getString("last_update"));
                list.add(lang);
            }
        }
        return list;
    }

    /* ===============================  CATÉGORIES  =============================== */
    public List<Category> getCategories() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT category_id, name, last_update FROM category";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setName(rs.getString("name"));
                c.setLastUpdate(rs.getString("last_update"));
                list.add(c);
            }
        }
        return list;
    }

    /* ===============================  ACTEURS  =============================== */
    public List<Actor> getActors() throws SQLException {
        List<Actor> list = new ArrayList<>();
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Actor a = new Actor();
                a.setActorId(rs.getInt("actor_id"));
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));
                a.setLastUpdate(rs.getString("last_update"));
                list.add(a);
            }
        }
        return list;
    }

    /* ===============================  FILMS  =============================== */
    public List<Film> getFilms() throws SQLException {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT film_id, title, description, release_year, language_id, last_update FROM film";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Film film = new Film();
                film.setFilmId(rs.getInt("film_id"));
                film.setTitle(rs.getString("title"));
                film.setDescription(rs.getString("description"));
                film.setReleaseYear(rs.getInt("release_year"));
                film.setLastUpdate(rs.getString("last_update"));

                // Récupère la langue
                int languageId = rs.getInt("language_id");
                film.setLanguage(getLanguageById(conn, languageId));

                // Récupère catégories et acteurs associés
                int filmId = film.getFilmId();
                film.setCategories(getCategoriesByFilmId(conn, filmId));
                film.setActors(getActorsByFilmId(conn, filmId));

                films.add(film);
            }
        }
        return films;
    }

    /* ===============================  UTILITAIRES PRIVÉS  =============================== */
    private Language getLanguageById(Connection conn, int id) throws SQLException {
        String sql = "SELECT language_id, name, last_update FROM language WHERE language_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Language lang = new Language();
                    lang.setLanguageId(rs.getInt("language_id"));
                    lang.setName(rs.getString("name"));
                    lang.setLastUpdate(rs.getString("last_update"));
                    return lang;
                }
            }
        }
        return null;
    }

    private List<Category> getCategoriesByFilmId(Connection conn, int filmId) throws SQLException {
        List<Category> cats = new ArrayList<>();
        String sql = """
                SELECT c.category_id, c.name, c.last_update
                FROM category c
                JOIN film_category fc ON c.category_id = fc.category_id
                WHERE fc.film_id = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, filmId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category c = new Category();
                    c.setCategoryId(rs.getInt("category_id"));
                    c.setName(rs.getString("name"));
                    c.setLastUpdate(rs.getString("last_update"));
                    cats.add(c);
                }
            }
        }
        return cats;
    }

    private List<Actor> getActorsByFilmId(Connection conn, int filmId) throws SQLException {
        List<Actor> actors = new ArrayList<>();
        String sql = """
                SELECT a.actor_id, a.first_name, a.last_name, a.last_update
                FROM actor a
                JOIN film_actor fa ON a.actor_id = fa.actor_id
                WHERE fa.film_id = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, filmId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Actor a = new Actor();
                    a.setActorId(rs.getInt("actor_id"));
                    a.setFirstName(rs.getString("first_name"));
                    a.setLastName(rs.getString("last_name"));
                    a.setLastUpdate(rs.getString("last_update"));
                    actors.add(a);
                }
            }
        }
        return actors;
    }
}
