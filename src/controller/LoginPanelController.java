package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.LoginDAO;
import dao.custom.impl.LoginDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import dto.UserDetailDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginPanelController {

    public Button loginBtn;
    public JFXRadioButton recRadBtn;
    public JFXRadioButton adminRadBtn;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;

    private final LoginDAO loginDAO = new LoginDAOImpl();

    public void loginOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        final boolean[] goToIn = {false};

        ArrayList<UserDetailDTO> receptionDetails = loginDAO.getAllUsers(recRadBtn.getText());
        receptionDetails.forEach(e->{
            if(e.getUserName().equals(txtUserName.getText()) && e.getPassword().equals(txtPassword.getText()) && recRadBtn.isSelected()){
                System.out.println(e.getAccountType());

                loadUI("CustomerRoot");
                goToIn[0] =true;
            }
        });

        ArrayList<UserDetailDTO> adminDetails = loginDAO.getAllUsers(adminRadBtn.getText());
        adminDetails.forEach(e->{
            if(e.getUserName().equals(txtUserName.getText()) && e.getPassword().equals(txtPassword.getText()) && adminRadBtn.isSelected()){
                System.out.println(e.getAccountType());

                loadUI("AdminRoot");
                goToIn[0] =true;
            }
        });

        if(!goToIn[0]){
            new Alert(Alert.AlertType.ERROR,"Invalid User !!").show();
        }
    }

    private void loadUI(String name){
        Stage primaryStage = (Stage) loginBtn.getScene().getWindow();
        primaryStage.close();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../views/"+name+".fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
