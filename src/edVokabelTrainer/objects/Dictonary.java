package edVokabelTrainer.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Dictonary implements Serializable {

    private ArrayList<EntrySet> entrySets = new ArrayList<>();
    private ArrayList<EntrySet> learndSets = new ArrayList<>();
    private String name;

    public Dictonary(String name) {
        this.name = name;
    }

    public void addEntry(String foreignword, String germanWord) {
        entrySets.add(new EntrySet(foreignword, germanWord));
    }

    public void moveToLearnd(EntrySet entrySet) {
        entrySets.remove(entrySet);
        learndSets.add(entrySet);
    }

    public void moveToEntrySet(EntrySet entrySet) {
        learndSets.remove(entrySet);
        entrySets.add(entrySet);
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
