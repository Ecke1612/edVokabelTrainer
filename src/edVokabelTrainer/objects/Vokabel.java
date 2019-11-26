package edVokabelTrainer.objects;

import java.util.ArrayList;

public class Vokabel {

    private String german;
    private String singular = "";
    private String plural = "";
    private String ersteS = "";
    private String zweiteS = "";
    private String dritteS = "";
    private String vierteP = "";
    private int successCount = 0;

    public Vokabel(String german) {
        this.german = german;
    }

    public String getSingular() {
        return singular;
    }

    public void setSingular(String singular) {
        this.singular = singular;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }

    public String getErsteS() {
        return ersteS;
    }

    public void setErsteS(String ersteS) {
        this.ersteS = ersteS;
    }

    public String getZweiteS() {
        return zweiteS;
    }

    public void setZweiteS(String zweiteS) { this.zweiteS = zweiteS;
    }

    public String getDritteS() {
        return dritteS;
    }

    public void setDritteS(String dritteS) {
        this.dritteS = dritteS;
    }

    public String getVierteP() {
        return vierteP;
    }

    public void setVierteP(String vierteP) {
        this.vierteP = vierteP;
    }

    public String getGerman() {
        return german;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public void raisSuccessCount() {
        successCount++;
    }

    public void lowerSuccessCount() {
        if(successCount > 0) successCount--;
    }

    public void resetSuccesscount() {
        successCount = 0;
    }

    public ArrayList<String> getWordsAsList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(singular);
        list.add(plural);
        list.add(ersteS);
        list.add(zweiteS);
        list.add(dritteS);
        list.add(vierteP);
        return list;
    }

    public String getWordByIndex(int index) {
        switch (index) {
            case 0: return singular;
            case 1: return plural;
            case 2: return ersteS;
            case 3: return zweiteS;
            case 4: return dritteS;
            case 5: return vierteP;
        }
        return singular;
    }

    public void setGerman(String german) {
        this.german = german;
    }

    public int getSettetFieldCount() {
        int count = 0;
        for(String s : getWordsAsList()) {
            if(!s.equals("")) count++;
        }
        return count;
    }
}
