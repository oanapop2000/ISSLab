package repository;

import domain.Client;

public interface ClientRepository extends Repository<Integer, Client>{
    Iterable<Client> findAllFromAnAgent(Integer id);
}
