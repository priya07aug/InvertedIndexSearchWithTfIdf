package org.example.util;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DocumentUtil {

    private DocumentUtil(){

    }

    static final String WORD_REGEX = "\\W+";

    public static String[] splitContentToWords(String content) {
        return content.toLowerCase().split(WORD_REGEX);
    }

    public static Integer computeMaxOccurringFrequency(String[] words) {

        Optional<Long> wordFrequency = Arrays.stream(words)
                .collect(Collectors.groupingBy(String::valueOf, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getValue);

        return wordFrequency.map(Math::toIntExact).orElse(0);
    }
}
