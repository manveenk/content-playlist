package com.manveen.samples.content.playlist.model;

import java.util.List;

public class ContentBase {
    protected final String name;
    protected final List<Video> videos;

    public ContentBase(String name, List<Video> videos) {
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
