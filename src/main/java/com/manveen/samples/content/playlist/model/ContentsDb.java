package com.manveen.samples.content.playlist.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class ContentsDb {
    private final List<ContentItem> content;
    private final List<PreRoll> preroll;

    public ContentsDb(List<ContentItem> content, List<PreRoll> preroll) {
        this.content = content;
        this.preroll = preroll;
    }

    public List<ContentItem> getContent() {
        return content;
    }

    public List<PreRoll> getPreroll() {
        return preroll;
    }

    public ContentItem findItemByName(String name) {
        if (content == null) return null;
        return content.stream()
                .filter(t -> Objects.equals(t.getName(), name))
                .findAny()
                .get();
    }

    public Collection<Playlist> findMatchingPlaylists(String itemName, String country) {
        ContentItem item = findItemByName(itemName);
        if (item == null) return null;
        return findMatchingPlaylists(item, country);
    }

    public Collection<Playlist> findMatchingPlaylists(ContentItem item, String country) {
        Queue<Playlist> playlists = new ConcurrentLinkedQueue<>();
        if (preroll == null) return playlists;
        List<PreRoll> matchingPrerolls = preroll.parallelStream()
                .filter(t -> matchingPrerollId(t.getName(), item))
                .collect(Collectors.toList());
        matchingPrerolls.parallelStream()
                .forEach(s -> addMatchingVideos(playlists, item, country, s));
        return playlists;
    }

    private boolean matchingPrerollId(String preRollName, ContentItem item) {
        return item.getPreroll().parallelStream()
                .anyMatch(s -> Objects.equals(preRollName, s.getName()));
    }

    private void addMatchingVideos(Collection<Playlist> playlist, ContentItem item,
            String country, PreRoll preroll) {
        preroll.getVideos().parallelStream()
                .forEach(v -> addMatching(playlist, item, country, v));
    }

    private void addMatching(Collection<Playlist> playlist, ContentItem item,
            String country, Video v) {
        item.getVideos().parallelStream()
                .forEach(itemVideo -> addIfMatching(playlist, itemVideo, country, v));
    }

    private void addIfMatching(Collection<Playlist> playlist, Video itemVideo,
            String country, Video v) {
        // TODO: Also need to handle the case where multiple prerolls could exist
        // TODO: also need to handle the case where multiple videos can exist in the list.
        if (itemVideo.getAttributes().getAspect() == v.getAttributes().getAspect()
                && isCountrySupported(itemVideo, country)
                && isCountrySupported(v, country)) {
            playlist.add(new Playlist(v, itemVideo));
        }
    }

    private boolean isCountrySupported(Video itemVideo, String country) {
        return itemVideo.getAttributes().getCountries().parallelStream()
                .anyMatch(c -> Objects.equals(c, country));
    }
}
