package migration;

import java.util.List;

public class Film {
    private int filmId;
    private String title;
    private String description;
    private Integer releaseYear;
    private Language language;
    private List<Category> categories;
    private List<Actor> actors;
    private String lastUpdate;

    // getters & setters
    public int getFilmId() { return filmId; }
    public void setFilmId(int filmId) { this.filmId = filmId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }
    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
    public List<Actor> getActors() { return actors; }
    public void setActors(List<Actor> actors) { this.actors = actors; }
    public String getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(String lastUpdate) { this.lastUpdate = lastUpdate; }
}
