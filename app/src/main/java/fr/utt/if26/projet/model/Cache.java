package fr.utt.if26.projet.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "cache_table")
public class Cache implements Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "lat")
    private double lat;

    @ColumnInfo(name = "lon")
    private double lon;

    @ColumnInfo(name = "type")
    private int  type;

    @ColumnInfo(name = "difficulty")
    private int difficulty;


    @ColumnInfo(name = "terrain")
    private int terrain;

    @ColumnInfo(name = "size")
    private int size;

    @ColumnInfo(name = "owner")
    private int owner;

    @ColumnInfo(name = "hint")
    @NonNull
    private String hint;

    @ColumnInfo(name = "description")
    @NonNull
    private String description;

    public Cache(double lat, double lon, int type, int difficulty, int terrain, int size, int owner, String hint, String description) {
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

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLon() {
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
