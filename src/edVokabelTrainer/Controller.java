package edVokabelTrainer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    public Button btn_ok;
    public Label label_out;
    public Label label_vok;
    public TextField textfield;

    public DataHandling datahandler = new DataHandling();

    public Controller() {

    }

    public void initialize() {

    }

    public void importDic() {
        datahandler.importDictonary();
    }

    public void startTraining() {

    }

    public void close() {

    }

    public void check_vok() {

    }

}
