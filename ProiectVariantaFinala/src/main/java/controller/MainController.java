package controller;

import domain.Agent;
import domain.Produs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.Service;
import utils.events.ChangeEvent;
import utils.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController implements Observer<ChangeEvent> {

    public TableView<Produs> tabelProduse;
    public TableColumn<Produs, String> coloanaNume;
    public TableColumn<Produs, Integer> coloanaPret;
    public TableColumn<Produs, Integer> coloanaCantitate;
    public TableColumn<Produs, Integer> coloanaId;
    Service services;
    Agent agent;
    ObservableList<Produs> produseModel = FXCollections.observableArrayList();

    public void setService(Service service, Agent agent){
        this.services=service;
        this.agent = agent;
        this.services.addObserver(this);
        initProduseTabel();
    }

    @FXML
    public void initialize() {
        coloanaNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        coloanaPret.setCellValueFactory(new PropertyValueFactory<>("pret"));
        coloanaCantitate.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        coloanaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tabelProduse.setItems(produseModel);
    }

    public void initProduseTabel(){
        Iterable<Produs> produse = services.findAllProduse();
        List<Produs> produsList = new ArrayList<>();
        for(Produs produs : produse){
            produsList.add(produs);
        }
        produseModel.setAll(produsList);
    }

    public void openComanda(ActionEvent actionEvent) {
        Produs produs = tabelProduse.getSelectionModel().getSelectedItem();
        if(produs == null){
            MessageAlert.showErrorMessage(null,"Nu ati selectat niciun produs!");
        }else{
            try {
                // create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/ComandaView.fxml"));

                AnchorPane root = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Fereastra de cumparare pt " + this.agent.getNume());
                dialogStage.initModality(Modality.WINDOW_MODAL);
                //dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                ComandaController controller =loader.getController();
                controller.setService(services, agent, produs);

                dialogStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openAnulare(ActionEvent actionEvent) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AnulareView.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Fereastra de anulare pt " + this.agent.getNume());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AnulareController anulareController =loader.getController();
            anulareController.setService(services, agent);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openComenzi(ActionEvent actionEvent) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ComenziView.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Fereastra pt vizualizare comenzi " + this.agent.getNume());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ComenziController comenziController =loader.getController();
            comenziController.setService(services, agent);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openClienti(ActionEvent actionEvent) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ClientiView.fxml"));

            AnchorPane root = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Fereastra de vizualizare clienti pt " + this.agent.getNume());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ClientiController clientiController =loader.getController();
            clientiController.setService(services, agent);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ChangeEvent produsChangeEvent) {
        initProduseTabel();
    }
}
