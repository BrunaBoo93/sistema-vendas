/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.ClienteView;
import view.CompraView;
import view.ContasPagarView;
import view.ContasReceberView;
import view.EstoqueView;
import view.FornecedorView;
import view.FuncionarioView;
import view.LoginView;
import view.MenuView;
import view.ProdutoView;
import view.SobreView;
import view.VendaView;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class MenuController {

    private final MenuView menuView;
    ArrayList<JFrame> dialogs = new ArrayList();

    //public ProdutoController() {
    //  }
    public MenuController(MenuView menuView) {
        this.menuView = menuView;
    }

    public void acaoFuncionarios() {
        bloquearMenu(menuView);
        JFrame funcionario = new FuncionarioView(menuView);
        this.openFrame(funcionario);
    }

    public void acaoCompras() {
        bloquearMenu(menuView);
        JFrame compras = new CompraView(menuView);
        this.openFrame(compras);
    }

    public void acaoSair() {
        int opcao = JOptionPane.showConfirmDialog(null, "Encerrar o sistema?", "Atenção", JOptionPane.YES_OPTION,
                JOptionPane.CANCEL_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void acaoClientes() {
        bloquearMenu(menuView);
        JFrame clientes = new ClienteView(menuView);
        this.openFrame(clientes);
    }

    public void acaoVendas() {
        bloquearMenu(menuView);
        JFrame vendas = new VendaView(menuView);
        this.openFrame(vendas);
    }

    public void acaoProdutos() {
        bloquearMenu(menuView);
        JFrame produtos = new ProdutoView(menuView);
        this.openFrame(produtos);
    }

    public void acaoFornecedores() {

        bloquearMenu(menuView);
        JFrame fornecedor = new FornecedorView(menuView);
        this.openFrame(fornecedor);
    }

    public void acaoJMenuEstoque() {
        bloquearMenu(menuView);
        JFrame estoque = new EstoqueView(menuView);
        this.openFrame(estoque);
    }

    public void acaoJMenuContasPagar() {
        bloquearMenu(menuView);
        JFrame contasPagar = new ContasPagarView(menuView);
        this.openFrame(contasPagar);
    }

    public void acaoJMenuContasReceber() {
        bloquearMenu(menuView);
        JFrame contasReceber = new ContasReceberView(menuView);
        this.openFrame(contasReceber);
    }

    public void acaoJMenuLogout() {

        //  SwingUtilities.windowForComponent(this.funcionarioView).dispose();
        //    this.funcionarioView.dispose();
        for (int i = 0; i < this.dialogs.size(); i++) {
            this.dialogs.get(i).dispose();
        }
        new LoginView().setVisible(true);
    }

    public void acaoJMenuSobre() {
        bloquearMenu(menuView);
        JFrame sobre = new SobreView(menuView);
        this.openFrame(sobre);
    }

    public void openFrame(JFrame frame) {
        frame.setVisible(true);
        this.dialogs.add(frame);
    }

    private void bloquearMenu(MenuView menu) {
        menu.getMenuCadastro().setEnabled(false);
        menu.getMenuCompras().setEnabled(false);
        menu.getMenuContas().setEnabled(false);
        menu.getMenuEstoque().setEnabled(false);
        menu.getMenuSistema().setEnabled(false);
        menu.getMenuVendas().setEnabled(false);

        menu.getBtFornecedor().setEnabled(false);
        menu.getBtClientes().setEnabled(false);
        menu.getBtCompras().setEnabled(false);
        menu.getBtFuncionarios().setEnabled(false);
        menu.getBtProdutos().setEnabled(false);
        menu.getBtSair().setEnabled(false);
        menu.getBtVendas().setEnabled(false);
    }

    public void desbloquearMenu(MenuView menu) {
        menu.getMenuCadastro().setEnabled(true);
        menu.getMenuCompras().setEnabled(true);
        menu.getMenuContas().setEnabled(true);
        menu.getMenuEstoque().setEnabled(true);
        menu.getMenuSistema().setEnabled(true);
        menu.getMenuVendas().setEnabled(true);

        menu.getBtFornecedor().setEnabled(true);
        menu.getBtClientes().setEnabled(true);
        menu.getBtCompras().setEnabled(true);
        menu.getBtFuncionarios().setEnabled(true);
        menu.getBtProdutos().setEnabled(true);
        menu.getBtSair().setEnabled(true);
        menu.getBtVendas().setEnabled(true);
    }
    
    public void carregarUsuario(){
       menuView.getLbUsuario().setText(LoginController.nomeFuncionario);
    }
    
}
