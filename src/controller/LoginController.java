/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.LoginDAO;
import javax.swing.JOptionPane;
import org.eclipse.persistence.internal.oxm.mappings.Login;
import util.Mensagem;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class LoginController {

    LoginController() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void salvar(Login login) {
        try {
            new LoginDAO().salvar(login);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.login_erro, Mensagem.cadastro_login, 0);
        }
    }

    public void excluir(Login login) {
        try {
            new LoginDAO().excluir(login);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir pessoa", "Cadastro ", 0);
        }
    }

}
