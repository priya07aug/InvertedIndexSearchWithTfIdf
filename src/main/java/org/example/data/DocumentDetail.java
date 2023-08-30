package org.example.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DocumentDetail {

    String id;
    Integer totalWordCount;
    Integer maxOccurringWordFrequency;
    String[] words;

}
