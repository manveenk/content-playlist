package com.manveen.samples.content.playlist.model;

import java.util.Objects;

public class ContentId {
    private final String name;

    public ContentId(String name) {
        Objects.requireNonNull(name);
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
