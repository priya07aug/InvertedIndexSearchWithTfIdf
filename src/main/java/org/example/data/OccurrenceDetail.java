package org.example.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.exception.IndexException;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class OccurrenceDetail implements Comparable<OccurrenceDetail> {

    // Term Frequency
    private Double tf;
    // Inverse DocumentDetail Frequency
    private Double idf;
    // TF-IDF
    private Double tfIdf;
    //TF-Strategy
    private TfStrategy tfStrategy;
    //Word count
    private Integer wordCount;
    //Total number of words in document
    private Integer totalWordsInDocument;

    public OccurrenceDetail(Integer totalWordsInDocument, Integer wordCount, TfStrategy tfStrategy) throws IndexException {
        this.totalWordsInDocument = totalWordsInDocument;
        this.wordCount = wordCount;
        this.tfStrategy = tfStrategy;
        setTermFrequency();
    }

    public void setTermFrequency() throws IndexException {
        if (tfStrategy.equals(TfStrategy.DOCUMENT)) {
            this.tf = ((double) this.wordCount / (double) this.totalWordsInDocument);
        } else if (tfStrategy.equals(TfStrategy.AUGMENTED)) {
            this.tf = 0.5 + (0.5 * ((double) this.wordCount / (double) this.totalWordsInDocument));
        } else {
            throw new IndexException("Invalid Tf Strategy");
        }
    }

    public void setInverseDocumentFrequency(Integer totalNoOfDocuments, Integer noOfDocumentsWithWord) {
        this.idf = 1 + Math.log((double) totalNoOfDocuments / (double) noOfDocumentsWithWord);

        this.tfIdf = this.tf * this.idf;
    }

    @Override
    public int compareTo(OccurrenceDetail occurrenceDetail) {
        // In descending order
        return occurrenceDetail.getTfIdf().compareTo(this.getTfIdf());
    }

    @Override
    // Two instances of OccurrenceDetail is equal if they point to the same document.
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof OccurrenceDetail occurrenceDetail) {
            return Objects
                    .equals(this.tfIdf, occurrenceDetail.getTfIdf());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tfIdf);
    }
}
