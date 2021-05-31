package repository;

import domain.Produs;
import domain.validator.ProdusValidator;
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
        Produs produs = new Produs();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "from Produs where id = :integer";
                Query query = session.createQuery(hql);
                query.setParameter("integer", integer);

                List<Produs> produses = query.list();
                produs.setId(integer);
                produs.setNume(produses.get(0).getNume());
                produs.setPret(produses.get(0).getPret());
                produs.setCantitate(produses.get(0).getCantitate());
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return produs;
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
                    produs1.setId(produs.getId());
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
        try {
            produsValidator.validate(entity);
            try(Session session = sessionFactory.openSession()){
                Transaction tx=null;
                try{
                    tx = session.beginTransaction();
                    Produs produs = session.load( Produs.class, new Integer(entity.getId()) );
                    produs.setNume(entity.getNume());
                    produs.setPret(entity.getPret());
                    produs.setCantitate(entity.getCantitate());
                    tx.commit();

                } catch(RuntimeException ex){
                    if (tx!=null)
                        tx.rollback();
                }
            }
        }catch (ValidationException e){
            throw e;
        }
    }
}
