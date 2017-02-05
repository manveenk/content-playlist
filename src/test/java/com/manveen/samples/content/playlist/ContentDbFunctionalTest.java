package com.manveen.samples.content.playlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

import org.junit.Test;

import com.google.gson.Gson;
import com.manveen.samples.content.playlist.model.ContentsDb;
import com.manveen.samples.content.playlist.model.ContentsDbTest;
import com.manveen.samples.content.playlist.model.Playlist;

public class ContentDbFunctionalTest {
    private static final Gson gson = new Gson();

    @Test
    public void testFindItemByName() {
        Reader reader = new BufferedReader(new InputStreamReader(
                ContentDbFunctionalTest.class.getResourceAsStream("/contents.json")));
        ContentsDb contents = gson.fromJson(reader, ContentsDb.class);
        assertNull(contents.findItemByName("ABC"));
        assertEquals("MI3", contents.findItemByName("MI3").getName());
    }

    @Test
    public void testMi3Us() throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                ContentDbFunctionalTest.class.getResourceAsStream("/contents.json")));
        ContentsDb contents = gson.fromJson(reader, ContentsDb.class);
        Collection<Playlist> playlists = contents.generateMatchingPlaylists("MI3", "US");
        assertTrue(playlists.isEmpty());
        reader.close();
    }

    @Test
    public void testMi3Ca() throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                ContentDbFunctionalTest.class.getResourceAsStream("/contents.json")));
        ContentsDb contents = gson.fromJson(reader, ContentsDb.class);
        Collection<Playlist> playlists = contents.generateMatchingPlaylists("MI3", "CA");
        assertEquals(1, playlists.size());
        ContentsDbTest.containsVideos(playlists, "V5", "V1");
        reader.close();
    }

    @Test
    public void testMi3Uk() throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                ContentDbFunctionalTest.class.getResourceAsStream("/contents.json")));
        ContentsDb contents = gson.fromJson(reader, ContentsDb.class);
        Collection<Playlist> playlists = contents.generateMatchingPlaylists("MI3", "UK");
        assertEquals(2, playlists.size());
        assertTrue(ContentsDbTest.containsVideos(playlists, "V6", "V2"));
        assertTrue(ContentsDbTest.containsVideos(playlists, "V7", "V3"));
        reader.close();
    }

    @Test
    public void testMultiplePrerolls() throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(
                ContentDbFunctionalTest.class.getResourceAsStream("/multiple-prerolls-contents.json")));
        ContentsDb contents = gson.fromJson(reader, ContentsDb.class);
        Collection<Playlist> playlists = contents.generateMatchingPlaylists("MI3", "CA");
        assertEquals(4, playlists.size());
        assertTrue(ContentsDbTest.containsVideos(playlists, "V5", "V1"));
        assertTrue(ContentsDbTest.containsVideos(playlists, "V7", "V1"));
        assertTrue(playlists.toString(), ContentsDbTest.containsVideos(playlists, "V5", "V7", "V1"));
        assertTrue(playlists.toString(), ContentsDbTest.containsVideos(playlists, "V7", "V5", "V1"));
        reader.close();
    }
}
