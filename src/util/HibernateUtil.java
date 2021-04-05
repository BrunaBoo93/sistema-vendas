package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * Classe resposnável por efetuar a conexão com o banco de dados
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class HibernateUtil {
    
    //atributo para armazenar a seção de comunicação
    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    /*
    * método para criar uma sessão de conexão com o banco de dados
    */
    private static SessionFactory buildSessionFactory() {
        return new AnnotationConfiguration().configure().buildSessionFactory();
    }
    
    //método para acessar o atributo de conexão e seção com o banco de dados
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
