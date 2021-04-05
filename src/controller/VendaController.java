package controller;

import view.ConfirmaVendaPrazoView;
import view.MenuView;
import view.VendaView;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class VendaController {

    private final VendaView vendaView;

    //public VendaController() {
    //  }
    public VendaController(VendaView vendaView) {
        this.vendaView = vendaView;
    }

    public void acaoBotaoIniciarVenda() {

        //habilitando os botões de controle
        this.vendaView.getBtIniciarVenda().setEnabled(true);
        this.vendaView.getBtSalvarProduto().setEnabled(true);
        this.vendaView.getBtExcluirProduto().setEnabled(true);
        this.vendaView.getBtIncluirFormaPagamento().setEnabled(true);
        this.vendaView.getBtExcluirFormaPagamento().setEnabled(true);
        this.vendaView.getBtConfirmar().setEnabled(true);
        this.vendaView.getBtSair().setEnabled(true);
        this.vendaView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
        this.vendaView.getCbxCliente().grabFocus();

    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);; 
        menuController.desbloquearMenu(menu);
        this.vendaView.dispose();

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
        new ConfirmaVendaPrazoView().setVisible(true);
    }

    public void acaoBotaoCancelar() {
        this.vendaView.getBtSair().setEnabled(true);
        this.vendaView.getBtConfirmar().setEnabled(true);
        //habilitando os botões de controle
        this.vendaView.getBtIniciarVenda().setEnabled(false);
        this.vendaView.getBtSalvarProduto().setEnabled(false);
        this.vendaView.getBtExcluirProduto().setEnabled(false);
        this.vendaView.getBtIncluirFormaPagamento().setEnabled(false);
        this.vendaView.getBtExcluirFormaPagamento().setEnabled(false);
        this.vendaView.getBtCancelar().setEnabled(false);
        desbloquearCampos();
        this.vendaView.getCbxCliente().grabFocus();
        limparCampos();
        bloquearCampos();
    }

    public void bloqueioInicial() {
        this.vendaView.getBtSair().setEnabled(true);
        this.vendaView.getBtConfirmar().setEnabled(true);
        //habilitando os botões de controle
        this.vendaView.getBtIniciarVenda().setEnabled(false);
        this.vendaView.getBtSalvarProduto().setEnabled(false);
        this.vendaView.getBtExcluirProduto().setEnabled(false);
        this.vendaView.getBtIncluirFormaPagamento().setEnabled(false);
        this.vendaView.getBtExcluirFormaPagamento().setEnabled(false);
        this.vendaView.getBtCancelar().setEnabled(false);
        bloquearCampos();
    }

    private void bloquearCampos() {
        this.vendaView.getTfDescontoPagamento().setEditable(false);
        this.vendaView.getTfDescontoProduto().setEditable(false);
        this.vendaView.getTfQuantidade().setEditable(false);
        this.vendaView.getCbxCliente().setEnabled(true);
        this.vendaView.getCbxFuncionario().setEnabled(true);
        this.vendaView.getCbxPagamento().setEnabled(false);
        this.vendaView.getCbxProduto().setEnabled(false);
    }

    private void desbloquearCampos() {
        this.vendaView.getTfDescontoPagamento().setEditable(true);
        this.vendaView.getTfDescontoProduto().setEditable(true);
        this.vendaView.getTfQuantidade().setEditable(true);
        this.vendaView.getCbxCliente().setEnabled(true);
        this.vendaView.getCbxFuncionario().setEnabled(true);
        this.vendaView.getCbxPagamento().setEnabled(true);
        this.vendaView.getCbxProduto().setEnabled(true);
    }

    private void limparCampos() {
        this.vendaView.getTfDescontoPagamento().setText(null);
        this.vendaView.getTfDescontoProduto().setText(null);
        this.vendaView.getTfQuantidade().setText(null);
        this.vendaView.getCbxCliente().setSelectedIndex(0);
        this.vendaView.getCbxFuncionario().setSelectedIndex(0);
        this.vendaView.getCbxPagamento().setSelectedIndex(0);
        this.vendaView.getCbxProduto().setSelectedIndex(0);

    }

}
