package controller;

import dao.FuncionarioDAO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cidade;
import model.Contato;
import model.Endereco;
import model.Estado;
import model.Funcionario;
import model.PessoaFisica;
import util.Mensagem;
import util.Util;
import util.Valida;
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

    private FuncionarioView funcionarioView;

    //arraylist para lista de estado, cidade e funcionarios
    private ArrayList<Estado> listaEstados;
    private ArrayList<Cidade> listaCidades;
    private ArrayList<Funcionario> listaFuncionarios;

    private Funcionario funcionario;
    private PessoaFisica pessoa;
    private Endereco endereco;
    private Contato contato;

    //variavel booleana de controle
    private boolean alterar;

        //método contrutor
    public FuncionarioController() {
       
    }
    
    //método contrutor
    public FuncionarioController(FuncionarioView funcionarioView) {
        this.funcionarioView = funcionarioView;
    }

    //controle dos botões da ação do botao novo.
    public void acaoBotaoNovo() {
        //variavel alterar recebe false, só ira mudar ao chamar o botao de alterar
        alterar = false;
        //habilitando os botões de controle ao clicar em Novo
        this.funcionarioView.getBtSalvarFuncionario().setEnabled(true);
        this.funcionarioView.getBtCancelar().setEnabled(true);
        //os campos para criar um novo funcionário são desbloqueados
        desbloquearCampos();
        //foco no primeiro text fiels, cpf.
        this.funcionarioView.getTfCpf().grabFocus();

    }

    //controle dos botões da ação do botao alterar
    public void acaoBotaoAlterar() {
        //valiravel booleana que controla se a opção de alterar foi clicada, ao clicar, ela é alterada pra true
        alterar = true;
        //aqui o if valida apenas se não tiver nenhuma linha selecionada. ou seja, mostra a mensagem pedindo que sela selecionado alguma funcionario/linha 
        if (funcionarioView.getTabelaFuncionarios().getSelectedRow() < 0) {
            //mensagem informando que não há seleção. solicita ao usuário que seja selecionado algum funcionário 
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.selecione_funcionario, Mensagem.cadastro_funcionario, 0);
        } else { //entra nesse else se algum funcionário(linha) foi selecionado.
            //aqui o funcionario receber/carregar as informações do funcionário selecionado
            funcionario = listaFuncionarios.get(funcionarioView.getTabelaFuncionarios().getSelectedRow());
            //o método bloqueioAlterar é chamado para que somente os campos permitidos, de acordo com a regra de negócio, estejam habilitados para seleção
            bloqueioAlterar();
            //a tela é carregada
            carregarTela();

        }
    }

    //controle dos botões da ação do botao excluir
    public void acaoBotaoExcluir() {

        //aqui o if valida apenas se não tiver nenhuma linha selecionada. ou seja, mostra a mensagem pedindo que sela selecionado alguma funcionario/linha 
        if (funcionarioView.getTabelaFuncionarios().getSelectedRow() < 0) {
            //mensagem informando que não há seleção. solicita ao usuário que seja selecionado algum funcionário
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.selecione_funcionario, Mensagem.cadastro_funcionario, 0);
        } else { //entra nesse else se algum funcionário(linha) foi selecionado.
            //abaixo atribuição de valor a variavel -opcao-
            int opcao = JOptionPane.showConfirmDialog(funcionarioView, Mensagem.excluir_funcionario, Mensagem.cadastro_funcionario, 2);
            if (opcao == JOptionPane.YES_OPTION) { //se a variavel -opcao- foi selecionada, então esse if é validado

                //o funcionario recebe a linha que estava selecionada
                funcionario = listaFuncionarios.get(funcionarioView.getTabelaFuncionarios().getSelectedRow());

                //é atribuida a exclusão ao funcionário
                new FuncionarioDAO().excluir(funcionario);

                //aqui é feita a exclusão dos demais dados do funcionário pertencentes a outras classes/tabelas
                new ContatoController().excluir(funcionario.getContatoIdContato());
                new EnderecoController().excluir(funcionario.getEnderecoIdEndereco());
                new PessoaFisicaController().excluir(funcionario.getPessoaFisicaIdPessoaFisica());

                //mensagem de sucesso ao excluir o funcionário
                JOptionPane.showMessageDialog(funcionarioView, Mensagem.funcionario_excluido, Mensagem.cadastro_funcionario, 1);

                //a tabela é chamada novamente, carregando apenas os registros que não foram excluidos.
                carregarTabela();
            }

        }
    }

    //controle dos botões da ação do botao sair
    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);;
        menuController.desbloquearMenu(menu);
        this.funcionarioView.dispose();

    }

    //controle dos botões da ação do botao salvar
    public void acaoBotaoSalvar() {
        //esse if valida as informações inseridas antes de salvar
        if (validarDados()) {
            //esse if ira alterar os dados quando o botao de alterar é chamado, ou seja, a variaver alterar torna-se true
            if (alterar) {
                //os objetos recebem os dados que foram setados nos gets para que sejam alterados
                pessoa = funcionario.getPessoaFisicaIdPessoaFisica();
                endereco = funcionario.getEnderecoIdEndereco();
                contato = funcionario.getContatoIdContato();
            } else {
                //pra alterar utilizo objeto existente
                //para incluir preciso de um novo objeto

                //para salvar o objeto funcionario criamos novo objeto de funcionário e respectivos atributos, pessoa fisica, endereco e contato
                funcionario = new Funcionario();
                pessoa = new PessoaFisica();
                endereco = new Endereco();
                contato = new Contato();
            }

            //os objetos recebem os atributos que foram setados nos gets
            pessoa = getPessoaFisica();
            endereco = getEndereco();
            contato = getContato();

            //os objetos recebem seu atributos e sao salvos
            new PessoaFisicaController().salvar(pessoa);
            new EnderecoController().salvar(endereco);
            new ContatoController().salvar(contato);

            //aqui é atribuido valor a cada atributo da classe funcionario, os atributos que são da própria classe sao setados diretona classe, 
            //os que dependem de outra classe são setados a artir de um novo objeto que é criado
            funcionario.setLogin(this.funcionarioView.getTfLogin().getText());
            funcionario.setSenha(this.funcionarioView.getTfSenha().getText());
            funcionario.setPessoaFisicaIdPessoaFisica(pessoa);
            funcionario.setEnderecoIdEndereco(endereco);
            funcionario.setContatoIdContato(contato);

            try {
                new FuncionarioDAO().salvar(funcionario);
                //se o funcionário foi cadastrado com sucesso, será exibida uma mensagem com a confirmação
                JOptionPane.showMessageDialog(null, Mensagem.funcionarioSalvo, Mensagem.cadastro_funcionario, 1);
                //os campos textfiels, combos e etc serão limpos
                limparCampos();
                //o bloqueio inicial, que bloqueia os campos para que seja dada uma nova ação é chamado
                bloqueioInicial();
                //a tabela é carregada atualizada
                carregarTabela();
            } catch (Exception e) {
                e.printStackTrace();
                   JOptionPane.showMessageDialog(funcionarioView, Mensagem.funcionario_erro, Mensagem.cadastro_funcionario, 0);
            }

        }
    }

    //controle dos botões da ação do botao cancelar
    public void acaoBotaoCancelar() {
        //botões liberados ao clicar em cancelar
        this.funcionarioView.getBtNovo().setEnabled(true);
        this.funcionarioView.getBtAlterar().setEnabled(true);
        this.funcionarioView.getBtExcluir().setEnabled(true);
        this.funcionarioView.getBtSair().setEnabled(true);
        //desabilitando os botões de salvar e cancelar quando o cancelar é chamado
        this.funcionarioView.getBtSalvarFuncionario().setEnabled(false);
        this.funcionarioView.getBtCancelar().setEnabled(false);
        //ccaso algum campo tenha recebido algum dado e após o botão de cancelar foi chaado, os campos são limpos pelo método, limparCampos
        limparCampos();
        //os campos são bloqueados até que seja dado uma nova ação que faça o desbloqueio
        bloquearCampos();
    }

    //método de bloqueio inicial para habilitar e desabilitar os botões principais
    public void bloqueioInicial() {
        this.funcionarioView.getBtNovo().setEnabled(true);
        this.funcionarioView.getBtAlterar().setEnabled(true);
        this.funcionarioView.getBtExcluir().setEnabled(true);
        this.funcionarioView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.funcionarioView.getBtSalvarFuncionario().setEnabled(false);
        this.funcionarioView.getBtCancelar().setEnabled(false);
        //método que bloqueia os campos de digitação ou seleção
        bloquearCampos();
    }

    //método para bloquear os campos de digitação ou seleção
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

    //método para desbloquear os campos de digitação ou seleção
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
    
    //método para limpar os campos de digitação ou seleção
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
                JOptionPane.showMessageDialog(null, Mensagem.erro_consultar_cidade);
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

    private boolean validarDados() {

        //validando o CPF
        if (Valida.isCpfVazio(this.funcionarioView.getTfCpf().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.cpfVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfCpf().grabFocus();
            return false;
        } else if (Valida.isCpfInvalido(this.funcionarioView.getTfCpf().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.cpfInvalido, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfCpf().grabFocus();
            return false;
        }
        
        //validando o RG
            //validando o RG
            if (Valida.isRgVazio(funcionarioView.getTfRg().getText())) {
                JOptionPane.showMessageDialog(funcionarioView, Mensagem.rgVazio, Mensagem.cadastro_funcionario, 0);
                funcionarioView.getTfRg().grabFocus();
                return false;
            }
        
        //validando o nome
        if (Valida.isEmptyOrNull(this.funcionarioView.getTfNome().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.razaoSocialVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfNome().grabFocus();
            return false;
        }

        //validando a data de fundação
        if (Valida.isDataVazio(this.funcionarioView.getTfDataNascimento().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.dataFundacaoVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfDataNascimento().grabFocus();
            return false;
        } else if (Valida.isDataVazio(this.funcionarioView.getTfDataNascimento().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.dataInvalida, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfDataNascimento().grabFocus();
            return false;
        }

        // validando o endereço
        if (Valida.isEmptyOrNull(this.funcionarioView.getTfEndereco().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.enderecoVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfEndereco().grabFocus();
            return false;
        }

        // validando o numero
        if (!Valida.isInteger(this.funcionarioView.getTfNumero().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.numeroVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfNumero().grabFocus();
            return false;
        }

        // validando o bairro
        if (Valida.isEmptyOrNull(this.funcionarioView.getTfBairro().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.bairroVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfBairro().grabFocus();
            return false;
        }

        // validando o estado
        if (Valida.isComboInvalida(this.funcionarioView.getCbxUF().getSelectedIndex())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.estadoVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getCbxUF().grabFocus();
            return false;
        }

        // validando a cidade
        if (Valida.isComboInvalida(this.funcionarioView.getCbxCidade().getSelectedIndex())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.cidadeVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getCbxCidade().grabFocus();
            return false;
        }

        // validando o cep
        if (Valida.isCepVazio(this.funcionarioView.getTfCep().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.cepVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfCep().grabFocus();
            return false;
        }

        // validando o celular
        if (Valida.isCelularVazio(this.funcionarioView.getTfCelular().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.celularVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfCelular().grabFocus();
            return false;
        }

        // validando o email
        if (Valida.isEmptyOrNull(this.funcionarioView.getTfEmail().getText())) {
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.emailVazio, Mensagem.cadastro_funcionario, 0);
            this.funcionarioView.getTfEmail().grabFocus();
            return false;
        }

        return true;

    }

    private PessoaFisica getPessoaFisica() {

        pessoa.setCpf(this.funcionarioView.getTfCpf().getText());
        pessoa.setRg(this.funcionarioView.getTfRg().getText());
        pessoa.setNome(this.funcionarioView.getTfNome().getText());
        pessoa.setDataNascimento(this.funcionarioView.getTfDataNascimento().getText());
        return pessoa;
    }

    /*
     *método para retornar um novo objeto
     */
    private Endereco getEndereco() {

        endereco.setNome(this.funcionarioView.getTfEndereco().getText());
        endereco.setNumero(Util.getInteger(this.funcionarioView.getTfNumero().getText()));
        endereco.setComplemento(this.funcionarioView.getTfComplemento().getText());
        endereco.setBairro(this.funcionarioView.getTfBairro().getText());
        endereco.setCep(this.funcionarioView.getTfCep().getText());
        endereco.setCidadeIdCidade(listaCidades.get(this.funcionarioView.getCbxCidade().getSelectedIndex() - 1));
        return endereco;
    }

    /*
     *método para retornar um novo objeto
     */
    private Contato getContato() {

        contato.setTelefone(this.funcionarioView.getTfTelefone().getText());
        contato.setCelular(this.funcionarioView.getTfCelular().getText());
        contato.setEmail(this.funcionarioView.getTfEmail().getText());
        return contato;
    }


    /*
     * método responsável por chamar o DAO e arregador os funcionarioes cadastrados 
     * no banco de dados
     */
    private ArrayList<Funcionario> buscarTodos() { //?
        try {
            return listaFuncionarios = new FuncionarioDAO().buscarTodos(null);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(funcionarioView, "teste", Mensagem.cadastro_funcionario, 0);
        }
        return null;
    }

    /*
     *Método para carregar a JTable de funcionario
     */
    public void carregarTabela() {
        buscarTodos();
        DefaultTableModel modelo = (DefaultTableModel) funcionarioView.getTabelaFuncionarios().getModel(); //
        //limpar a tabla
        modelo.setRowCount(0);
        //carregar a tabela
        for (Funcionario funcionario : listaFuncionarios) {
            modelo.addRow(new String[]{
                funcionario.getPessoaFisicaIdPessoaFisica().getNome(),
                funcionario.getEnderecoIdEndereco().getCidadeIdCidade().getNome(),
                funcionario.getContatoIdContato().getTelefone(),
                funcionario.getContatoIdContato().getCelular()
            });
        }

    }

    /*
     *método para carregar a tela com dados do fonewcedor
     */
    private void carregarTela() {
        funcionarioView.getTfCpf().setText(funcionario.getPessoaFisicaIdPessoaFisica().getCpf());
        funcionarioView.getTfRg().setText(funcionario.getPessoaFisicaIdPessoaFisica().getRg());
        funcionarioView.getTfNome().setText(funcionario.getPessoaFisicaIdPessoaFisica().getNome());
        funcionarioView.getTfDataNascimento().setText(funcionario.getPessoaFisicaIdPessoaFisica().getDataNascimento());
        funcionarioView.getTfEndereco().setText(funcionario.getEnderecoIdEndereco().getNome());
        funcionarioView.getTfNumero().setText(funcionario.getEnderecoIdEndereco().getNumero() + "");
        funcionarioView.getTfComplemento().setText(funcionario.getEnderecoIdEndereco().getComplemento());
        funcionarioView.getTfBairro().setText(funcionario.getEnderecoIdEndereco().getBairro());

        //hierarquia de banco
        funcionarioView.getCbxUF().setSelectedItem(funcionario.getEnderecoIdEndereco().getCidadeIdCidade().getEstadoIdEstado().getNome());
        funcionarioView.getCbxCidade().setSelectedItem(funcionario.getEnderecoIdEndereco().getCidadeIdCidade().getNome());
        funcionarioView.getTfCep().setText((funcionario.getEnderecoIdEndereco().getCep()));
        funcionarioView.getTfTelefone().setText(funcionario.getContatoIdContato().getTelefone());
        funcionarioView.getTfCelular().setText(funcionario.getContatoIdContato().getCelular());
        funcionarioView.getTfEmail().setText(funcionario.getContatoIdContato().getEmail());
        funcionarioView.getTfLogin().setText(funcionario.getLogin());
        funcionarioView.getTfSenha().setText(funcionario.getSenha());
      

    }

    /*
     * método para bloquear os cmapos na ação do alterar
     */
    private void bloqueioAlterar() {
        this.funcionarioView.getBtNovo().setEnabled(false);
        this.funcionarioView.getBtAlterar().setEnabled(false);
        this.funcionarioView.getBtExcluir().setEnabled(false);
        this.funcionarioView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.funcionarioView.getBtSalvarFuncionario().setEnabled(true);
        this.funcionarioView.getBtCancelar().setEnabled(true);

        this.funcionarioView.getTfEndereco().setEditable(true);
        this.funcionarioView.getTfNumero().setEditable(true);
        this.funcionarioView.getTfBairro().setEditable(true);
        this.funcionarioView.getCbxUF().setEnabled(true);
        this.funcionarioView.getTfTelefone().setEditable(true);
        this.funcionarioView.getTfCep().setEditable(true);
        this.funcionarioView.getTfCelular().setEditable(true);
        this.funcionarioView.getTfComplemento().setEditable(true);
        this.funcionarioView.getTfEmail().setEditable(true);

    }
    

    public ArrayList<Funcionario> buscarPorLogin(String login){
        try {
            return new FuncionarioDAO().buscarPorLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(funcionarioView, Mensagem.funcionario_erro_consulta, Mensagem.cadastro_funcionario, 0);
        }
        return null;
    }
}
