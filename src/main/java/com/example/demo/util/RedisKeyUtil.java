package com.example.demo.util;

public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIEK = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";

    //public static String getEvent

    public static String getLikeKey(int entityId, int entityType){
        return BIZ_LIEK + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityId, int entityType){
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
}
