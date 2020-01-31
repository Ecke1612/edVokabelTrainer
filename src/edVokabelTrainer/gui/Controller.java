package edVokabelTrainer.gui;

import edVokabelTrainer.handling.DataHandling;
import edVokabelTrainer.objects.Dictonary;
import edVokabelTrainer.objects.Vokabel;
import edVokabelTrainer.online.AutoRequest;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    public Button btn_ok;
    public Label label_out;
    public Label label_vok;
    public TextField textfield;
    public BorderPane borderPane;
    public Label info_label;
    public Menu menu_dictionaries;
    public Label label_title;
    public Label label_feedback;
    public Button btn_toggleTraining;
    public Label label_person;
    public Button btn_editvok;
    public MenuItem menuExercise;
    public MenuItem menuRepetition;

    public static final int learnIndex = 5;

    private DataHandling datahandler = new DataHandling();
    private AddVokWindowOnline addVokWindowOnline = new AddVokWindowOnline();
    private int globalRIndex = 0;
    private Random random = new Random();
    private boolean vocableActive = false;
    private ArrayList<Vokabel> activeVokabelList;
    private boolean repeatState = false;
    private int countDics = 0;
    private boolean stateTrainingRunning = false;
    private String correctAnswer;
    private Vokabel chosenVokabel;
    private AutoRequest autoRequest = new AutoRequest();

    public Controller() {

    }

    public void initialize() {
        if(datahandler.loadFromFile()) {
            if(datahandler.dicLoaded()) {
                info_label.setText(datahandler.getActiveDictionary().getName() + " geladen");
            }
            setTitle();
            changeUIActiveState();
        }
        initMenuDictionaries();
        setGlobalEventHandler(borderPane);
        changeUIActiveState();
    }

    private void setTitle() {
        if(datahandler.dicLoaded()) {
            String[] strArray = datahandler.getActiveDictionary().getName().split("\\.");
            String title = strArray[0];
            label_title.setText(title);
        } else {
            label_title.setText("ED Vokabeltrainer");
        }
    }

    public void importDic() {
        datahandler.importDictonary();
        initMenuDictionaries();
        setTitle();
        changeUIActiveState();
        datahandler.save();
    }

    public void startTraining() {
        if(stateTrainingRunning) {
            stopTraining();
        } else {
            btn_toggleTraining.setText("\uE103");
            System.out.println("start training");
            activeVokabelList = (datahandler.getActiveDictionary().getVokabelList());
            info_label.setText(activeVokabelList.size() + " Vokablen geladen");
            repeatState = false;
            stateTrainingRunning = true;
            changeUIActiveState();
            nextEntry();
        }
    }

    public void startRepetition() {
        if(repeatState) {
            stopTraining();
        } else {
            System.out.println("start Repetition");
            activeVokabelList = datahandler.getActiveDictionary().getLearndVokabelList();
            info_label.setText(activeVokabelList.size() + " Vokablen geladen");
            repeatState = true;
            stateTrainingRunning = true;
            changeUIActiveState();
            nextEntry();
        }
    }

    private void nextEntry() {
        //System.out.println("next Entry");
        textfield.setText("");
        textfield.setStyle("-fx-text-fill: white");
        label_feedback.setText("");
        if(activeVokabelList.size() != 0) {
            chosenVokabel = pickVokabel();
            label_vok.setText(chosenVokabel.getGerman());
            int settetFields = chosenVokabel.getSettetFieldCount();
            int calculatedLearnIndex =(int)(learnIndex * (1 + ((settetFields  - 1)* 0.5)));
            info_label.setText("Vokabel hat " + settetFields + " Wörter, es bedarf einen Lernindex von: " + calculatedLearnIndex + " um als gelernt zu gelten.");
            int correctIndex = chooseFormFromVokabel(chosenVokabel);
            label_person.setText(datahandler.getActiveDictionary().getDicMetaData().getPersonByIndex(correctIndex));
            correctAnswer = chosenVokabel.getWordByIndex(correctIndex);
            vocableActive = true;
        } else {
            label_out.setText("Glückwunsch, alle Vokabeln gelernt");
        }
    }

    private Vokabel pickVokabel() {
        int rIndex = random.nextInt(activeVokabelList.size());
        globalRIndex = rIndex;
        return activeVokabelList.get(rIndex);
    }

    private void stopTraining() {
        if(activeVokabelList != null) {
            //activeVokabelList.clear();
            label_vok.setText("");
            info_label.setText("Übungen wurde gestoppt");
            stateTrainingRunning = false;
            btn_toggleTraining.setText("\uE102");
            changeUIActiveState();
        }
    }

    private void changeUIActiveState() {
        //System.out.println("stateTR: " + stateTrainingRunning + "; dicLoaded: " + datahandler.dicLoaded());
        if(datahandler.dicLoaded()) {
            btn_toggleTraining.setDisable(false);
            if (stateTrainingRunning) {
                btn_ok.setDisable(false);
                textfield.setDisable(false);
                btn_editvok.setDisable(false);
            } else {
                //System.out.println("deactivate UI");
                btn_ok.setDisable(true);
                textfield.setDisable(true);
                btn_editvok.setDisable(true);
            }
        } else {
            btn_toggleTraining.setDisable(true);
        }
    }


    public void check_vok() {
        if(stateTrainingRunning) {
            if (vocableActive) {
                if(correctAnswer.split(" ").length == 1 || correctAnswer.split(" ").length >= 3) {
                    System.out.println("correct equals: " + correctAnswer);
                    if(textfield.getText().equals(correctAnswer)) answerIsCorrect();
                    else answerIsNotCorrect();
                } else {
                    System.out.println("correct contains: " + correctAnswer);
                    String answer = textfield.getText();
                    if (isContain(correctAnswer, answer) && !answer.equals("de") && !answer.equals("dat") && !answer.trim().equals("")) {
                        answerIsCorrect();
                    } else {
                        answerIsNotCorrect();
                    }
                }
                textfield.setStyle("-fx-text-fill: rgb(125,175,125)");
                datahandler.save();
                btn_ok.setText("Nächste");
                vocableActive = false;
            } else {
                btn_ok.setText("Prüfen");
                nextEntry();
                label_out.setText("");
            }
        } else {

        }
    }

    private void answerIsCorrect() {
        if (!repeatState) {
            chosenVokabel.raisSuccessCount();
            int calculatedLearnIndex =(int)(learnIndex * (1 + ((chosenVokabel.getSettetFieldCount()  - 1)* 0.5)));
            if (chosenVokabel.getSuccessCount() >= calculatedLearnIndex) {
                datahandler.getActiveDictionary().moveVokabelToLearnd(chosenVokabel);
                //activeVokabelList.remove(chosenVokabel);
                info_label.setText(chosenVokabel.getGerman() + " wurde als gelernt eingestuft. noch " + activeVokabelList.size() + " Vokablen zu lernen.");
                System.out.println("Vokabel wurde als gelernt eingestuft");
                initMenuVokSize();
            }
        }
        label_out.setTextFill(Color.WHITE);
        label_out.setText("Super! " + correctAnswer + " ist korrekt. (" + chosenVokabel.getSuccessCount() + ")");
        label_feedback.setText("\uE001");
        label_feedback.setTextFill(Color.rgb(147,255,130));
    }

    private boolean isContain(String source, String subTerm) {
        String pattern = "\\b" + subTerm + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(source);
        return m.find();
    }

    private void answerIsNotCorrect() {
        label_out.setTextFill(Color.rgb(136, 0, 21));
        label_out.setText("Leider falsch. " + correctAnswer + " wäre richtig gewesen. (" + chosenVokabel.getSuccessCount() + ")");
        label_feedback.setText("\uE106");
        label_feedback.setTextFill(Color.rgb(136,0,21));
        chosenVokabel.lowerSuccessCount();
        if (repeatState) {
            datahandler.getActiveDictionary().moveVokabelToActiveList(chosenVokabel);
            //activeVokabelList.remove(chosenVokabel);
            info_label.setText(chosenVokabel.getSingular() + " wurde zurück zu lernen verschoben. noch " + activeVokabelList.size() + " Vokablen zu wiederholen");
            initMenuVokSize();
        }
    }

    private int chooseFormFromVokabel(Vokabel vokabel) {
        ArrayList<Integer> availableWords = new ArrayList<>();
        int count = 0;
        for(String s : vokabel.getWordsAsList()) {
            if(!s.equals("")) availableWords.add(count);
            count++;
        }
        int index = random.nextInt(availableWords.size());
        return availableWords.get(index);
    }

    public void initMenuDictionaries() {
        //System.out.println("show dics");
        initMenuVokSize();
        menu_dictionaries.getItems().clear();
        countDics = 0;
        //System.out.println("dic size: " + datahandler.getDictonaries().size());
        for(Dictonary d : datahandler.getDictonaries()) {
            int locDic = countDics;
            MenuItem menuItem = new MenuItem(d.getName());
            menuItem.setOnAction(event -> {
                changeDictionarySelection(locDic);
            });
            menu_dictionaries.getItems().add(menuItem);
            countDics++;
        }
    }

    private void initMenuVokSize() {
        try {
            menuExercise.setText("Übung starten (" + datahandler.getActiveDictionary().getVokabelList().size() + ")");
            menuRepetition.setText("Wiederholung starten (" + datahandler.getActiveDictionary().getLearndVokabelList().size() + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editVok() {
        addVokWindowOnline.drawWindow(datahandler);
        addVokWindowOnline.setEditMode(true);
        addVokWindowOnline.setFieldsFromVokabel(chosenVokabel);
    }

    public void deleteVok() {
        if(confirmDialog("Vokabel löschen?", "Möchtest du diese Vokabel wirklisch löschen?")) {
            activeVokabelList.remove(chosenVokabel);
            datahandler.save();
            nextEntry();
        }
    }

    private boolean confirmDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    private void changeDictionarySelection(int locDic) {
        stopTraining();
        datahandler.getStoreSettingsObject().setActiveDic(locDic);
        info_label.setText(datahandler.getActiveDictionary().getName() + " wurde geladen");
        setTitle();
        initMenuVokSize();
    }

    public void resetDic() {
        if(confirmDialog("Fortschritt löschen?", "Möchtest du deinen Lernfortschritt wirklich löschen?")) {
            for (Vokabel v : datahandler.getActiveDictionary().getVokabelList()) {
                v.resetSuccesscount();
            }
            while (datahandler.getActiveDictionary().getLearndVokabelList().size() != 0) {
                datahandler.getActiveDictionary().moveVokabelToActiveList(datahandler.getActiveDictionary().getLearndVokabelList().get(0));
            }
            datahandler.save();
            initMenuVokSize();
        }
    }

    public void deleteDic() {
        stopTraining();
        datahandler.getStoreSettingsObject().deleteActiveDic();
        datahandler.reload();
        initMenuDictionaries();
        setTitle();
        changeUIActiveState();
        datahandler.save();
    }

    public void addVokabel() {
        addVokWindowOnline.drawWindow(datahandler);
    }


    private void setGlobalEventHandler(Node root) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                btn_ok.fire();
                ev.consume();
            }
        });
    }

    public void autoImport() {
        autoRequest.autoRequestPerFile(datahandler);
        datahandler.reload();
    }

    public void close() {

    }

}
