package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.DocumentDetail;
import org.example.data.OccurrenceDetail;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SearchEngine {

    BuildIndex buildIndex;

    Map<String, Map<DocumentDetail, OccurrenceDetail>> index;

    public List<String> searchText(String text) {
        index = buildIndex.index;

        return index.containsKey(text) ? index.get(text).entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(DocumentDetail::getId)
                .toList() : Collections.emptyList();
    }

}
