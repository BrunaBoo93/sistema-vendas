/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.EstoqueView;
import view.MenuView;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class EstoqueController {

    private final EstoqueView estoqueView;

    //public EstoqueController() {
    //  }
    public EstoqueController(EstoqueView estoqueView) {
        this.estoqueView = estoqueView;
    }

    public void acaoBotaoNovo() {
        this.estoqueView.getBtNovo().setEnabled(false);
        this.estoqueView.getBtAlterar().setEnabled(false);
        this.estoqueView.getBtExcluir().setEnabled(false);
        this.estoqueView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.estoqueView.getBtSalvarEstoque().setEnabled(true);
        this.estoqueView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
        this.estoqueView.getCbxProdutoFornecedor().grabFocus();

    }

    public void acaoBotaoAlterar() {

    }

    public void acaoBotaoExcluir() {

    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);; 
        menuController.desbloquearMenu(menu);
        this.estoqueView.dispose();

    }

    public void acaoBotaoSalvar() {

    }

    public void acaoBotaoCancelar() {
        this.estoqueView.getBtNovo().setEnabled(true);
        this.estoqueView.getBtAlterar().setEnabled(true);
        this.estoqueView.getBtExcluir().setEnabled(true);
        this.estoqueView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.estoqueView.getBtSalvarEstoque().setEnabled(false);
        this.estoqueView.getBtCancelar().setEnabled(false);
        limparCampos();
        bloquearCampos();
    }

    public void bloqueioInicial() {
        this.estoqueView.getBtNovo().setEnabled(true);
        this.estoqueView.getBtAlterar().setEnabled(true);
        this.estoqueView.getBtExcluir().setEnabled(true);
        this.estoqueView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.estoqueView.getBtSalvarEstoque().setEnabled(false);
        this.estoqueView.getBtCancelar().setEnabled(false);
        bloquearCampos();
    }

    private void bloquearCampos() {
        this.estoqueView.getTfQuantidadeMinima().setEditable(false);
        this.estoqueView.getTfQuantidadeEstoque().setEditable(false);
        this.estoqueView.getCbxProdutoFornecedor().setEnabled(false);
    }

    private void desbloquearCampos() {
        this.estoqueView.getTfQuantidadeMinima().setEditable(true);
        this.estoqueView.getTfQuantidadeEstoque().setEditable(true);
        this.estoqueView.getCbxProdutoFornecedor().setEnabled(true);
    }

    private void limparCampos() {
        this.estoqueView.getTfQuantidadeMinima().setText(null);
        this.estoqueView.getTfQuantidadeEstoque().setText(null);
        this.estoqueView.getCbxProdutoFornecedor().setSelectedIndex(0);

    }

}
