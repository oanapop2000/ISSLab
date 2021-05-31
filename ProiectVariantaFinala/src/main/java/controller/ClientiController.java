package controller;

import domain.Agent;
import domain.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

import java.util.ArrayList;
import java.util.List;

public class ClientiController {

    public TableColumn<Client, Integer> coloanaIdAgent;
    ObservableList<Client> clientiModel = FXCollections.observableArrayList();
    Service services;
    Agent agent;
    public TableView<Client> table;
    public TableColumn<Client, String> coloanaNume;
    public TableColumn<Client, String> coloanaPrenume;
    public TableColumn<Client, String> coloanaAdresa;
    public TableColumn<Client, Integer> coloanaTelefon;

    public void setService(Service service, Agent agent){
        this.services=service;
        this.agent = agent;
        initClientiTabel();
    }

    @FXML
    public void initialize() {
        coloanaNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        coloanaPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        coloanaAdresa.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        coloanaTelefon.setCellValueFactory(new PropertyValueFactory<>("nrTel"));
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
}
