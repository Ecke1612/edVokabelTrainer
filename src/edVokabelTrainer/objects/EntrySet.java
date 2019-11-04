package edVokabelTrainer.objects;

import java.io.Serializable;

public class EntrySet implements Serializable {

    private String foreignWord;
    private String germanWord;
    private int correctTranslated = 0;

    public EntrySet(String foreignWord, String germanWord) {
        this.foreignWord = foreignWord;
        this.germanWord = germanWord;
    }

    public EntrySet(String foreignWord, String germanWord, int correctTranslated) {
        this.foreignWord = foreignWord;
        this.germanWord = germanWord;
        this.correctTranslated = correctTranslated;
    }

    public void countCorrectTranslatedUp() {
        correctTranslated++;
    }

    public void countCorrectTranslatedDown() {
        if(correctTranslated > 0) {
            correctTranslated--;
        }
    }

    public String getForeignWord() {
        return foreignWord;
    }

    public String getGermanWord() {
        return germanWord;
    }

    public int getCorrectTranslated() {
        return correctTranslated;
    }
}
