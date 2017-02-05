package com.manveen.samples.content.playlist.model;

import java.util.List;
import java.util.Objects;

public class ContentBase {
    protected final String name;
    protected final List<Video> videos;

    public ContentBase(String name, List<Video> videos) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(videos);
        this.name = name;
        this.videos = videos;
    }

    public String getName() {
        return name;
    }

    public List<Video> getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return "{name:" + name + ", videos:" + videos + "}";
    }
}
