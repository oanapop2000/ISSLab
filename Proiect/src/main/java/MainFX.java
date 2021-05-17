
import controller.LoginController;
import domain.validator.AgentValidator;
import domain.validator.ProdusValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import repository.AgentDBRepository;
import repository.AgentRepository;
import repository.ProdusDBRepository;
import repository.ProdusRepository;
import service.Service;
import service.ServicesException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static javafx.application.Application.launch;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            Parent root = loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app "+e);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    static Service getService(){
        AgentRepository agentRepo=new AgentDBRepository(new AgentValidator());
        ProdusRepository produsRepository = new ProdusDBRepository(new ProdusValidator());
        return new Service(agentRepo, produsRepository);

    }
}
