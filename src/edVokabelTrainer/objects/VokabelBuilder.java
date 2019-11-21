package edVokabelTrainer.objects;

public class VokabelBuilder {



    public Vokabel buildVokabel(String selected, String german) {
        Vokabel vokabel = new Vokabel(german);
        String[] mainSplitted = selected.split(",");
        System.out.println(mainSplitted.length + " Elemente");

        if(mainSplitted.length == 1) {
            vokabel.setSingular(mainSplitted[0].trim());
        }
        else if(mainSplitted.length == 2) {
            vokabel.setSingular(mainSplitted[0].trim());
            String[] plural = mainSplitted[1].split(":");
            if(plural.length != 2) {
                System.out.println("Fehler bei Plural");
            } else vokabel.setPlural(plural[1].trim());
        }
        else if(mainSplitted.length == 3) {
            complicatedThree(vokabel, mainSplitted);
        }
        int emptyCount = 0;
        for(String s : vokabel.getWordsAsList()) {
            if(s.equals("")) emptyCount++;
        }
        if(emptyCount == vokabel.getWordsAsList().size()) return null;
        else return vokabel;
    }

    private void complicatedThree(Vokabel vokabel, String[] mainSplitted) {
        String[] firstSplit = mainSplitted[0].split(" Konjugation");
        firstSplit = firstSplit[0].split(" Flexion");
        firstSplit = firstSplit[0].split(" Komparation");
        System.out.println("firstSplitt: " + firstSplit[0]);

        boolean noKonjugation = false;

        vokabel.setSingular(firstSplit[0].trim());
        String[] strWesen = firstSplit[0].split(" ");
        if(strWesen.length > 1) {
            System.out.println("strWesen 1: " + strWesen[1]);
            if(strWesen[1].equals("wesen")) {
                noKonjugation = true;
                vokabel.setGerman(vokabel.getGerman() + " sein");
            }
        }
        if(!noKonjugation) {
            String[] secondSplitt = mainSplitted[2].split(" ");
            int count = 0;
            for (String s : secondSplitt) {
                switch (s) {
                    case "ik":
                        vokabel.setErsteS(selectSplittedWord(secondSplitt, count));
                        break;
                    case "du":
                        vokabel.setZweiteS(selectSplittedWord(secondSplitt, count));
                        break;
                    case "he/se/dat":
                        vokabel.setDritteS(selectSplittedWord(secondSplitt, count));
                        break;
                    case "wi/ji/Se/se":
                        vokabel.setVierteP(selectSplittedWord(secondSplitt, count));
                        break;
                }
                count++;
            }
        }
    }

    private String selectSplittedWord(String[] str, int count) {
        String select = str[count + 1];
        String[] array = select.split("/");
        return array[0].trim();
    }

}
