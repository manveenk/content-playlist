package com.manveen.samples.content.playlist.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Playlist {
    private final List<Video> videos;

    public Playlist(Video... videos) {
        this.videos = videos == null || videos.length == 0
                ? new ArrayList<>() : Arrays.asList(videos);
    }

    public Playlist(List<Video> videos) {
        this.videos = videos;
    }

    public List<Video> getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return "[" + videos.stream().map(Video::getName).collect(Collectors.joining(", ")) + "]";
    }

    public static Playlist createPlaylist(Collection<Video> preRollVideos, Video contentVideo) {
        Objects.requireNonNull(preRollVideos);
        Objects.requireNonNull(contentVideo);
        Video[] videos = new Video[preRollVideos.size() + 1];
        int i = 0;
        for (Video v : preRollVideos) {
            videos[i] = v;
            ++i;
        }
        videos[i] = contentVideo;
        return new Playlist(videos);
    }
}
