package controller;

import view.CompraView;
import view.ConfirmaCompraPrazoView;
import view.MenuView;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class CompraController {

    private final CompraView compraView;

    //public CompraController() {
    //  }
    public CompraController(CompraView compraView) {
        this.compraView = compraView;
    }

    public void acaoBotaoIniciarCompra() {

        //habilitando os botões de controle
        this.compraView.getBtIniciarCompra().setEnabled(true);
        this.compraView.getBtSalvarProduto().setEnabled(true);
        this.compraView.getBtExcluirProduto().setEnabled(true);
        this.compraView.getBtIncluirFormaPagamento().setEnabled(true);
        this.compraView.getBtExcluirFormaPagamento().setEnabled(true);
        this.compraView.getBtConfirmar().setEnabled(true);
        this.compraView.getBtSair().setEnabled(true);
        this.compraView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
        this.compraView.getCbxFuncionario().grabFocus();

    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);;
        menuController.desbloquearMenu(menu);
        this.compraView.dispose();

    }

    public void acaoBotaoSalvar() {

    }

    public void acaoBotaoIncluir() {

    }

    public void acaoBotaoExcluirProduto() {

    }

    public void acaoBotaoExcluirFormaPagamento() {

    }

    public void acaoBotaoConfirmar() {
        new ConfirmaCompraPrazoView().setVisible(true);
    }

    public void acaoBotaoCancelar() {
        this.compraView.getBtSair().setEnabled(true);
        this.compraView.getBtConfirmar().setEnabled(true);
        this.compraView.getBtIniciarCompra().setEnabled(false);
        this.compraView.getBtSalvarProduto().setEnabled(false);
        this.compraView.getBtExcluirProduto().setEnabled(false);
        this.compraView.getBtIncluirFormaPagamento().setEnabled(false);
        this.compraView.getBtExcluirFormaPagamento().setEnabled(false);
        this.compraView.getBtCancelar().setEnabled(false);
        desbloquearCampos();
        this.compraView.getCbxFuncionario().grabFocus();
        limparCampos();
        bloquearCampos();
    }

    public void bloqueioInicial() {
        this.compraView.getBtSair().setEnabled(true);
        this.compraView.getBtConfirmar().setEnabled(true);
        //habilitando os botões de controle
        this.compraView.getBtIniciarCompra().setEnabled(false);
        this.compraView.getBtSalvarProduto().setEnabled(false);
        this.compraView.getBtExcluirProduto().setEnabled(false);
        this.compraView.getBtIncluirFormaPagamento().setEnabled(false);
        this.compraView.getBtExcluirFormaPagamento().setEnabled(false);
        this.compraView.getBtCancelar().setEnabled(false);
        bloquearCampos();
    }

    private void bloquearCampos() {
        this.compraView.getTfQuantidade().setEditable(false);
        this.compraView.getCbxFornecedor().setEnabled(true);
        this.compraView.getCbxFuncionario().setEnabled(true);
        this.compraView.getCbxPagamento().setEnabled(false);
        this.compraView.getCbxProduto().setEnabled(false);
    }

    private void desbloquearCampos() {

        this.compraView.getTfQuantidade().setEditable(true);
        this.compraView.getCbxFornecedor().setEnabled(true);
        this.compraView.getCbxFuncionario().setEnabled(true);
        this.compraView.getCbxPagamento().setEnabled(true);
        this.compraView.getCbxProduto().setEnabled(true);
    }

    private void limparCampos() {
        this.compraView.getTfQuantidade().setText(null);
        this.compraView.getCbxFornecedor().setSelectedIndex(0);
        this.compraView.getCbxFuncionario().setSelectedIndex(0);
        this.compraView.getCbxPagamento().setSelectedIndex(0);
        this.compraView.getCbxProduto().setSelectedIndex(0);

    }

}
