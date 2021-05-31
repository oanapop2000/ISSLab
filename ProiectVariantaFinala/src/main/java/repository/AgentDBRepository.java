package repository;

import domain.Agent;
import domain.validator.AgentValidator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class AgentDBRepository implements AgentRepository{

    private AgentValidator agentValidator;

    public AgentDBRepository(AgentValidator validator) {
        try {
            initialize();
        }catch (Exception e){
            System.err.println("Exception "+e);
            e.printStackTrace();
            close();
        }
        this.agentValidator = validator;
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
    public Agent findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Agent> findAll() {
        return null;
    }

    @Override
    public void save(Agent entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Agent entity) {

    }

    @Override
    public Agent filterByNamePassword(String nume, String parola){
        Agent agent = new Agent(" "," ");
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "from Agent where nume = :nume and parola = :parola ";
                Query query = session.createQuery(hql);
                query.setParameter("nume", nume);
                query.setParameter("parola", parola);

                List<Agent> agents = query.list();
                agent.setNume(agents.get(0).getNume());
                agent.setId(agents.get(0).getId());
                agent.setParola(agents.get(0).getParola());
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return agent;
    }
}
