package com.manveen.samples.content.playlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.Test;

import com.google.gson.Gson;
import com.manveen.samples.content.playlist.model.ContentsDb;
import com.manveen.samples.content.playlist.model.Playlist;
import com.manveen.samples.content.playlist.model.Video;

/**
 * Functional tests for {@link Main}.
 */
public class MainTest {

    @Test
    public void testMi3Us() throws Exception {
        Gson gson = new Gson();

        Reader reader = new BufferedReader(new InputStreamReader(
                MainTest.class.getResourceAsStream("/contents.json")));
        ContentsDb contents = gson.fromJson(reader, ContentsDb.class);
        Collection<Playlist> playlists = contents.findMatchingPlaylists("MI3", "US");
        assertTrue(playlists.isEmpty());
        reader.close();
    }

    @Test
    public void testMi3Ca() throws Exception {
        Gson gson = new Gson();

        Reader reader = new BufferedReader(new InputStreamReader(
                MainTest.class.getResourceAsStream("/contents.json")));
        ContentsDb contents = gson.fromJson(reader, ContentsDb.class);
        Collection<Playlist> playlists = contents.findMatchingPlaylists("MI3", "CA");
        assertEquals(1, playlists.size());
        containsVideos(playlists, "V5", "V1");
        reader.close();
    }

    @Test
    public void testMi3Uk() throws Exception {
        Gson gson = new Gson();

        Reader reader = new BufferedReader(new InputStreamReader(
                MainTest.class.getResourceAsStream("/contents.json")));
        ContentsDb contents = gson.fromJson(reader, ContentsDb.class);
        Collection<Playlist> playlists = contents.findMatchingPlaylists("MI3", "UK");
        assertEquals(2, playlists.size());
        assertTrue(containsVideos(playlists, "V6", "V2"));
        assertTrue(containsVideos(playlists, "V7", "V3"));
        reader.close();
    }

    private boolean containsVideos(Collection<Playlist> playlists, String... videoNames) {
        return playlists.parallelStream().anyMatch(p -> matchingNames(p.getVideos(), videoNames));
    }

    private boolean matchingNames(List<Video> videos, String... videoNames) {
        return videos.size() == videoNames.length
                && IntStream.range(0, videos.size()).allMatch(
                        i -> Objects.equals(videos.get(i).getName(), videoNames[i]));
    }
}
