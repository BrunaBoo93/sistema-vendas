/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.ContasPagarView;
import view.MenuView;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class ContasPagarController {

    private final ContasPagarView contasPagarView;

    //public ContasPagarController() {
    //  }
    public ContasPagarController(ContasPagarView contasPagarView) {
        this.contasPagarView = contasPagarView;
    }

    public void bloqueioInicial() {
        this.contasPagarView.getBtSair().setEnabled(true);
        this.contasPagarView.getBtAlterar().setEnabled(true);
        //habilitando os botões de controle
        this.contasPagarView.getBtSalvar().setEnabled(false);
        this.contasPagarView.getBtCancelar().setEnabled(false);
        bloquearCampos();
    }

    private void bloquearCampos() {
        this.contasPagarView.getTfDataPagamento().setEditable(false);
        this.contasPagarView.getTfDataVencimento().setEditable(false);
        this.contasPagarView.getRbNaoPago().setEnabled(false);
        this.contasPagarView.getRbPago().setEnabled(false);
    }

    private void desbloquearCampos() {
        this.contasPagarView.getTfDataPagamento().setEditable(true);
        this.contasPagarView.getTfDataVencimento().setEditable(true);
        this.contasPagarView.getRbNaoPago().setEnabled(true);
        this.contasPagarView.getRbPago().setEnabled(true);
    }

    public void acaoBotaoAlterar() {
        this.contasPagarView.getBtSair().setEnabled(true);
        this.contasPagarView.getBtAlterar().setEnabled(true);
        //habilitando os botões de controle
        this.contasPagarView.getBtSalvar().setEnabled(true);
        this.contasPagarView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);;
        menuController.desbloquearMenu(menu);
        this.contasPagarView.dispose();

    }

    public void acaoBotaoCancelar() {
        limparCampos();
        this.contasPagarView.getBtSair().setEnabled(true);
        this.contasPagarView.getBtAlterar().setEnabled(true);
        //habilitando os botões de controle
        this.contasPagarView.getBtSalvar().setEnabled(false);
        this.contasPagarView.getBtCancelar().setEnabled(false);
        bloquearCampos();

    }

    public void acaoBotaoSalvar() {

    }

    private void limparCampos() {
        this.contasPagarView.getTfDataPagamento().setText(null);
        this.contasPagarView.getTfDataVencimento().setText(null);
        this.contasPagarView.getGrpPagamento().clearSelection();

    }

}
