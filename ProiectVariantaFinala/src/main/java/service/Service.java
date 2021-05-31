package service;

import domain.*;
import repository.*;
import utils.events.ChangeEvent;
import utils.events.ChangeEventType;
import utils.observer.Observable;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Service implements Observable<ChangeEvent> {
    private AgentRepository agentRepository;
    private ProdusRepository produsRepository;
    private ClientRepository clientRepository;
    private ComandaRepository comandaRepository;

    public Service(AgentRepository agentRepository, ProdusRepository produsRepository, ClientRepository clientRepository, ComandaRepository comandaRepository) {
        this.agentRepository = agentRepository;
        this.produsRepository = produsRepository;
        this.clientRepository = clientRepository;
        this.comandaRepository = comandaRepository;
    }

    public Agent filterAgentsNamePassword(String name, String password){
        try {
            return agentRepository.filterByNamePassword(name,password);
        }catch (RepositoryException e){
            throw e;
        }
    }

    public Iterable<Produs> findAllProduse(){
        return produsRepository.findAll();
    }

    public Iterable<Client> findAllClienti(int id){
        return clientRepository.findAllFromAnAgent(id);
    }

    public void addComanda(int agentId, int clientId, int produsId, int cantitate, String status){
        Comanda comanda = new Comanda(agentId, clientId, produsId, cantitate, status);
        comandaRepository.save(comanda);
    }

    public void updateProdus(Produs produs){
        produsRepository.update(produs);
        notifyObservers(new ChangeEvent(ChangeEventType.ADD));
    }

    public void updateProdusStergere(Produs produs){
        produsRepository.update(produs);
        notifyObservers(new ChangeEvent(ChangeEventType.DELETE));
    }

    private List<Observer<ChangeEvent>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void notifyObservers(ChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Comanda stergeComanda(Integer id, Integer idA) throws ServicesException{
        Comanda comanda = comandaRepository.deleteByAgent(id, idA);
        if(comanda.getId() == null){
            throw new ServicesException("Nu exista comenzi efectuate de dumneavoastra cu acest id!");
        }
        else {
            return comanda;
        }
    }

    public Produs cautaProdus(Integer id){
        return produsRepository.findOne(id);
    }

    public Iterable<Comanda> findAllComenzi(Integer idA){
        return comandaRepository.findAllByAgent(idA);
    }
}

