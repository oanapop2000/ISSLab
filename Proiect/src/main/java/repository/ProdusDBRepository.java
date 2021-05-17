package repository;

import domain.Produs;
import domain.validator.ProdusValidator;
import domain.validator.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class ProdusDBRepository implements ProdusRepository{

    private ProdusValidator produsValidator;

    public ProdusDBRepository(ProdusValidator produsValidator) {
        try {
            initialize();
        }catch (Exception e){
            System.err.println("Exception "+e);
            e.printStackTrace();
            close();
        }
        this.produsValidator = produsValidator;
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
    public Produs findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Produs> findAll() {
        List<Produs> produseModel = new ArrayList<>();
        List<Produs> produse;
        Iterable<Produs> produseIterable;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                produse =
                        session.createQuery("from Produs", Produs.class).
                                list();
                for(Produs produs : produse){
                    Produs produs1 = new Produs(produs.getNume(), produs.getPret(), produs.getCantitate());
                    produseModel.add(produs1);
                }
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        produseIterable = produseModel;
        return produseIterable;
    }

    @Override
    public void save(Produs entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Produs entity) {

    }
}
