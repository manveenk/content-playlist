package com.manveen.samples.content.playlist.model;

import java.util.List;

public class ContentItem extends ContentBase {
    private final List<ContentId> preroll;

    public ContentItem(String name, List<Video> videos, List<ContentId> preroll) {
        super(name, videos);
        this.preroll = preroll;
    }

    public List<ContentId> getPreroll() {
        return preroll;
    }

    @Override
    public String toString() {
        return "{preroll:" + preroll + ", name:" + name + ", videos:" + videos + "}";
    }
}
