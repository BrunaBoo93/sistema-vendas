package controller;

import dao.ProdutoDAO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Fornecedor;
import model.Produto;
import util.Mensagem;
import util.Util;
import util.Valida;
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
    private ArrayList<Produto> listaProdutos;
    private ArrayList<Fornecedor> listaFornecedores;

    private Produto produto;
    private Fornecedor fornecedor;

    private boolean alterar;

    public ProdutoController(ProdutoView produtoView) {
        this.produtoView = produtoView;
    }

    public void acaoBotaoNovo() {
        alterar = false;
        //this.produtoView.getBtNovo().setEnabled(false);
        //this.produtoView.getBtAlterar().setEnabled(false);
        //this.produtoView.getBtExcluir().setEnabled(false);
        //this.produtoView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.produtoView.getBtSalvarProduto().setEnabled(true);
        this.produtoView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
        this.produtoView.getTfDescricao().grabFocus();

    }

    public void acaoBotaoAlterar() {
        alterar = true;
        if (produtoView.getTabelaProdutosCadastrados().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(produtoView, Mensagem.selecione_produto, Mensagem.cadastro_produto, 0);
        } else {
            produto = listaProdutos.get(produtoView.getTabelaProdutosCadastrados().getSelectedRow());
            bloqueioAlterar();
            carregarTela();

        }
    }

    public void acaoBotaoExcluir() {
        if (produtoView.getTabelaProdutosCadastrados().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(produtoView, Mensagem.selecione_produto, Mensagem.cadastro_produto, 0);
        } else {
            int opcao = JOptionPane.showConfirmDialog(produtoView, Mensagem.excluir_produto, Mensagem.cadastro_produto, 2);
            if (opcao == JOptionPane.YES_OPTION) {
                produto = listaProdutos.get(produtoView.getTabelaProdutosCadastrados().getSelectedRow());

                new ProdutoDAO().excluir(produto);

                JOptionPane.showMessageDialog(produtoView, Mensagem.produto_excluido, Mensagem.cadastro_produto, 1);

                carregarTabela();
            }

        }
    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);;
        menuController.desbloquearMenu(menu);
        this.produtoView.dispose();

    }

    public void acaoBotaoSalvar() {
        if (validarDados()) {
            if (alterar) {
                fornecedor = produto.getFornecedorIdFornecedor();
            } else {
                //pra alterar utilizo objeto existente
                //para incluir preciso de um novo objeto
                produto = new Produto();
                
            }      
            
            fornecedor = listaFornecedores.get(this.produtoView.getCbxFornecedor().getSelectedIndex() - 1);
            produto.setDescricao(this.produtoView.getTfDescricao().getText());
            produto.setFornecedorIdFornecedor(fornecedor);
            produto.setValorVenda(Util.getDouble(this.produtoView.getTfValorVenda().getText()));
            produto.setValorCusto(Util.getDouble(this.produtoView.getTfValorCusto().getText()));

            try {
                new ProdutoDAO().salvar(produto);
                JOptionPane.showMessageDialog(null, Mensagem.produtoSalvo, Mensagem.cadastro_produto, 1);
                limparCampos();
                bloqueioInicial();
                carregarTabela();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
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

    /*
     * método para carregar a combo de estado
     */
    public void carregarComboFornecedor() {
        listaFornecedores = new FornecedorController().buscarTodos();
      //  this.produtoView.getCbxFornecedor().removeAllItems();
        this.produtoView.getCbxFornecedor().addItem("- Selecionar Fornecedor -");
        for (Fornecedor fornecedor : listaFornecedores) {
            this.produtoView.getCbxFornecedor().addItem(fornecedor.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial());
        }
    }


    /*
     * método para validar dados da inclusãio
     */
    private boolean validarDados() {

        //validando a descrição
        if (Valida.isEmptyOrNull(this.produtoView.getTfDescricao().getText())) {
            JOptionPane.showMessageDialog(produtoView, Mensagem.descricaoVazio, Mensagem.cadastro_produto, 0);
            this.produtoView.getTfDescricao().grabFocus();
            return false;
        }

        // validando a combo de fornecedor
        if (Valida.isComboInvalida(this.produtoView.getCbxFornecedor().getSelectedIndex())) {
            JOptionPane.showMessageDialog(produtoView, Mensagem.fornecedorVazio, Mensagem.cadastro_produto, 0);
            this.produtoView.getCbxFornecedor().grabFocus();
            return false;
        }

        // validando o valor de custo
        if (!Valida.isDouble(this.produtoView.getTfValorCusto().getText())) {
            JOptionPane.showMessageDialog(produtoView, Mensagem.valorCustoVazio, Mensagem.cadastro_produto, 0);
            this.produtoView.getTfValorCusto().grabFocus();
            return false;
        }

        // validando o valor de venda
        if (!Valida.isDouble(this.produtoView.getTfValorVenda().getText())) {
            JOptionPane.showMessageDialog(produtoView, Mensagem.valorVendaVazio, Mensagem.cadastro_produto, 0);
            this.produtoView.getTfValorVenda().grabFocus();
            return false;
        }

        return true;
    }

    /*
     * método responsável por chamar o DAO e arregador os fornecedores cadastrados 
     * no banco de dados
     */
    private ArrayList<Produto> buscarTodos() {
        try {
            return listaProdutos = new ProdutoDAO().buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(produtoView, Mensagem.produto_erro_consulta, Mensagem.cadastro_produto, 0);
        }
        return null;
    }

    /*
     *Método para carregar a JTable de fornecedor
     */
    public void carregarTabela() {
        buscarTodos();
        DefaultTableModel modelo = (DefaultTableModel) produtoView.getTabelaProdutosCadastrados().getModel(); //
        //limpar a tabla
        modelo.setRowCount(0);
        //carregar a tabela
        for (Produto produto : listaProdutos) {
            modelo.addRow(new String[]{
                produto.getDescricao(),
                produto.getFornecedorIdFornecedor().getPessoaJuridicaIdPessoaJuridica().getRazaoSocial(),
                produto.getValorCusto() + "",
                produto.getValorVenda() + ""});
        }

    }

    /*
     *método para carregar a tela com dados do fonewcedor
     */
    private void carregarTela() {
        produtoView.getTfDescricao().setText(produto.getDescricao());
        produtoView.getCbxFornecedor().setSelectedItem(produto.getFornecedorIdFornecedor().getPessoaJuridicaIdPessoaJuridica().getRazaoSocial());
        produtoView.getTfValorCusto().setText(produto.getValorCusto() + "");
        produtoView.getTfValorVenda().setText(produto.getValorVenda() + "");

    }

    /*
     * método para bloquear os cmapos na ação do alterar
     */
    private void bloqueioAlterar() {
        this.produtoView.getBtNovo().setEnabled(false);
        this.produtoView.getBtAlterar().setEnabled(false);
        this.produtoView.getBtExcluir().setEnabled(false);
        this.produtoView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.produtoView.getBtSalvarProduto().setEnabled(true);
        this.produtoView.getBtCancelar().setEnabled(true);

        this.produtoView.getTfDescricao().setEditable(true);
        this.produtoView.getCbxFornecedor().setEditable(true);
        this.produtoView.getTfValorCusto().setEditable(true);
        this.produtoView.getTfValorVenda().setEditable(true);

    }
}
