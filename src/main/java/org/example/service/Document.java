package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.DocumentDetail;
import org.example.exception.IndexException;
import org.example.util.DocumentUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class Document {

    List<DocumentDetail> documentDetails;

    BuildIndex buildIndex;

    public void addDocuments(Map<String, String> documents) {
        documents.forEach((id, content) -> {
            try {
                addDocument(id, content);
            } catch (IndexException e) {
                Logger.getLogger("DocumentDetail could not be indexed");
            }
        });
    }

    private void addDocument(String id, String content) throws IndexException {

        String[] words = DocumentUtil.splitContentToWords(content);

        Integer maxOccurringFrequency = DocumentUtil.computeMaxOccurringFrequency(words);

        if (maxOccurringFrequency == 0) {
            throw new IndexException("DocumentDetail could not be processed");
        }

        DocumentDetail documentDetail = new DocumentDetail(id, words.length, maxOccurringFrequency, words);

        documentDetails.add(documentDetail);

        buildIndex.buildInvertedIndex(documentDetail, documentDetails.size());
    }

}
