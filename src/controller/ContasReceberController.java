/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.ContasReceberView;
import view.MenuView;

/**
 *
 * @author boriani
 */
public class ContasReceberController {

    private final ContasReceberView contasReceberView;

    //public ContasPagarController() {
    //  }
    public ContasReceberController(ContasReceberView contasReceberView) {
        this.contasReceberView = contasReceberView;
    }

    public void bloqueioInicial() {
        this.contasReceberView.getBtSair().setEnabled(true);
        this.contasReceberView.getBtAlterar().setEnabled(true);
        //habilitando os botões de controle
        this.contasReceberView.getBtSalvar().setEnabled(false);
        this.contasReceberView.getBtCancelar().setEnabled(false);
        bloquearCampos();
    }

    private void bloquearCampos() {
        this.contasReceberView.getTfDataPagamento().setEditable(false);
        this.contasReceberView.getTfDataVencimento().setEditable(false);
        this.contasReceberView.getRbNaoPago().setEnabled(false);
        this.contasReceberView.getRbPago().setEnabled(false);
    }

    private void desbloquearCampos() {
        this.contasReceberView.getTfDataPagamento().setEditable(true);
        this.contasReceberView.getTfDataVencimento().setEditable(true);
        this.contasReceberView.getRbNaoPago().setEnabled(true);
        this.contasReceberView.getRbPago().setEnabled(true);
    }

    public void acaoBotaoAlterar() {
        this.contasReceberView.getBtSair().setEnabled(true);
        this.contasReceberView.getBtAlterar().setEnabled(true);
        //habilitando os botões de controle
        this.contasReceberView.getBtSalvar().setEnabled(true);
        this.contasReceberView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);;
        menuController.desbloquearMenu(menu);
        this.contasReceberView.dispose();

    }

    public void acaoBotaoCancelar() {
        limparCampos();
        this.contasReceberView.getBtSair().setEnabled(true);
        this.contasReceberView.getBtAlterar().setEnabled(true);
        //habilitando os botões de controle
        this.contasReceberView.getBtSalvar().setEnabled(false);
        this.contasReceberView.getBtCancelar().setEnabled(false);
        bloquearCampos();

    }

    public void acaoBotaoSalvar() {

    }

    private void limparCampos() {
        this.contasReceberView.getTfDataPagamento().setText(null);
        this.contasReceberView.getTfDataVencimento().setText(null);
        this.contasReceberView.getGrpPagamento().clearSelection();

    }

}
