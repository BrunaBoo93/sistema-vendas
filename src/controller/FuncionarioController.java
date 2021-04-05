package controller;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Cidade;
import model.Estado;
import view.FuncionarioView;
import view.MenuView;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class FuncionarioController {

    private final FuncionarioView funcionarioView;
    private ArrayList<Estado> listaEstados;
    private ArrayList<Cidade> listaCidades;

   // public FuncionarioController() {
   // }
    
    public FuncionarioController(FuncionarioView funcionarioView) {
        this.funcionarioView = funcionarioView;
    }

    public void acaoBotaoNovo() {
        this.funcionarioView.getBtNovo().setEnabled(false);
        this.funcionarioView.getBtAlterar().setEnabled(false);
        this.funcionarioView.getBtExcluir().setEnabled(false);
        this.funcionarioView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.funcionarioView.getBtSalvarFuncionario().setEnabled(true);
        this.funcionarioView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
        this.funcionarioView.getTfCpf().grabFocus();

    }

    public void acaoBotaoAlterar() {

    }

    public void acaoBotaoExcluir() {

    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);; 
        menuController.desbloquearMenu(menu);
        this.funcionarioView.dispose();

    }

    public void acaoBotaoSalvar() {

    }

    public void acaoBotaoCancelar() {
        this.funcionarioView.getBtNovo().setEnabled(true);
        this.funcionarioView.getBtAlterar().setEnabled(true);
        this.funcionarioView.getBtExcluir().setEnabled(true);
        this.funcionarioView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.funcionarioView.getBtSalvarFuncionario().setEnabled(false);
        this.funcionarioView.getBtCancelar().setEnabled(false);
        limparCampos();
        bloquearCampos();
    }

    public void bloqueioInicial() {
        this.funcionarioView.getBtNovo().setEnabled(true);
        this.funcionarioView.getBtAlterar().setEnabled(true);
        this.funcionarioView.getBtExcluir().setEnabled(true);
        this.funcionarioView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.funcionarioView.getBtSalvarFuncionario().setEnabled(false);
        this.funcionarioView.getBtCancelar().setEnabled(false);
        bloquearCampos();
    }

    private void bloquearCampos() {
        this.funcionarioView.getTfCpf().setEditable(false);
        this.funcionarioView.getTfRg().setEditable(false);
        this.funcionarioView.getTfNome().setEditable(false);
        this.funcionarioView.getTfDataNascimento().setEditable(false);
        this.funcionarioView.getTfEndereco().setEditable(false);
        this.funcionarioView.getTfNumero().setEditable(false);
        this.funcionarioView.getTfBairro().setEditable(false);
        this.funcionarioView.getCbxUF().setEnabled(false);
        this.funcionarioView.getCbxCidade().setEnabled(false);
        this.funcionarioView.getTfTelefone().setEditable(false);
        this.funcionarioView.getTfCep().setEditable(false);
        this.funcionarioView.getTfCelular().setEditable(false);
        this.funcionarioView.getTfComplemento().setEditable(false);
        this.funcionarioView.getTfEmail().setEditable(false);
        this.funcionarioView.getTfLogin().setEditable(false);
        this.funcionarioView.getTfSenha().setEditable(false);
    }

    private void desbloquearCampos() {
        this.funcionarioView.getTfCpf().setEditable(true);
        this.funcionarioView.getTfRg().setEditable(true);
        this.funcionarioView.getTfNome().setEditable(true);
        this.funcionarioView.getTfDataNascimento().setEditable(true);
        this.funcionarioView.getTfEndereco().setEditable(true);
        this.funcionarioView.getTfNumero().setEditable(true);
        this.funcionarioView.getTfBairro().setEditable(true);
        this.funcionarioView.getCbxUF().setEnabled(true);
        this.funcionarioView.getTfTelefone().setEditable(true);
        this.funcionarioView.getTfCep().setEditable(true);
        this.funcionarioView.getTfCelular().setEditable(true);
        this.funcionarioView.getTfComplemento().setEditable(true);
        this.funcionarioView.getTfEmail().setEditable(true);
        this.funcionarioView.getTfLogin().setEditable(true);
        this.funcionarioView.getTfSenha().setEditable(true);
    }

    private void limparCampos() {
        this.funcionarioView.getTfCpf().setValue(null);
        this.funcionarioView.getTfRg().setValue(null);
        this.funcionarioView.getTfDataNascimento().setValue(null);
        this.funcionarioView.getTfNome().setText(null);
        this.funcionarioView.getTfEndereco().setText(null);
        this.funcionarioView.getTfCep().setValue(null);
        this.funcionarioView.getTfBairro().setText(null);
        this.funcionarioView.getTfComplemento().setText(null);
        this.funcionarioView.getCbxCidade().setSelectedIndex(0);
        this.funcionarioView.getCbxUF().setSelectedIndex(0);
        this.funcionarioView.getTfTelefone().setValue(null);
        this.funcionarioView.getTfCelular().setValue(null);
        this.funcionarioView.getTfEmail().setText(null);
        this.funcionarioView.getTfNumero().setText(null);
        this.funcionarioView.getTfLogin().setText(null);
        this.funcionarioView.getTfSenha().setText(null);

    }

    /*
     * método para carregar a combo de estado
     */
    public void carregarComboEstado() {
        listaEstados = new EstadoController().buscarTodos();
        this.funcionarioView.getCbxUF().addItem("- Selecionar Estado -");
        for (Estado estado : new EstadoController().buscarTodos()) {
            this.funcionarioView.getCbxUF().addItem(estado.getNome());
        }
    }

    /*
     * método para carregar a combo de cidade
     */
    public void carregarComboCidade() {
        int indice = this.funcionarioView.getCbxUF().getSelectedIndex() - 1;
        if (indice >= 0) {
            try {
                listaCidades = new CidadeController().buscarPorEstado(listaEstados.get(indice));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao consultar cidades");
                Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //removendo todos os dados da combo
            this.funcionarioView.getCbxCidade().removeAllItems();
            this.funcionarioView.getCbxCidade().addItem(" - Selecione Cidade -");

            for (Cidade cidade : listaCidades) {
                this.funcionarioView.getCbxCidade().addItem(cidade.getNome());

            }
            this.funcionarioView.getCbxCidade().setEnabled(true);

        }
    }
}
