package cn.joruachan.study;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    public static final void arrayList() {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");

        List<String> subStrings = strings.subList(1, 3);
        subStrings.add("5");

        System.out.println(strings);        // [1, 2, 3, 5, 4]
        System.out.println(subStrings);     // [2, 3, 5]

        /*
         * List的SubList需要注意，在修改sublist会同时反应到list上；
         * */
    }

    public static void main(String[] args) {
        new ArrayList<>();
    }
}
