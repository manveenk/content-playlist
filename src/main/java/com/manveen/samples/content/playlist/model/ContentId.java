package com.manveen.samples.content.playlist.model;

public class ContentId {
    private final String name;

    public ContentId(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{name:" + name + "}";
    }
}
