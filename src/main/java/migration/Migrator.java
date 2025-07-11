package migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Migrator {

    public void migrate() {
        System.out.println("Début de la migration...");

        try (Connection conn = DatabaseConfig.getConnection()) {
            RedisService redis = new RedisService("localhost", 6379);

            try {
                // Countries
                String sqlCountry = "SELECT country_id, country FROM country";
                try (PreparedStatement ps = conn.prepareStatement(sqlCountry);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int countryId = rs.getInt("country_id");
                        String country = rs.getString("country");
                        redis.jedis.set("country:" + countryId, country);
                    }
                }

                // Cities
                String sqlCity = "SELECT city_id, city, country_id FROM city";
                try (PreparedStatement ps = conn.prepareStatement(sqlCity);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int cityId = rs.getInt("city_id");
                        String city = rs.getString("city");
                        int countryId = rs.getInt("country_id");
                        redis.jedis.set("city:" + cityId,
                                String.format("{\"name\":\"%s\", \"country_id\":%d}", city, countryId));
                    }
                }

                System.out.println("✅ Migration terminée.");

            } finally {
                redis.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
