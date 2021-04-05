package dao;

import java.util.ArrayList;
import model.Fornecedor;
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
public class FornecedorDAO extends GenericDAO{ 
    
    /*
    * método para consultar os Fornecedores gravados na tabela
    */
    public ArrayList<Fornecedor> buscarTodos() throws Exception{
        //lista auxiliar para retornar no método
        ArrayList<Fornecedor> retorno = new ArrayList<>();
        //classe auxiliar para armazenar a sessão com o banco de dados
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //classe auxiliar para consultar o banco de dados
        Criteria criteria = sessao.createCriteria(Fornecedor.class);
        //adicionando a ordenação da pesquisa
        criteria.addOrder(Order.asc("idFornecedor"));
        //valorizando o objeto de retorno do método com os registros da tabela
        retorno = (ArrayList<Fornecedor>) criteria.list();
        //encerrando a conexão com o banco de dados
        sessao.close();
        //retornando a lista preenchida
        return retorno;
    }//fim do método buscarTodos
    
}//fim da classe
