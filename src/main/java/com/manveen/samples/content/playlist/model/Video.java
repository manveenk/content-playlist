package com.manveen.samples.content.playlist.model;

import java.util.Objects;

public class Video {
    private final String name;
    private final VideoAttributes attributes;

    public Video(String name, VideoAttributes attributes) {
        Objects.requireNonNull(name);
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public VideoAttributes getAttributes() {
        return attributes;
    }

    public boolean isCountrySupported(String country) {
        return attributes != null && attributes.getCountries().parallelStream()
                .anyMatch(c -> Objects.equals(c, country));
    }

    public boolean isCompatiblePreRoll(Video preRollVideo, String country) {
        Objects.requireNonNull(preRollVideo);
        VideoAttributes otherAttributes = preRollVideo.getAttributes();
        return attributes != null && otherAttributes != null
                && attributes.getAspect() == otherAttributes.getAspect()
                && Objects.equals(attributes.getLanguage(), otherAttributes.getLanguage())
                && isCountrySupported(country)
                && preRollVideo.isCountrySupported(country);
    }

    @Override
    public String toString() {
        return name;
    }
}
