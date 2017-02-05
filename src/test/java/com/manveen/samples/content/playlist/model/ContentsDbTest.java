package com.manveen.samples.content.playlist.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

import org.junit.Test;

public class ContentsDbTest {

    /**
     * Test to verify in case of more than one pre-roll match, play lists generated contain all ordered subsets of the matches.
     * e.g. if Video content C has eligible pre-rolls P1 and P2, then there would be 4 ways to generate the play list.
     * {{P1}, {P2}, {P1, P2}, {P2, P1}} followed by C.
     */
    @Test
    public void testAddEnumeratedPlaylists() {
        Collection<Playlist> playlists = new ConcurrentLinkedQueue<>();
        Collection<Video> prerolls = Arrays.asList(new Video("P1", null), new Video("P2", null));
        Video contentVideo = new Video("C", null);
        ContentsDb.addEnumeratedPlaylists(playlists, prerolls, contentVideo);
        assertEquals(4, playlists.size());
        assertTrue(containsVideos(playlists, "P1", "C"));
        assertTrue(containsVideos(playlists, "P2", "C"));
        assertTrue(containsVideos(playlists, "P1", "P2", "C"));
        assertTrue(containsVideos(playlists, "P2", "P1", "C"));
    }

    /**
     * Test to ensure that pre roll matching takes country code, language and aspect ratio into account.
     */
    @Test
    public void testFindMatchingPreRollVideos() {
        List<PreRoll> prerolls = Arrays.asList(
                new PreRoll("P1", Arrays.asList(
                        new Video("V1", new VideoAttributes(Arrays.asList("US"), "English", AspectRatio.SIXTEEN_BY_NINE)),
                        new Video("V2", new VideoAttributes(Arrays.asList("US"), "French", AspectRatio.SIXTEEN_BY_NINE)),
                        new Video("V3", new VideoAttributes(Arrays.asList("US"), "English", AspectRatio.FOUR_BY_THREE)),
                        new Video("V4", new VideoAttributes(Arrays.asList("UK"), "English", AspectRatio.SIXTEEN_BY_NINE)))));
        Video contentVideo = new Video("C", new VideoAttributes(Arrays.asList("US"), "English", AspectRatio.SIXTEEN_BY_NINE));
        Collection<Video> matching = ContentsDb.findMatchingPreRollVideos(prerolls, contentVideo, "US");
        assertEquals(1, matching.size());
        assertEquals("V1", matching.iterator().next().getName());
    }

    public static boolean containsVideos(Collection<Playlist> playlists, String... videoNames) {
        return playlists.parallelStream().anyMatch(p -> matchingNames(p.getVideos(), videoNames));
    }

    public static boolean matchingNames(List<Video> videos, String... videoNames) {
        return videos.size() == videoNames.length
                && IntStream.range(0, videos.size()).allMatch(
                        i -> Objects.equals(videos.get(i).getName(), videoNames[i]));
    }
}
