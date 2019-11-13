package edVokabelTrainer.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Dictonary implements Serializable {

    private ArrayList<EntrySet> entrySets = new ArrayList<>();
    private ArrayList<EntrySet> learndSets = new ArrayList<>();
    private ArrayList<Vokabel> vokabelList = new ArrayList<>();
    private String name;

    public Dictonary(String name) {
        this.name = name;
    }

    public void addEntry(String foreignword, String germanWord) {
        entrySets.add(new EntrySet(foreignword, germanWord));
    }

    public void addEntry(String foreignword, String germanWord, int correctTranslated, int learndIndex) {
        if(correctTranslated >= learndIndex) learndSets.add(new EntrySet(foreignword, germanWord, correctTranslated));
        else entrySets.add(new EntrySet(foreignword, germanWord, correctTranslated));
    }

    public void addVokabel(Vokabel vokabel) {
        vokabelList.add(vokabel);
    }

    public ArrayList<Vokabel> getVokabelList() {
        return vokabelList;
    }

    public void moveToLearnd(EntrySet entrySet) {
        entrySets.remove(entrySet);
        learndSets.add(entrySet);
    }

    public void moveToEntrySet(EntrySet entrySet) {
        learndSets.remove(entrySet);
        entrySets.add(entrySet);
    }

    public ArrayList<String> getListInSaveForm() {
        ArrayList<String> content = new ArrayList<>();
        for(EntrySet e: entrySets) {
            content.add(e.getForeignWord() + ";" + e.getGermanWord() + ";" + e.getCorrectTranslated());
        }
        for(EntrySet e: learndSets) {
            content.add(e.getForeignWord() + ";" + e.getGermanWord() + ";" + e.getCorrectTranslated());
        }
        return content;
    }

    public void setVokabelList(ArrayList<Vokabel> vokabelList) {
        this.vokabelList = vokabelList;
    }

    public ArrayList<EntrySet> getEntrySets() {
        return entrySets;
    }

    public ArrayList<EntrySet> getLearndSets() {
        return learndSets;
    }

    public String getName() {
        return name;
    }
}
