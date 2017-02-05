package com.manveen.samples.content.playlist.model;

import java.util.List;
import java.util.Objects;

public class ContentItem extends ContentBase {
    private final List<ContentId> preroll;

    public ContentItem(String name, List<Video> videos, List<ContentId> preroll) {
        super(name, videos);
        this.preroll = preroll;
    }

    public List<ContentId> getPreRoll() {
        return preroll;
    }

    public boolean containsPreRoll(String preRollName) {
        return preroll != null && preroll.parallelStream()
                .anyMatch(s -> Objects.equals(preRollName, s.getName()));
    }

    @Override
    public String toString() {
        return "{preroll:" + preroll + ", name:" + name + ", videos:" + videos + "}";
    }
}
