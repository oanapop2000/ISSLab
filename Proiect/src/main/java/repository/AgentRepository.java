package repository;

import domain.Agent;

public interface AgentRepository extends Repository<Integer, Agent>{
    Agent filterByNamePassword(String name, String password);
}
