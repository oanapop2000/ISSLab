package repository;

import domain.Client;
import domain.validator.ClientValidator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ClientDBRepository implements ClientRepository{

    private ClientValidator clientValidator;

    public ClientDBRepository(ClientValidator validator) {
        try {
            initialize();
        }catch (Exception e){
            System.err.println("Exception "+e);
            e.printStackTrace();
            close();
        }
        this.clientValidator = validator;
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
    public Client findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Client> findAll() {
        return null;
    }

    @Override
    public void save(Client entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Client entity) {

    }

    @Override
    public Iterable<Client> findAllFromAnAgent(Integer idA) {
        List<Client> clientiModel = new ArrayList<>();
        List<Client> clienti;
        Iterable<Client> clientiIterable;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "from Client where idAgent = :idA";
                Query query = session.createQuery(hql);
                query.setParameter("idA", idA);

                clienti = query.list();

                for(Client client : clienti){
                    Client client1 = new Client(client.getNume(), client.getPrenume(), client.getAdresa(), client.getNrTel(), client.getIdAgent());
                    client1.setId(client.getId());
                    clientiModel.add(client1);
                }
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        clientiIterable = clientiModel;
        return clientiIterable;
    }
}
