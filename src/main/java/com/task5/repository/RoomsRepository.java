package com.task5.repository;

import com.task5.models.GameRoom;
import com.task5.models.Move;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class RoomsRepository {

    private static AbstractMap<String, GameRoom> rooms = new HashMap<String, GameRoom>();

    public static ArrayList<GameRoom> getAll() {
        return new ArrayList<GameRoom>(rooms.values());
    }

    public static GameRoom getRoom(String roomId) {
        if(rooms.containsKey(roomId)) {
            return rooms.get(roomId);
        }
        return null;
    }

    public static int addRoom(String title, String tags, String color, short field_size) {
        if(!rooms.containsKey(title)) {
            String[] tags_array = splitTags(tags);
            rooms.put(title, new GameRoom(
                    title,
                    tags_array,
                    color,
                    field_size
            ));
            TagsRepository.addTags(tags_array);
            return 1;
        }
        return -1;
    }

    public static void disposeRoom(String roomId) {
        if(rooms.containsKey(roomId)) {
            rooms.remove(roomId);
        }
    }

    public static boolean isAvailable(String roomId) {
        if(rooms.containsKey(roomId)){
            return rooms.get(roomId).isAvailable();
        }
        return false;
    }

    public static boolean saveMove(Move m) {
        if(rooms.containsKey(m.roomId)) {
            return rooms.get(m.roomId).saveMove(m);
        }
        return false;
    }

    public static boolean gameEnded(String roomId) {
        if(rooms.containsKey(roomId)) {
            return rooms.get(roomId).winner != GameRoom.NOT_ENDED;
        }
        return false;
    }

    public static int getWinner(String roomId) {
        if(rooms.containsKey(roomId)) {
            return rooms.get(roomId).winner;
        }
        return -2;
    }

    private static String[] splitTags(String tags) {
        return tags.replace("[", "")
                .replace("]", "")
                .split(";");
    }
}
