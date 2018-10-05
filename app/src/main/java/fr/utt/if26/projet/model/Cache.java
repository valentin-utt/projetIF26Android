package fr.utt.if26.projet.model;

public class Cache {

    private int id;
    private float lat;
    private float lon;
    private int type;
    private int difficulty;
    private int terrain;
    private int size;
    private int owner;
    private String hint;
    private String description;

    public Cache(int id, float lat, float lon, int type, int difficulty, int terrain, int size, int owner, String hint, String description) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.difficulty = difficulty;
        this.terrain = terrain;
        this.size = size;
        this.owner = owner;
        this.hint = hint;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTerrain() {
        return terrain;
    }

    public void setTerrain(int terrain) {
        this.terrain = terrain;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Cache{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                ", type=" + type +
                ", difficulty=" + difficulty +
                ", terrain=" + terrain +
                ", size=" + size +
                ", owner=" + owner +
                ", hint='" + hint + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
