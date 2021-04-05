/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.Contato;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import util.HibernateUtil;

/**
 *
 * @author boriani
 */
public class ContatoDAO extends GenericDAO {

    /*
     * método para consultar os contatos gravados na tabela
     */
    public ArrayList<Contato> buscarTodos() throws Exception {
        //lista auxiliar para retornar no método
        ArrayList<Contato> retorno = new ArrayList<>();
        //classe auxiliar para armazenar a sessão com o banco de dados
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //classe auxiliar para consultar o banco de dados
        Criteria criteria = sessao.createCriteria(Contato.class);
        //adicionando a ordenação da pesquisa
        criteria.addOrder(Order.asc("idContato"));
        //valorizando o objeto de retorno do método com os registros da tabela
        retorno = (ArrayList<Contato>) criteria.list();
        //encerrando a conexão com o banco de dados
        sessao.close();
        //retornando a lista preenchida
        return retorno;
    }//fim do método buscarTodos
}
