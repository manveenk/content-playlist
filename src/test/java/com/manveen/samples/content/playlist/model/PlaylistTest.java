package com.manveen.samples.content.playlist.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Unit tests for {@link Playlist}.
 */
public class PlaylistTest {

    @Test
    public void testCreatePlaylist() throws Exception {
        Playlist playlist = Playlist.createPlaylist(
                Arrays.asList(new Video("P1", null), new Video("P2", null)),
                new Video("V", null));
        List<Video> videos = playlist.getVideos();
        assertEquals(3, videos.size());
        assertEquals("P1", videos.get(0).getName());
        assertEquals("P2", videos.get(1).getName());
        assertEquals("V", videos.get(2).getName());
    }

    @Test
    public void testConstruction() throws Exception {
        Playlist playlist = new Playlist();
        assertTrue(playlist.getVideos().isEmpty());

        playlist = new Playlist((Video[]) null);
        assertTrue(playlist.getVideos().isEmpty());

        try {
            Playlist.createPlaylist(null, new Video("V", null));
            fail("null preroll not allowed");
        } catch (NullPointerException expected) {}

        try {
            Playlist.createPlaylist(Arrays.asList(new Video("P1", null)), null);
            fail("null content not allowed");
       } catch (NullPointerException expected) {}
    }
}
