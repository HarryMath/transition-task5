package com.task5.repository;


import java.util.ArrayList;

public abstract class TagsRepository {
    private static ArrayList<String> tags = new ArrayList<>();

    public static void addTags(String[] tags_array) {
        for(String tag: tags_array) {
            if(!tags.contains(tag)){
                tags.add(tag);
            }
        }
    }

    public static ArrayList<String> getAll() {
        return tags;
    }
}
