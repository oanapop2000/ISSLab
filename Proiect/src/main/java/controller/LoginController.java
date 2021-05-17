package controller;

import domain.Agent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repository.RepositoryException;
import service.Service;

import java.io.IOException;

public class LoginController {
    public TextField textFieldName;
    public TextField textFieldPassword;
    Service service;

    public void setService(Service service){
        this.service=service;
    }

    public void openMain(ActionEvent actionEvent) {
        String name = textFieldName.getText();
        String password = textFieldPassword.getText();
        Agent agent = service.filterAgentsNamePassword(name, password);
        if (agent.getId() == null) {
            MessageAlert.showErrorMessage(null, "Datele introduse sunt incorecte!");
        } else {
            try {
                // create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/MainView.fxml"));

                AnchorPane root = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Welcome " + name + "!");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                //dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                MainController mainController = loader.getController();
                mainController.setService(service, agent);

                dialogStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

