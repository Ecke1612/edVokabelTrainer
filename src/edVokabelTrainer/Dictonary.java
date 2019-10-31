package edVokabelTrainer;

import java.io.Serializable;
import java.util.ArrayList;

public class Dictonary implements Serializable {

    private ArrayList<EntrySet> dicList = new ArrayList<>();
    private String name;

    public Dictonary(String name) {
        this.name = name;
    }

    public void addEntry(String foreignword, String germanWord) {
        dicList.add(new EntrySet(foreignword, germanWord));
    }

    public ArrayList<EntrySet> getDicList() {
        return dicList;
    }

    public String getName() {
        return name;
    }
}
