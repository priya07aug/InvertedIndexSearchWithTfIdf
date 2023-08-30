package org.example.service;

import lombok.AllArgsConstructor;
import org.example.data.DocumentDetail;
import org.example.data.OccurrenceDetail;
import org.example.data.TfStrategy;
import org.example.exception.IndexException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class BuildIndex {

    //Map of Word to DocumentId and Occurrence Details
    Map<String, Map<DocumentDetail, OccurrenceDetail>> index;

    protected void buildInvertedIndex(DocumentDetail documentDetail, Integer documentCount) throws IndexException {

        Integer count;
        for (String word : documentDetail.getWords()) {
            count = 1;
            if (index.containsKey(word)) {
                Map<DocumentDetail, OccurrenceDetail> textOccurrence = new HashMap<>(index.get(word));

                //index has the word mapped to the documentDetail
                if (index.get(word).containsKey(documentDetail)) {
                    OccurrenceDetail occurrenceDetail = textOccurrence.get(documentDetail);
                    count = occurrenceDetail.getWordCount() + 1;
                    textOccurrence.get(documentDetail).setWordCount(count);
                }
                //index has only the word
                else {
                    textOccurrence.put(documentDetail,
                            new OccurrenceDetail(documentDetail.getTotalWordCount(), count, TfStrategy.DOCUMENT));
                }
                index.put(word, textOccurrence);

            } else  //index does not have the word
                index.put(word, Map.of(documentDetail,
                        new OccurrenceDetail(documentDetail.getTotalWordCount(), count, TfStrategy.DOCUMENT)));
        }

        computeTfIdf(documentCount);
    }

    private void computeTfIdf(Integer documentCount) {
        index.forEach((word, documentDetail) -> documentDetail.forEach((document, occurrence) -> occurrence.
                setInverseDocumentFrequency(documentCount, documentDetail.size())));
    }

}