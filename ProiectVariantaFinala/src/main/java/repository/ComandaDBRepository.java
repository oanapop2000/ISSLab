package repository;

import domain.Comanda;
import domain.validator.ComandaValidator;
import domain.validator.ValidationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ComandaDBRepository implements ComandaRepository{


    private ComandaValidator comandaValidator;

    public ComandaDBRepository(ComandaValidator validator) {
        try {
            initialize();
        }catch (Exception e){
            System.err.println("Exception "+e);
            e.printStackTrace();
            close();
        }
        this.comandaValidator = validator;
    }

    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    @Override
    public Comanda findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Comanda> findAll() {
        return null;
    }

    @Override
    public void save(Comanda entity) {
        try {
            comandaValidator.validate(entity);
            try(Session session = sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    Comanda comanda = new Comanda(entity.getIdAgent(), entity.getIdClient(), entity.getIdProdus(), entity.getCantitate(), entity.getStatus());
                    session.save(comanda);
                    tx.commit();
                } catch (RuntimeException ex) {
                    if (tx != null)
                        tx.rollback();
                }
            }
        }catch (ValidationException e){
            throw e;
        }

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Comanda entity) {

    }

    @Override
    public Comanda deleteByAgent(Integer id, Integer idA) {
        Comanda comanda = new Comanda();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String hql = "from Comanda where id = :id and idAgent = :idA";
                Query query = session.createQuery(hql);
                query.setParameter("idA", idA);
                query.setParameter("id", id);

                List<Comanda> comenzi = query.list();

                comanda.setId(comenzi.get(0).getId());
                comanda.setIdProdus(comenzi.get(0).getIdProdus());
                comanda.setCantitate(comenzi.get(0).getCantitate());

                session.delete(comenzi.get(0));
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return comanda;
    }

    @Override
    public Iterable<Comanda> findAllByAgent(Integer idA) {
        List<Comanda> comenziModel = new ArrayList<>();
        List<Comanda> comenzi;
        Iterable<Comanda> comenziIterable;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "from Comanda where idAgent = :idA";
                Query query = session.createQuery(hql);
                query.setParameter("idA", idA);

                comenzi = query.list();

                for(Comanda comanda : comenzi){
                    Comanda comanda1 = new Comanda(comanda.getIdAgent(), comanda.getIdClient(), comanda.getIdProdus(), comanda.getCantitate(), comanda.getStatus());
                    comanda1.setId(comanda.getId());
                    comenziModel.add(comanda1);
                }
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        comenziIterable = comenziModel;
        return comenziIterable;
    }
}
