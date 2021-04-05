package dao;

import java.util.ArrayList;
import model.PessoaJuridica;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import util.HibernateUtil;

/**
 * Classe responsável por armazenar os métodos para acesso ao banco de dados
 * referente ao objeto PessoaJuridica
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class PessoaJuridicaDAO extends GenericDAO {
    /*
     * método para consultar as pessoas juridicas gravados na tabela
     */

    public ArrayList<PessoaJuridica> buscarTodos() throws Exception {
        //lista auxiliar para retornar no método
        ArrayList<PessoaJuridica> retorno = new ArrayList<>();
        //classe auxiliar para armazenar a sessão com o banco de dados
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //classe auxiliar para consultar o banco de dados
        Criteria criteria = sessao.createCriteria(PessoaJuridica.class);
        //adicionando a ordenação da pesquisa
        criteria.addOrder(Order.asc("idPessoaJuridica"));
        //valorizando o objeto de retorno do método com os registros da tabela
        retorno = (ArrayList<PessoaJuridica>) criteria.list();
        //encerrando a conexão com o banco de dados
        sessao.close();
        //retornando a lista preenchida
        return retorno;
    }//fim do método buscarTodos
}
