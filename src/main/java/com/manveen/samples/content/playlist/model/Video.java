package com.manveen.samples.content.playlist.model;

public class Video {
    private final String name;
    private final VideoAttributes attributes;

    public Video(String name, VideoAttributes attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public VideoAttributes getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "{name:" + name + ", attributes:" + attributes + "}";
    }
}
