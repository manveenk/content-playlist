package com.manveen.samples.content.playlist.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
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
        try {
            if (content == null) return null;
            return content.stream()
                    .filter(t -> Objects.equals(t.getName(), name))
                    .findAny()
                    .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public Collection<Playlist> generateMatchingPlaylists(String itemName, String country) {
        ContentItem item = findItemByName(itemName);
        if (item == null) return null;
        return generateMatchingPlaylists(item, country);
    }

    public Collection<Playlist> generateMatchingPlaylists(ContentItem item, String country) {
        Objects.requireNonNull(item);
        Objects.requireNonNull(country);
        Collection<Playlist> playlists = new ConcurrentLinkedQueue<>();
        if (preroll == null) return playlists;
        List<PreRoll> matchingPrerolls = preroll.parallelStream()
                .filter(t -> item.containsPreRoll(t.getName()))
                .collect(Collectors.toList());
        item.getVideos().parallelStream().forEach(
                v -> addEnumeratedPlaylists(playlists, findMatchingPreRollVideos(matchingPrerolls, v, country), v));
        return playlists;
    }

    // Visible for testing only
    static Collection<Video> findMatchingPreRollVideos(List<PreRoll> prerolls,
            Video contentVideo, String country) {
        if (!contentVideo.isCountrySupported(country)) {
            return null;
        }
        Collection<Video> matching = new ConcurrentLinkedQueue<>();
        prerolls.parallelStream().forEach(
                preroll -> addMatchingPrerollVideos(matching, preroll, contentVideo, country));
        return matching;
    }

    private static void addMatchingPrerollVideos(Collection<Video> matching,
            PreRoll preroll, Video contentVideo, String country) {
        preroll.getVideos().parallelStream().forEach(
                v -> addIfMatchingAttributes(matching, v, contentVideo, country));
    }

    private static void addIfMatchingAttributes(Collection<Video> matching,
            Video preRollVideo, Video contentVideo, String country) {
        if (contentVideo.isCompatiblePreRoll(preRollVideo, country)) {
            matching.add(preRollVideo);
        }
    }

    /** Generates all combinations of prerolls for an item */
    // Visible for testing only
    static void addEnumeratedPlaylists(Collection<Playlist> playlists,
            Collection<Video> prerolls, Video contentVideo) {
        if (prerolls == null || prerolls.isEmpty()) return;
        List<List<Video>> prerollSet = Utils.allSubsets(new ArrayList<Video>(prerolls));
        prerollSet.parallelStream().forEach(p -> addPlaylist(playlists, p, contentVideo));
    }

    private static void addPlaylist(Collection<Playlist> playlists, Collection<Video> preRollVideos,
            Video contentVideo) {
        if (!preRollVideos.isEmpty()) {
            playlists.add(Playlist.createPlaylist(preRollVideos, contentVideo));
        }
    }
}
