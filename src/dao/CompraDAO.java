package dao;

import java.util.ArrayList;
import model.Compra;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import util.HibernateUtil;

/**
 * Classe responsável por armazenar os métodos para acesso ao banco de dados
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class CompraDAO {
    /*
     * método para consultar os Compras gravados na tabela
     */

    public ArrayList<Compra> buscarTodos() throws Exception {
        //lista auxiliar para retornar no método
        ArrayList<Compra> retorno = new ArrayList<>();
        //classe auxiliar para armazenar a sessão com o banco de dados
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //classe auxiliar para consultar o banco de dados
        Criteria criteria = sessao.createCriteria(Compra.class);
        //adicionando a ordenação da pesquisa
        criteria.addOrder(Order.asc("idCompra"));
        //valorizando o objeto de retorno do método com os registros da tabela
        retorno = (ArrayList<Compra>) criteria.list();
        //encerrando a conexão com o banco de dados
        sessao.close();
        //retornando a lista preenchida
        return retorno;
    }//fim do método buscarTodos
}
