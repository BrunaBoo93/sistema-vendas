package controller;

import dao.FornecedorDAO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cidade;
import model.Contato;
import model.Endereco;
import model.Estado;
import model.Fornecedor;
import model.PessoaJuridica;
import util.Mensagem;
import util.Util;
import util.Valida;
import view.FornecedorView;
import view.MenuView;

/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 *
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class FornecedorController {

    private FornecedorView fornecedorView;
    private ArrayList<Estado> listaEstados;
    private ArrayList<Cidade> listaCidades;
    private ArrayList<Fornecedor> listaFornecedores;

    private Fornecedor fornecedor;
    private PessoaJuridica pessoa;
    private Endereco endereco;
    private Contato contato;

    private boolean alterar;

    public FornecedorController(FornecedorView fornecedorView) {
        this.fornecedorView = fornecedorView;
    }

    public FornecedorController() {
        
    }

    

    public void acaoBotaoNovo() {
        alterar = false;
        // this.fornecedorView.getBtNovo().setEnabled(false);
        // this.fornecedorView.getBtAlterar().setEnabled(false);
        //  this.fornecedorView.getBtExcluir().setEnabled(false);
        //  this.fornecedorView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.fornecedorView.getBtSalvarFornecedor().setEnabled(true);
        this.fornecedorView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
        this.fornecedorView.getTfCnpj().grabFocus();

    }

    public void acaoBotaoAlterar() {
        alterar = true;
        if (fornecedorView.getTabelaFornecedores().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.selecione_fornecedor, Mensagem.cadastro_fornecedor, 0);
        } else {
            fornecedor = listaFornecedores.get(fornecedorView.getTabelaFornecedores().getSelectedRow());
            bloqueioAlterar();
            carregarTela();

        }

    }

    public void acaoBotaoExcluir() {
        if (fornecedorView.getTabelaFornecedores().getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.selecione_fornecedor, Mensagem.cadastro_fornecedor, 0);
        } else {
            int opcao = JOptionPane.showConfirmDialog(fornecedorView, Mensagem.excluir_fornecedor, Mensagem.cadastro_fornecedor, 2);
            if (opcao == JOptionPane.YES_OPTION) {
                fornecedor = listaFornecedores.get(fornecedorView.getTabelaFornecedores().getSelectedRow());

                new FornecedorDAO().excluir(fornecedor);

                new ContatoController().excluir(fornecedor.getContatoIdContato());
                new EnderecoController().excluir(fornecedor.getEnderecoIdEndereco());
                new PessoaJuridicaController().excluir(fornecedor.getPessoaJuridicaIdPessoaJuridica());

                JOptionPane.showMessageDialog(fornecedorView, "Fornecedor excluido com sucesso", Mensagem.cadastro_fornecedor, 1);

                carregarTabela();
            }

        }
    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);;
        menuController.desbloquearMenu(menu);
        this.fornecedorView.dispose();

    }

    public void acaoBotaoSalvar() { //vaerificar esse meotdo aula 2
        if (validarDados()) {
            if (alterar) {
                pessoa = fornecedor.getPessoaJuridicaIdPessoaJuridica();
                endereco = fornecedor.getEnderecoIdEndereco();
                contato = fornecedor.getContatoIdContato();

            } else {

                //pra alterar utilizo objeto existente
                //para incluir preciso de um novo objeto
                fornecedor = new Fornecedor();
                pessoa = new PessoaJuridica();
                endereco = new Endereco();
                contato = new Contato();
            }
            pessoa = getPessoaJuridica();
            endereco = getEndereco();
            contato = getContato();

            new PessoaJuridicaController().salvar(pessoa);
            new EnderecoController().salvar(endereco);
            new ContatoController().salvar(contato);

            fornecedor.setContato(this.fornecedorView.getTfContato().getText());
            fornecedor.setPessoaJuridicaIdPessoaJuridica(pessoa);
            fornecedor.setEnderecoIdEndereco(endereco);
            fornecedor.setContatoIdContato(contato);

            try {
                new FornecedorDAO().salvar(fornecedor);
                JOptionPane.showMessageDialog(null, Mensagem.fornecedorSalvo, Mensagem.cadastro_fornecedor, 1);
                limparCampos();
                bloqueioInicial();
                carregarTabela();
            } catch (Exception e) {
                e.printStackTrace();
                   JOptionPane.showMessageDialog(fornecedorView, Mensagem.fornecedor_erro, Mensagem.cadastro_fornecedor, 0);
            }

        }
    }

    public void acaoBotaoCancelar() {
        this.fornecedorView.getBtNovo().setEnabled(true);
        this.fornecedorView.getBtAlterar().setEnabled(true);
        this.fornecedorView.getBtExcluir().setEnabled(true);
        this.fornecedorView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.fornecedorView.getBtSalvarFornecedor().setEnabled(false);
        this.fornecedorView.getBtCancelar().setEnabled(false);
        limparCampos();
        bloquearCampos();
    }

    public void bloqueioInicial() {
        this.fornecedorView.getBtNovo().setEnabled(true);
        this.fornecedorView.getBtAlterar().setEnabled(true);
        this.fornecedorView.getBtExcluir().setEnabled(true);
        this.fornecedorView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.fornecedorView.getBtSalvarFornecedor().setEnabled(false);
        this.fornecedorView.getBtCancelar().setEnabled(false);
        bloquearCampos();
    }

    private void bloquearCampos() {
        this.fornecedorView.getTfCnpj().setEditable(false);
        this.fornecedorView.getTfInscrEstadual().setEditable(false);
        this.fornecedorView.getTfDataFundacao().setEditable(false);
        this.fornecedorView.getTfRazaoSocial().setEditable(false);
        this.fornecedorView.getTfContato().setEditable(false);
        this.fornecedorView.getTfEndereco().setEditable(false);
        this.fornecedorView.getTfNumero().setEditable(false);
        this.fornecedorView.getTfBairro().setEditable(false);
        this.fornecedorView.getCbxUF().setEnabled(false);
        this.fornecedorView.getCbxCidade().setEnabled(false);
        this.fornecedorView.getTfTelefone().setEditable(false);
        this.fornecedorView.getTfCep().setEditable(false);
        this.fornecedorView.getTfCelular().setEditable(false);
        this.fornecedorView.getTfComplemento().setEditable(false);
        this.fornecedorView.getTfEmail().setEditable(false);
    }

    private void desbloquearCampos() {
        this.fornecedorView.getTfCnpj().setEditable(true);
        this.fornecedorView.getTfInscrEstadual().setEditable(true);
        this.fornecedorView.getTfDataFundacao().setEditable(true);
        this.fornecedorView.getTfRazaoSocial().setEditable(true);
        this.fornecedorView.getTfContato().setEditable(true);
        this.fornecedorView.getTfEndereco().setEditable(true);
        this.fornecedorView.getTfNumero().setEditable(true);
        this.fornecedorView.getTfBairro().setEditable(true);
        this.fornecedorView.getCbxUF().setEnabled(true);
        this.fornecedorView.getTfTelefone().setEditable(true);
        this.fornecedorView.getTfCep().setEditable(true);
        this.fornecedorView.getTfCelular().setEditable(true);
        this.fornecedorView.getTfComplemento().setEditable(true);
        this.fornecedorView.getTfEmail().setEditable(true);

    }

    private void limparCampos() {
        this.fornecedorView.getTfCnpj().setValue(null);
        this.fornecedorView.getTfInscrEstadual().setValue(null);
        this.fornecedorView.getTfDataFundacao().setValue(null);
        this.fornecedorView.getTfRazaoSocial().setText(null);
        this.fornecedorView.getTfEndereco().setText(null);
        this.fornecedorView.getTfCep().setValue(null);
        this.fornecedorView.getTfBairro().setText(null);
        this.fornecedorView.getTfComplemento().setText(null);
        this.fornecedorView.getCbxCidade().setSelectedIndex(0);
        this.fornecedorView.getCbxUF().setSelectedIndex(0);
        this.fornecedorView.getTfTelefone().setValue(null);
        this.fornecedorView.getTfCelular().setValue(null);
        this.fornecedorView.getTfContato().setText(null);
        this.fornecedorView.getTfEmail().setText(null);
        this.fornecedorView.getTfNumero().setText(null);

    }

    /*
     * método para carregar a combo de estado
     */
    public void carregarComboEstado() {
        listaEstados = new EstadoController().buscarTodos();
        this.fornecedorView.getCbxUF().removeAllItems();
        this.fornecedorView.getCbxUF().addItem("- Selecionar Estado -");
        for (Estado estado : listaEstados) {
            this.fornecedorView.getCbxUF().addItem(estado.getNome());
        }
    }

    /*
     * método para carregar a combo de cidade
     */
    public void carregarComboCidade() {
        int indice = this.fornecedorView.getCbxUF().getSelectedIndex() - 1;
        if (indice >= 0) {
            try {
                listaCidades = new CidadeController().buscarPorEstado(listaEstados.get(indice));

            } catch (Exception ex) {
                Logger.getLogger(FornecedorController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            //removendo todos os dados da combo
            this.fornecedorView.getCbxCidade().removeAllItems();
            this.fornecedorView.getCbxCidade().addItem(" - Selecione Cidade -");

            for (Cidade cidade : listaCidades) {
                this.fornecedorView.getCbxCidade().addItem(cidade.getNome());

            }
            this.fornecedorView.getCbxCidade().setEnabled(true);

        }
    }

    /*
     * método para validar dados da inclusãio
     */
    private boolean validarDados() {

        //validando o CNPJ
        if (Valida.isCnpjVazio(this.fornecedorView.getTfCnpj().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.cnpjVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfCnpj().grabFocus();
            return false;
        } else if (Valida.isCnpjInvalido(this.fornecedorView.getTfCnpj().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.cnpjInvalido, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfCnpj().grabFocus();
            return false;
        }

        //validando a INSCRIÇAO ESTADUAL
        if (Valida.isInscricaoEstadualVazio(this.fornecedorView.getTfInscrEstadual().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.inscricaoEstadualVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfInscrEstadual().grabFocus();
            return false;
        } else if (Valida.isInscricaoEstadualInvalido(this.fornecedorView.getTfInscrEstadual().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.inscricaoEstadualInvalida, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfInscrEstadual().grabFocus();
            return false;
        }

        //validando a razao social
        if (Valida.isEmptyOrNull(this.fornecedorView.getTfRazaoSocial().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.razaoSocialVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfRazaoSocial().grabFocus();
            return false;
        }

        //validando a data de fundação
        if (Valida.isDataVazio(this.fornecedorView.getTfDataFundacao().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.dataFundacaoVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfDataFundacao().grabFocus();
            return false;
        } else if (Valida.isDataVazio(this.fornecedorView.getTfDataFundacao().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.dataInvalida, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfDataFundacao().grabFocus();
            return false;
        }

        // validando o endereço
        if (Valida.isEmptyOrNull(this.fornecedorView.getTfEndereco().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.enderecoVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfEndereco().grabFocus();
            return false;
        }

        // validando o numero
        if (!Valida.isInteger(this.fornecedorView.getTfNumero().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.numeroVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfNumero().grabFocus();
            return false;
        }

        // validando o bairro
        if (Valida.isEmptyOrNull(this.fornecedorView.getTfBairro().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.bairroVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfBairro().grabFocus();
            return false;
        }

        // validando o estado
        if (Valida.isComboInvalida(this.fornecedorView.getCbxUF().getSelectedIndex())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.estadoVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getCbxUF().grabFocus();
            return false;
        }

        // validando a cidade
        if (Valida.isComboInvalida(this.fornecedorView.getCbxCidade().getSelectedIndex())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.cidadeVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getCbxCidade().grabFocus();
            return false;
        }

        // validando o cep
        if (Valida.isCepVazio(this.fornecedorView.getTfCep().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.cepVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfCep().grabFocus();
            return false;
        }

        // validando o celular
        if (Valida.isCelularVazio(this.fornecedorView.getTfCelular().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.celularVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfCelular().grabFocus();
            return false;
        }

        // validando o email
        if (Valida.isEmptyOrNull(this.fornecedorView.getTfEmail().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.emailVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfEmail().grabFocus();
            return false;
        }

        // validando o contato
        if (Valida.isEmptyOrNull(this.fornecedorView.getTfBairro().getText())) {
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.bairroVazio, Mensagem.cadastro_fornecedor, 0);
            this.fornecedorView.getTfBairro().grabFocus();
            return false;
        }

        return true;
    }

    /*
     *método para retornar um novo objeto
     */
    private PessoaJuridica getPessoaJuridica() {

        pessoa.setCnpj(this.fornecedorView.getTfCnpj().getText());
        pessoa.setInscricaoEstadual(this.fornecedorView.getTfInscrEstadual().getText());
        pessoa.setRazaoSocial(this.fornecedorView.getTfRazaoSocial().getText());
        pessoa.setDataFundacao(this.fornecedorView.getTfDataFundacao().getText());
        return pessoa;
    }

    /*
     *método para retornar um novo objeto
     */
    private Endereco getEndereco() {

        endereco.setNome(this.fornecedorView.getTfEndereco().getText());
        endereco.setNumero(Util.getInteger(this.fornecedorView.getTfNumero().getText()));
        endereco.setComplemento(this.fornecedorView.getTfComplemento().getText());
        endereco.setBairro(this.fornecedorView.getTfBairro().getText());
        endereco.setCep(this.fornecedorView.getTfCep().getText());
        endereco.setCidadeIdCidade(listaCidades.get(this.fornecedorView.getCbxCidade().getSelectedIndex() - 1));
        return endereco;
    }

    /*
     *método para retornar um novo objeto
     */
    private Contato getContato() {

        contato.setTelefone(this.fornecedorView.getTfTelefone().getText());
        contato.setCelular(this.fornecedorView.getTfCelular().getText());
        contato.setEmail(this.fornecedorView.getTfEmail().getText());
        return contato;
    }

    /*
     * método responsável por chamar o DAO e arregador os fornecedores cadastrados 
     * no banco de dados
     */
    public ArrayList<Fornecedor> buscarTodos() {
        try {
            return listaFornecedores = new FornecedorDAO().buscarTodos();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(fornecedorView, Mensagem.fornecedor_erro_consulta, Mensagem.cadastro_fornecedor, 0);
        }
        return null;
    }

    /*
     *Método para carregar a JTable de fornecedor
     */
    public void carregarTabela() {
        buscarTodos();
        DefaultTableModel modelo = (DefaultTableModel) fornecedorView.getTabelaFornecedores().getModel(); //
        //limpar a tabla
        modelo.setRowCount(0);
        //carregar a tabela
        for (Fornecedor fornecedor : listaFornecedores) {
            modelo.addRow(new String[]{
                fornecedor.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial(),
                fornecedor.getContatoIdContato().getTelefone(),
                fornecedor.getContato(),
                fornecedor.getEnderecoIdEndereco().getCidadeIdCidade().getNome()});
        }

    }

    /*
     *método para carregar a tela com dados do fonewcedor
     */
    private void carregarTela() {
        fornecedorView.getTfCnpj().setText(fornecedor.getPessoaJuridicaIdPessoaJuridica().getCnpj());
        fornecedorView.getTfInscrEstadual().setText(fornecedor.getPessoaJuridicaIdPessoaJuridica().getInscricaoEstadual());
        fornecedorView.getTfRazaoSocial().setText(fornecedor.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial());
        fornecedorView.getTfDataFundacao().setText(fornecedor.getPessoaJuridicaIdPessoaJuridica().getDataFundacao());
        fornecedorView.getTfEndereco().setText(fornecedor.getEnderecoIdEndereco().getNome());
        fornecedorView.getTfNumero().setText(fornecedor.getEnderecoIdEndereco().getNumero() + "");
        fornecedorView.getTfComplemento().setText(fornecedor.getEnderecoIdEndereco().getComplemento());
        fornecedorView.getTfBairro().setText(fornecedor.getEnderecoIdEndereco().getBairro());

        //hierarquia de banco
        fornecedorView.getCbxUF().setSelectedItem(fornecedor.getEnderecoIdEndereco().getCidadeIdCidade().getEstadoIdEstado().getNome());
        fornecedorView.getCbxCidade().setSelectedItem(fornecedor.getEnderecoIdEndereco().getCidadeIdCidade().getNome());
        fornecedorView.getTfCep().setText((fornecedor.getEnderecoIdEndereco().getCep()));
        fornecedorView.getTfTelefone().setText(fornecedor.getContatoIdContato().getTelefone());
        fornecedorView.getTfCelular().setText(fornecedor.getContatoIdContato().getCelular());
        fornecedorView.getTfEmail().setText(fornecedor.getContatoIdContato().getEmail());
        fornecedorView.getTfContato().setText(fornecedor.getContato());

    }

    /*
     * método para bloquear os cmapos na ação do alterar
     */
    private void bloqueioAlterar() {
        this.fornecedorView.getBtNovo().setEnabled(false);
        this.fornecedorView.getBtAlterar().setEnabled(false);
        this.fornecedorView.getBtExcluir().setEnabled(false);
        this.fornecedorView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.fornecedorView.getBtSalvarFornecedor().setEnabled(true);
        this.fornecedorView.getBtCancelar().setEnabled(true);

        this.fornecedorView.getTfRazaoSocial().setEditable(true);
        this.fornecedorView.getTfContato().setEditable(true);
        this.fornecedorView.getTfEndereco().setEditable(true);
        this.fornecedorView.getTfNumero().setEditable(true);
        this.fornecedorView.getTfBairro().setEditable(true);
        this.fornecedorView.getCbxUF().setEnabled(true);
        this.fornecedorView.getTfTelefone().setEditable(true);
        this.fornecedorView.getTfCep().setEditable(true);
        this.fornecedorView.getTfCelular().setEditable(true);
        this.fornecedorView.getTfComplemento().setEditable(true);
        this.fornecedorView.getTfEmail().setEditable(true);

    }

}
