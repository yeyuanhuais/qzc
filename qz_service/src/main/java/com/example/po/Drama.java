package com.example.po;

public class Drama {
    private Integer id;

    private String title;

    private String path;

    private String dramaClass;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getDramaClass() {
        return dramaClass;
    }

    public void setDramaClass(String dramaClass) {
        this.dramaClass = dramaClass == null ? null : dramaClass.trim();
    }
}