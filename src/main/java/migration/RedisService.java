package migration;

import redis.clients.jedis.Jedis;

public class RedisService {
    final Jedis jedis;

    public RedisService(String host, int port) {
        this.jedis = new Jedis(host, port);
    }

    public void saveCountry(Country country) {
        jedis.set("country:" + country.getCountryId(), country.getCountry());
    }

    public void saveCity(City city) {
        String json = String.format("{\"name\":\"%s\", \"country_id\":%d}", city.getCity(), city.getCountryId());
        jedis.set("city:" + city.getCityId(), json);
    }

    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
