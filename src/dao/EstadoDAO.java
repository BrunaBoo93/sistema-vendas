/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.Estado;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import util.HibernateUtil;

/**
 *
 * @author boriani
 */
public class EstadoDAO extends GenericDAO {
            /*
    * método para consultar os estados gravados na tabela
    */
    public ArrayList<Estado> buscarTodos() throws Exception{
        //lista auxiliar para retornar no método
        ArrayList<Estado> retorno = new ArrayList<>();
        //classe auxiliar para armazenar a sessão com o banco de dados
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //classe auxiliar para consultar o banco de dados
        Criteria criteria = sessao.createCriteria(Estado.class);
        //adicionando a ordenação da pesquisa
        criteria.addOrder(Order.asc("idEstado"));
        //valorizando o objeto de retorno do método com os registros da tabela
        retorno = (ArrayList<Estado>) criteria.list();
        //encerrando a conexão com o banco de dados
        sessao.close();
        //retornando a lista preenchida
        return retorno;
    }//fim do método buscarTodos
    
}
