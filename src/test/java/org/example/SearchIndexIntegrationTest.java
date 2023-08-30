package org.example;

import org.example.service.Document;
import org.example.service.SearchEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SearchIndexIntegrationTest {
    @Autowired
    Document document;

    @Autowired
    SearchEngine searchEngine;

    @Test
    void testBuildIndex() {

        document.addDocuments(Map.of("document 1", "the brown fox jumped over the brown dog",
                "document 2", "the lazy brown dog sat in the corner",
                "document 3", "the red fox bit the lazy dog"));

        assertEquals(List.of("document 3", "document 1"), searchEngine.searchText("fox"));
        assertTrue(searchEngine.searchText("brown").containsAll(List.of("document 2", "document 1")));
        assertEquals(List.of("document 3"), searchEngine.searchText("red"));

        List<String> occurrence = searchEngine.searchText("the");
        assertEquals("document 3", occurrence.get(0));
        assertTrue(occurrence.containsAll(List.of("document 3", "document 2", "document 1")));

        assertEquals(Collections.emptyList(), searchEngine.searchText("apple"));
    }

}