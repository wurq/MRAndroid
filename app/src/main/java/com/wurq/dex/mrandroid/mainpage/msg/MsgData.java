package com.wurq.dex.mrandroid.mainpage.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wurongqiu on 16/12/18.
 */
public class MsgData {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DataItem> ITEMS = new ArrayList<DataItem>();

    private static final int COUNT = 3;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDataItem(i));
        }
    }

    private static void addItem(DataItem item) {
        ITEMS.add(item);
    }

    private static DataItem createDataItem(int position) {
        return new DataItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A data item representing a piece of content.
     */
    public static class DataItem {
        public final String id;
        public final String content;
        public final String details;

        public DataItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
