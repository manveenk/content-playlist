package com.manveen.samples.content.playlist.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

public class ContentItemTest {

    @Test
    public void testContainsPreRoll() {
        ContentItem content = new ContentItem("C", Arrays.asList(new Video("V1", null)),
                Arrays.asList(new ContentId("P1")));
        assertTrue(content.containsPreRoll("P1"));
        assertFalse(content.containsPreRoll("V1"));

        content = new ContentItem("C", Arrays.asList(new Video("V1", null)), null);
        assertFalse(content.containsPreRoll("P1"));
    }

    @Test
    public void validConstruction() {
        // Preroll can be null
        ContentItem content = new ContentItem("C", Arrays.asList(new Video("V1", null)), null);
        assertEquals("C", content.getName());

        try {  // name can't be null
            new ContentItem(null, Arrays.asList(new Video("V1", null)),
                    Arrays.asList(new ContentId("P1")));
            fail("name must not be null");
        } catch (NullPointerException expected) {}

        try {  // videos can't be null
            new ContentItem("C", null, Arrays.asList(new ContentId("P1")));
            fail("videos must not be null");
        } catch (NullPointerException expected) {}
    }
}
