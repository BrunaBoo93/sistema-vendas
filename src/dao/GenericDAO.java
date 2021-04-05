package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 * Classe responsável por armazenar os métodos genéricos de incluir alterar e
 * excluir
 *
 * @author Bruna Oriani
 * @since 17/03/2021
 * @version 1.0
 */
public abstract class GenericDAO {

    /*
     * método genérico para incluir e alterar um objeto
     */
    public void salvar(Object obj) {
        // classe auxiliar para armazenar uma sessao com o banco de dados
        Session sessao = null;
        // classe auxiliar pára efwtuar transações com banco de dados
        Transaction transacao = null;

        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            transacao = sessao.beginTransaction();

            //incluindo ou alterando um objeto
            sessao.saveOrUpdate(obj);
            transacao.commit();
        } catch (Exception e) {
            transacao.rollback();
        } finally {
            sessao.close();
        }

    }//fim do método salvar

    /*
     * método genérico para excluir um registro
     */
    public void excluir(Object obj) {
        // classe auxiliar para armazenar uma sessao com o banco de dados
        Session sessao = null;
        // classe auxiliar pára efetuar transações com banco de dados
        Transaction transacao = null;

        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            transacao = sessao.beginTransaction();

            //excluindo um objeto
            sessao.delete(obj);
            transacao.commit();
        } catch (Exception e) {
            transacao.rollback();
        } finally {
            sessao.close();
        }

    }//fim do método excluir

}
