package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                String routeWithOurR = route.replaceAll("R", "");
                int countR = route.length() - (route.length() - routeWithOurR.length());
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(countR)) {
                        sizeToFreq.put(countR, sizeToFreq.get(countR) + 1);
                    } else {
                        sizeToFreq.put(countR, 1);
                    }
                }
            }).start();

        }
        int maxCount = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        int maxFreq = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
        System.out.println(String.format("Самое частое количество повторений %s (встретилось %s раз)", maxCount, maxFreq));
        System.out.println("Другие размеры:");
        sizeToFreq.entrySet().stream()
                .filter(item -> item.getKey() != maxCount)
                .sorted(Map.Entry.comparingByValue())
                .forEach(item ->
                        System.out.println(String.format("-  %s (%s раз)", item.getKey(), item.getValue())));


    }
}