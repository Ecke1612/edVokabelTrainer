package edVokabelTrainer.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Dictonary implements Serializable {

    private ArrayList<EntrySet> entrySets = new ArrayList<>();
    private String name;

    public Dictonary(String name) {
        this.name = name;
    }

    public void addEntry(String foreignword, String germanWord) {
        entrySets.add(new EntrySet(foreignword, germanWord));
    }

    public ArrayList<EntrySet> getEntrySets() {
        return entrySets;
    }

    public String getName() {
        return name;
    }
}
