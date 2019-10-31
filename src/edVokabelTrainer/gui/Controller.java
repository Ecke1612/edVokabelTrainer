package edVokabelTrainer.gui;

import edVokabelTrainer.handling.DataHandling;
import edVokabelTrainer.objects.Dictonary;
import edVokabelTrainer.objects.Tools;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.util.ArrayList;

public class Controller {

    public Button btn_ok;
    public Label label_out;
    public Label label_vok;
    public TextField textfield;
    public BorderPane borderPane;

    private DataHandling datahandler = new DataHandling();
    private Tools tools = new Tools();
    private int gamepointer = 0;
    ArrayList<Integer> rList;

    public Controller() {

    }

    public void initialize() {
        File file = new File("saves/save.dat");
        if(file.exists()) {
            datahandler.load();
        }

        setGlobalEventHandler(borderPane);
    }

    public void importDic() {
        datahandler.importDictonary();
    }

    public void startTraining() {
        gamepointer = 0;
        Dictonary dictonary = datahandler.getActiveDictionary();
        rList = tools.getRandomIntArray(dictonary.getEntrySets().size());
        nextEntry();
    }

    private void nextEntry() {
        if(gamepointer >= rList.size()) {
            startTraining();
        }
        label_vok.setText(datahandler.getActiveDictionary().getEntrySets().get(rList.get(gamepointer)).getGermanWord());
    }

    public void close() {

    }

    public void check_vok() {
        String input = textfield.getText();
        String correctAnswer = (datahandler.getActiveDictionary().getEntrySets().get(rList.get(gamepointer)).getForeignWord());
        if(input.equals(correctAnswer)) {
            label_out.setText("Korrekt, sehr gut");
            datahandler.getActiveDictionary().getEntrySets().get(rList.get(gamepointer)).countCorrectTranslatedUp();
        } else {
            label_out.setText("Leider falsch");
            if (datahandler.getActiveDictionary().getEntrySets().get(rList.get(gamepointer)).getCorrectTranslated() > 0) {
                datahandler.getActiveDictionary().getEntrySets().get(rList.get(gamepointer)).countCorrectTranslatedUp();
            }
        }
        textfield.setText("");
        gamepointer++;
        nextEntry();
        datahandler.save();
    }


    private void setGlobalEventHandler(Node root) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                btn_ok.fire();
                ev.consume();
            }
        });
    }

}
