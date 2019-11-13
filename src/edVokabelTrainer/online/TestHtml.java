package edVokabelTrainer.online;

public class TestHtml {

    private static String[] testing = {"Auto",  "baden",  "bald",  "Ball",  "bauen",  "Bauer",  "Baum",  "bei",  "beide",  "beim",
            "Bein",  "Beispiel",  "beißen",  "bekommen",  "Berg",  "besser",  "Bett",  "Bild",  "bin",  "bis",  "blau",  "bleiben",
            "Blume",  "Boden",  "böse",  "brauchen",  "braun",  "Brief",  "bringen",  "Brot",  "Bruder",  "Buch"};

    public static void main(String args[]) {
        HTMLRequest htmlRequest = new HTMLRequest();
        for(String s : testing) {
            htmlRequest.callHttp(s);
        }
    }

}
