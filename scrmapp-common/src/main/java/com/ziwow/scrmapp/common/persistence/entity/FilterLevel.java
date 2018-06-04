package com.ziwow.scrmapp.common.persistence.entity;

public class FilterLevel {
    private Long id;

    private String levelName;

    public FilterLevel() {
    }

    public FilterLevel(Long id, String levelName) {
        this.id = id;
        this.levelName = levelName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}