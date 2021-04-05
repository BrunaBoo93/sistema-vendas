/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.Endereco;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import util.HibernateUtil;

/**
 *
 * @author boriani
 */
public class EnderecoDAO extends GenericDAO{
        /*
    * método para consultar os endereços gravados na tabela
    */
    public ArrayList<Endereco> buscarTodos() throws Exception{
        //lista auxiliar para retornar no método
        ArrayList<Endereco> retorno = new ArrayList<>();
        //classe auxiliar para armazenar a sessão com o banco de dados
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        //classe auxiliar para consultar o banco de dados
        Criteria criteria = sessao.createCriteria(Endereco.class);
        //adicionando a ordenação da pesquisa
        criteria.addOrder(Order.asc("idEndereco"));
        //valorizando o objeto de retorno do método com os registros da tabela
        retorno = (ArrayList<Endereco>) criteria.list();
        //encerrando a conexão com o banco de dados
        sessao.close();
        //retornando a lista preenchida
        return retorno;
    }//fim do método buscarTodos
}
