package service;

import domain.Agent;
import domain.Produs;
import repository.AgentRepository;
import repository.ProdusRepository;
import repository.RepositoryException;

public class Service {
    private AgentRepository agentRepository;
    private ProdusRepository produsRepository;

    public Service(AgentRepository agentRepository, ProdusRepository produsRepository) {
        this.agentRepository = agentRepository;
        this.produsRepository = produsRepository;
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
}

