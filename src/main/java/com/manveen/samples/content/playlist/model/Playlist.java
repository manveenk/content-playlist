package com.manveen.samples.content.playlist.model;

import java.util.Arrays;
import java.util.List;

public class Playlist {
    private final List<Video> videos;

    public Playlist(Video... videos) {
        this.videos = Arrays.asList(videos);
    }

    public Playlist(List<Video> videos) {
        this.videos = videos;
    }

    public List<Video> getVideos() {
        return videos;
    }
}
