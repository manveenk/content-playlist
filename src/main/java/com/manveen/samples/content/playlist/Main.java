package com.manveen.samples.content.playlist;

import java.io.FileReader;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.manveen.samples.content.playlist.model.ContentItem;
import com.manveen.samples.content.playlist.model.ContentsDb;
import com.manveen.samples.content.playlist.model.Playlist;
import com.manveen.samples.content.playlist.model.Video;

public class Main {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();

        ContentsDb contents = gson.fromJson(new FileReader("contents.json"), ContentsDb.class);
        System.out.println("Content items: " + contents.getContent().size());
        System.out.println("Pre-Roll items: " + contents.getPreroll().size());

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Usage: 'quit' or 'MI3, US'");
            System.out.print("Input: ");
            String input = scanner.nextLine();
            quit = input.equalsIgnoreCase("quit");
            if (!quit) {
                String[] tokens = input.split(",");
                boolean validInput = tokens.length == 2;
                if (validInput) {
                    try {
                        ContentItem item = contents.findItemByName(tokens[0].trim());
                        String country = tokens[1].trim().toUpperCase();
                        validInput = item != null && country != null;
                        if (validInput) {
                            Collection<Playlist> playlists = contents.generateMatchingPlaylists(item, country);
                            if (playlists.isEmpty()) {
                                System.out.println("No legal playlist possible because the"
                                        + " Pre-Roll Video isn't compatible with the aspect ratio"
                                        + " of the Content Video for " + country + ".");
                            } else {
                                playlists.stream().forEach(s -> System.out.println(toPlaylistString(s)));
                            }
                        }
                    } catch (Exception e) {
                        validInput = false;
                    }
                }
                if (!validInput) {
                    System.err.println("Invalid input: " + input);
                    System.err.println("Try again!");
                }
            }
        }
        scanner.close();
    }

    private static String toPlaylistString(Playlist playlist) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(playlist.getVideos().stream()
                .map(Video::getName)
                .collect(Collectors.joining(", ")));
        sb.append("}");
        return sb.toString();
    }
}
