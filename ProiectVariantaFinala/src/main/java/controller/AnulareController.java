package controller;

import domain.Agent;
import domain.Comanda;
import domain.Produs;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import service.Service;
import service.ServicesException;

public class AnulareController {

    public TextField textField;
    Service services;
    Agent agent;
    public void setService(Service service, Agent agent){
        this.services=service;
        this.agent = agent;
    }


    public void anuleaza(ActionEvent actionEvent) {
        String id = textField.getText();
        try {
            Integer.parseInt(id);
            try {
                Comanda comanda = services.stergeComanda(Integer.parseInt(id), agent.getId());
                Produs produs = services.cautaProdus(comanda.getIdProdus());
                produs.setCantitate(produs.getCantitate() + comanda.getCantitate());
                services.updateProdusStergere(produs);
                MessageAlert.showInfoMessage(null, "Comanda stearsa cu succes!");
            }catch (ServicesException e){
                MessageAlert.showErrorMessage(null, e.getMessage());
            }

        }catch (NumberFormatException e){
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }
}
