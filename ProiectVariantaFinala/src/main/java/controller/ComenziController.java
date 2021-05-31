package controller;

import domain.Agent;
import domain.Comanda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;
import utils.events.ChangeEvent;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ComenziController implements Observer<ChangeEvent> {

    public TableView<Comanda> table;
    public TableColumn<Comanda, Integer> coloanaClient;
    public TableColumn<Comanda, Integer> coloanaProdus;
    public TableColumn<Comanda, Integer> coloanaCantitate;
    public TableColumn<Comanda, String> coloanaStatus;
    public TableColumn<Comanda, Integer> coloanaAgent;
    public TableColumn<Comanda, Integer> coloanaId;
    ObservableList<Comanda> comenziModel = FXCollections.observableArrayList();
    Service services;
    Agent agent;
    public void setService(Service service, Agent agent){
        this.services=service;
        this.agent = agent;
        this.services.addObserver(this);
        initComenziTabel();
    }

    @FXML
    public void initialize() {
        coloanaClient.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        coloanaProdus.setCellValueFactory(new PropertyValueFactory<>("idProdus"));
        coloanaCantitate.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        coloanaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        coloanaAgent.setCellValueFactory(new PropertyValueFactory<>("idAgent"));
        coloanaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.setItems(comenziModel);
    }

    void initComenziTabel(){
        Iterable<Comanda> comenzi = services.findAllComenzi(this.agent.getId());
        List<Comanda> comandaList = new ArrayList<>();
        for(Comanda comanda : comenzi){
            comandaList.add(comanda);
        }
        comenziModel.setAll(comandaList);
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        initComenziTabel();
    }
}
