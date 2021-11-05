package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminReportViewController implements Initializable {

    public AnchorPane incomeRoot;
    public JFXComboBox<String> cmbIncomeType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUI("DailyIncomeView");

        //=============================

        cmbIncomeType.getItems().addAll(
                "Daily Income",
                "Monthly Income"
        );
        cmbIncomeType.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.equals("Monthly Income")){
                loadUI("MonthlyIncomeView");
            }else if(newValue.equals("Daily Income")){
                loadUI("DailyIncomeView");
            }
        } ));
    }

    public void loadUI(String name){
        URL resource = getClass().getResource("../views/"+name+".fxml");
        Parent load = null;
        try {
            load = FXMLLoader.load(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        incomeRoot.getChildren().clear();
        incomeRoot.getChildren().add(load);
    }

}
