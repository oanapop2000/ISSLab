package controller;

import domain.*;
import domain.validator.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

import java.util.ArrayList;
import java.util.List;

public class ComandaController  {
    public TextField label;
    public TextField labelCantitate;
    public TableColumn<Comanda, Integer> coloanaId;
    public TableColumn<Comanda, Agent> coloanaIdAgent;
    ObservableList<Client> clientiModel = FXCollections.observableArrayList();
    public TableView<Client> table;
    public TableColumn<Client, String> coloanaNume;
    public TableColumn<Client, String> coloanaPrenume;
    public TableColumn<Client, String> coloanaAdresa;
    public TableColumn<Client, Integer> coloanaTelefon;
    Service services;
    Agent agent;
    Produs produs;
    public void setService(Service service, Agent agent, Produs produs){
        this.services=service;
        this.agent = agent;
        this.produs = produs;
        label.setText("Ati selectat: " + produs.getNume());
        initClientiTabel();
    }

    @FXML
    public void initialize() {
        coloanaNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        coloanaPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        coloanaAdresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        coloanaTelefon.setCellValueFactory(new PropertyValueFactory<>("nrTel"));
        coloanaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        coloanaIdAgent.setCellValueFactory(new PropertyValueFactory<>("idAgent"));
        table.setItems(clientiModel);
    }

    void initClientiTabel(){
        Iterable<Client> clienti = services.findAllClienti(this.agent.getId());
        List<Client> clientList = new ArrayList<>();
        for(Client client : clienti){
            clientList.add(client);
        }
        clientiModel.setAll(clientList);
    }

    public void salveazaHandle(ActionEvent actionEvent) {
        String cantitate = labelCantitate.getText();
        Client client = table.getSelectionModel().getSelectedItem();
        if (cantitate == ""){
            MessageAlert.showErrorMessage(null, "Nu ati introdus cantitatea dorita!");
        }
        if(client == null) {
            MessageAlert.showErrorMessage(null, "Nu ati selectat niciun client!");
        }
        try{
            Integer.parseInt(cantitate);
            if(Integer.parseInt(cantitate) > produs.getCantitate()){
                MessageAlert.showErrorMessage(null, "Cantitatea introdusa este prea mare!");
            }else{
                try {
                    services.addComanda(this.agent.getId(), client.getId(), produs.getId(), Integer.parseInt(cantitate), "IN_ASTEPTARE");
                    this.produs.setCantitate(this.produs.getCantitate() - Integer.parseInt(cantitate));
                    services.updateProdus(this.produs);
                    MessageAlert.showInfoMessage(null,"Comanda a fost inregistrata!");
                }catch (ValidationException e){
                    MessageAlert.showErrorMessage(null,e.getMessage());
                }

            }
        }catch (NumberFormatException e){
            MessageAlert.showErrorMessage(null, e.getMessage());
        }

    }
}
