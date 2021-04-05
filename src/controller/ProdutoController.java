package controller;

import view.MenuView;
import view.ProdutoView;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class ProdutoController {

    private final ProdutoView produtoView;

    //public ProdutoController() {
    //  }
    public ProdutoController(ProdutoView produtoView) {
        this.produtoView = produtoView;
    }

    public void acaoBotaoNovo() {
        this.produtoView.getBtNovo().setEnabled(false);
        this.produtoView.getBtAlterar().setEnabled(false);
        this.produtoView.getBtExcluir().setEnabled(false);
        this.produtoView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.produtoView.getBtSalvarProduto().setEnabled(true);
        this.produtoView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
        this.produtoView.getTfDescricao().grabFocus();

    }

    public void acaoBotaoAlterar() {

    }

    public void acaoBotaoExcluir() {

    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);; 
        menuController.desbloquearMenu(menu);
        this.produtoView.dispose();

    }

    public void acaoBotaoSalvar() {

    }

    public void acaoBotaoCancelar() {
        this.produtoView.getBtNovo().setEnabled(true);
        this.produtoView.getBtAlterar().setEnabled(true);
        this.produtoView.getBtExcluir().setEnabled(true);
        this.produtoView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.produtoView.getBtSalvarProduto().setEnabled(false);
        this.produtoView.getBtCancelar().setEnabled(false);
        limparCampos();
        bloquearCampos();
    }

    public void bloqueioInicial() {
        this.produtoView.getBtNovo().setEnabled(true);
        this.produtoView.getBtAlterar().setEnabled(true);
        this.produtoView.getBtExcluir().setEnabled(true);
        this.produtoView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.produtoView.getBtSalvarProduto().setEnabled(false);
        this.produtoView.getBtCancelar().setEnabled(false);
        bloquearCampos();
    }

    private void bloquearCampos() {
        this.produtoView.getTfDescricao().setEditable(false);
        this.produtoView.getTfValorCusto().setEditable(false);
        this.produtoView.getTfValorVenda().setEditable(false);
        this.produtoView.getCbxFornecedor().setEnabled(false);
    }

    private void desbloquearCampos() {
        this.produtoView.getTfDescricao().setEditable(true);
        this.produtoView.getTfValorCusto().setEditable(true);
        this.produtoView.getTfValorVenda().setEditable(true);
        this.produtoView.getCbxFornecedor().setEnabled(true);
    }

    private void limparCampos() {
        this.produtoView.getTfDescricao().setText(null);
        this.produtoView.getTfValorCusto().setText(null);
        this.produtoView.getTfValorVenda().setText(null);
        this.produtoView.getCbxFornecedor().setSelectedIndex(0);

    }

}
