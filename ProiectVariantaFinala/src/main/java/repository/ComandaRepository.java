package repository;

import domain.Comanda;

public interface ComandaRepository extends Repository<Integer, Comanda>{
    Comanda deleteByAgent(Integer id, Integer idA);
    Iterable<Comanda> findAllByAgent(Integer idA);
}
