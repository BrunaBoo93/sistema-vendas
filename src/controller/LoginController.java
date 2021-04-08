/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.JOptionPane;
import model.Funcionario;
import util.Mensagem;
import util.Valida;
import view.LoginView;
import view.MenuView;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class LoginController {

    private LoginView loginView;
    public static String nomeFuncionario;

    public LoginController() {

    }

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
    }

    public void acaoBotaoConfirmar() {
        if (validarDados()) {
            efetuarLogin();
        }
    }

    public void acaoBotaoCancelar() {
        System.exit(0);
    }
    
    private boolean validarDados(){
        if (Valida.isEmptyOrNull(loginView.getTfLogin().getText())) {
            JOptionPane.showMessageDialog(null, "Informe o login, campo obrigatório!");
            loginView.getTfLogin().grabFocus();
            return false;
        }
        
        if (Valida.isEmptyOrNull(loginView.getTfSenha().getText())) {
            JOptionPane.showMessageDialog(null, "Informe a senha, campo obrigatório!");
            loginView.getTfSenha().grabFocus();
            return false;
        }
        
        return true;
    }
    
    private void efetuarLogin(){
        String login = loginView.getTfLogin().getText();
        String senha = loginView.getTfSenha().getText();
        
        boolean achouLogin = false;
        boolean achouSenha = false;
        
        for (Funcionario funcionario : new FuncionarioController().buscarPorLogin(login)) {
            achouLogin = true;
            if (funcionario.getSenha().equals(senha)) {
                nomeFuncionario = funcionario.getPessoaFisicaIdPessoaFisica().getNome();
                loginView.dispose();
                new MenuView().setVisible(true);
                achouSenha = true;
                break;
            }else{
                achouSenha = false;
            }
        }
        
        if (achouLogin == false || achouSenha == false) {
            JOptionPane.showMessageDialog(null, Mensagem.credencial_invalida);
        }
    }
}
