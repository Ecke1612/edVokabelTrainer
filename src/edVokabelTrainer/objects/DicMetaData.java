package edVokabelTrainer.objects;

public class DicMetaData {

    private String sin = "";
    private String pl = "";
    private String ersteS = "";
    private String zweiteS = "";
    private String dritteS = "";
    private String vierteP = "";
    private String[] personArray;

    public DicMetaData() {
    }

    public String getErsteS() {
        return ersteS;
    }

    public String getZweiteS() {
        return zweiteS;
    }

    public String getDritteS() {
        return dritteS;
    }

    public String getVierteP() {
        return vierteP;
    }

    public void setErsteS(String ersteS) {
        this.ersteS = ersteS;
    }

    public void setZweiteS(String zweiteS) {
        this.zweiteS = zweiteS;
    }

    public void setDritteS(String dritteS) {
        this.dritteS = dritteS;
    }

    public void setVierteP(String vierteP) {
        this.vierteP = vierteP;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public String getSin() {
        return sin;
    }

    public String getPl() {
        return pl;
    }

    public String getPersonByIndex(int index) {
        personArray = new String[] {sin, pl, ersteS, zweiteS, dritteS, vierteP};
        return personArray[index];
    }
}
