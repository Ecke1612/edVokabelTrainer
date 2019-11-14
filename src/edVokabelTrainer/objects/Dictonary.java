package edVokabelTrainer.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Dictonary implements Serializable {

    private ArrayList<EntrySet> entrySets = new ArrayList<>();
    private ArrayList<EntrySet> learndSets = new ArrayList<>();
    private ArrayList<Vokabel> vokabelList = new ArrayList<>();
    private ArrayList<Vokabel> learndVokabelList = new ArrayList<>();
    private String name;
    private DicMetaData dicMetaData = new DicMetaData();

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

    public void addLearndVokabel(Vokabel vokabel) {
        learndVokabelList.add(vokabel);
    }

    public ArrayList<Vokabel> getVokabelList() {
        return vokabelList;
    }

    public void moveToLearnd(Vokabel vokabel) {
        vokabelList.remove(vokabel);
        learndVokabelList.add(vokabel);
    }

    public void moveToEntrySet(Vokabel vokabel) {
        learndVokabelList.remove(vokabel);
        vokabelList.add(vokabel);
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

    public ArrayList<Vokabel> getLearndVokabelList() {
        return learndVokabelList;
    }

    public void setLearndVokabelList(ArrayList<Vokabel> learndVokabelList) {
        this.learndVokabelList = learndVokabelList;
    }

    public DicMetaData getDicMetaData() {
        return dicMetaData;
    }

    public void setDicMetaData(DicMetaData dicMetaData) {
        this.dicMetaData = dicMetaData;
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
