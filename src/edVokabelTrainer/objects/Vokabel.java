package edVokabelTrainer.objects;

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
        System.out.println("singular set: " + singular);
        this.singular = singular;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        System.out.println("plural set: " + plural);
        this.plural = plural;
    }

    public String getErsteS() {
        return ersteS;
    }

    public void setErsteS(String ersteS) {
        System.out.println("ersteS set: " + singular);
        this.ersteS = ersteS;
    }

    public String getZweiteS() {
        return zweiteS;
    }

    public void setZweiteS(String zweiteS) {
        System.out.println("zweiteS set: " + singular);
        this.zweiteS = zweiteS;
    }

    public String getDritteS() {
        return dritteS;
    }

    public void setDritteS(String dritteS) {
        System.out.println("dritteS set: " + singular);
        this.dritteS = dritteS;
    }

    public String getVierteP() {
        return vierteP;
    }

    public void setVierteP(String vierteP) {
        System.out.println("vierteP set: " + singular);
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
        successCount--;
    }

    public void resetSuccesscount() {
        successCount = 0;
    }
}
