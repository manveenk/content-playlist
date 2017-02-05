package com.manveen.samples.content.playlist.model;

import java.util.List;

public class VideoAttributes {
    private final List<String> countries;
    private final String language;
    private final AspectRatio aspect;

    public VideoAttributes(List<String> countries, String language, AspectRatio aspect) {
        this.countries = countries;
        this.language = language;
        this.aspect = aspect;
    }

    public List<String> getCountries() {
        return countries;
    }

    public String getLanguage() {
        return language;
    }

    public AspectRatio getAspect() {
        return aspect;
    }

    @Override
    public String toString() {
        return "{countries:" + countries + ", language:" + language + ", aspect:" + aspect + "}";
    }
}
