/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ClienteDAO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import model.Cidade;
import model.Cliente;
import model.Contato;
import model.Endereco;
import model.Estado;
import model.PessoaFisica;
import model.PessoaJuridica;
import util.Mensagem;
import util.Pessoa;
import util.Util;
import util.Valida;
import view.ClienteView;
import view.MenuView;

/**
 *
 * @author boriani
 */
public class ClienteController {

    private ClienteView clienteView;
    private ArrayList<Estado> listaEstados;
    private ArrayList<Cidade> listaCidades;
    private ArrayList<Cliente> listaClientes;

    private Cliente cliente;
    private PessoaFisica pessoaFisica;
    private PessoaJuridica pessoaJuridica;
    private Endereco endereco;
    private Contato contato;

    private boolean alterar;

    //public ClienteController() {
    //  }
    public ClienteController(ClienteView clienteView) {
        this.clienteView = clienteView;
    }

    public void acaoBotaoNovo() {

        this.clienteView.getRbPessoaFisica().setSelected(true);
        this.clienteView.getBtNovo().setEnabled(false);
        this.clienteView.getBtAlterar().setEnabled(false);
        this.clienteView.getBtExcluir().setEnabled(false);
        this.clienteView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.clienteView.getBtSalvarCliente().setEnabled(true);
        this.clienteView.getBtCancelar().setEnabled(true);
        desbloquearCampos();
        alterar = false;
        this.clienteView.getTfNome().grabFocus();
    }

    public void acaoBotaoAlterar() {
        //valiravel booleana que controla se a opção de alterar foi clicada, ao clicar, ela é alterada pra true
        alterar = true;

        //aqui o if valida apenas se não tiver nenhuma linha selecionada. ou seja, mostra a mensagem pedindo que sela selecionado algum cliente/linha 
        if (clienteView.getTabelaClientes().getSelectedRow() < 0) {
            //mensagem informando que não há seleção. solicita ao usuário que seja selecionado algum cliente 
            JOptionPane.showMessageDialog(clienteView, Mensagem.selecione_cliente, Mensagem.cadastro_cliente, 0);
        } else { //entra nesse else se algum cliente(linha) foi selecionado.
            //aqui o cliente receber/carregar as informações do cliente selecionado
            cliente = listaClientes.get(clienteView.getTabelaClientes().getSelectedRow());
            //a tela é carregada 
            carregarTela();
        }
        //o método bloqueioAlterar é chamado para que somente os campos permitidos, de acordo com a regra de negócio, estejam habilitados para seleção
        bloqueioAlterar();

    }

    public void acaoBotaoExcluir() {

        //aqui o if valida apenas se não tiver nenhuma linha selecionada. ou seja, mostra a mensagem pedindo que sela selecionado alguma cliente/linha 
        if (clienteView.getTabelaClientes().getSelectedRow() < 0) {
            //mensagem informando que não há seleção. solicita ao usuário que seja selecionado algum cliente
            JOptionPane.showMessageDialog(clienteView, Mensagem.selecione_cliente, Mensagem.cadastro_cliente, 0);
        } else { //entra nesse else se algum cliente(linha) foi selecionado.
            //abaixo atribuição de valor a variavel -opcao-
            int opcao = JOptionPane.showConfirmDialog(clienteView, Mensagem.excluir_funcionario, Mensagem.cadastro_funcionario, 2);
            if (opcao == JOptionPane.YES_OPTION) { //se a variavel -opcao- foi selecionada, então esse if é validado

                //o cliente recebe a linha que estava selecionada
                cliente = listaClientes.get(clienteView.getTabelaClientes().getSelectedRow());

                //é atribuida a exclusão ao cliente
                new ClienteDAO().excluir(cliente);

                //aqui é feita a exclusão dos demais dados do cliente pertencentes a outras classes/tabelas
                new ContatoController().excluir(cliente.getContatoIdContato());
                new EnderecoController().excluir(cliente.getEnderecoIdEndereco());

                if (cliente.getTipoPessoa().equals(Pessoa.FISICO)) {
                    new PessoaFisicaController().excluir(cliente.getPessoaFisicaIdPessoaFisica());

                } else {
                    new PessoaJuridicaController().excluir(cliente.getPessoaJuridicaIdPessoaJuridica());

                }

                //mensagem de sucesso ao excluir o cliente
                JOptionPane.showMessageDialog(clienteView, Mensagem.cliente_excluido, Mensagem.cadastro_cliente, 1);

                //a tabela é chamada novamente, carregando apenas os registros que não foram excluidos.
                carregarTabela();
            }

        }
    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);;
        menuController.desbloquearMenu(menu);
        this.clienteView.dispose();

    }

    public void acaoBotaoSalvar() {
        if (validarDados()) {
            if (alterar) {

                if (clienteView.getRbPessoaFisica().isSelected()) {
                    pessoaFisica = cliente.getPessoaFisicaIdPessoaFisica();
                    pessoaFisica = getPessoaFisica();
                } else {
                    pessoaJuridica = cliente.getPessoaJuridicaIdPessoaJuridica();
                    pessoaJuridica = getPessoaJuridica();
                }

                endereco = cliente.getEnderecoIdEndereco();
                contato = cliente.getContatoIdContato();

            } else {
                // procedimentos de inclusão

                cliente = new Cliente();
                endereco = new Endereco();
                contato = new Contato();

                if (clienteView.getRbPessoaFisica().isSelected()) {
                    pessoaFisica = new PessoaFisica();
                    pessoaFisica = getPessoaFisica();
                } else {
                    pessoaJuridica = new PessoaJuridica();
                    pessoaJuridica = getPessoaJuridica();
                }
            }
            endereco = getEndereco();
            contato = getContato();
            cliente = getCliente();

            if (clienteView.getRbPessoaFisica().isSelected()) {
                new PessoaFisicaController().salvar(pessoaFisica);
            } else {
                new PessoaJuridicaController().salvar(pessoaJuridica);
            }

            new EnderecoController().salvar(endereco);
            new ContatoController().salvar(contato);

            try {
                new ClienteDAO().salvar(cliente);
                JOptionPane.showMessageDialog(clienteView, Mensagem.clienteSalvo, Mensagem.cadastro_cliente, 1);
                acaoBotaoCancelar();
                carregarTabela();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(clienteView, Mensagem.cliente_erro, Mensagem.cadastro_cliente, 0);
            }

        }

    }

    public void acaoBotaoCancelar() {
        mascaraCpf();
        mascaraRg();
        this.clienteView.getBtNovo().setEnabled(true);
        this.clienteView.getBtAlterar().setEnabled(true);
        this.clienteView.getBtExcluir().setEnabled(true);
        this.clienteView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.clienteView.getBtSalvarCliente().setEnabled(false);
        this.clienteView.getBtCancelar().setEnabled(false);
        limparCampos();
        bloquearCampos();
    }

    public void mascaraCnpj() {
        try {
            this.clienteView.getTfCpf().setValue(null);
            MaskFormatter cnpj = new MaskFormatter("##.###.###/####-##");
            this.clienteView.getTfCpf().setFormatterFactory(new DefaultFormatterFactory(cnpj));
            this.clienteView.getLbCpf().setText("CNPJ:");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void mascaraCpf() {
        try {
            this.clienteView.getTfCpf().setValue(null);
            MaskFormatter cpf = new MaskFormatter("###.###.###-##");
            this.clienteView.getTfCpf().setFormatterFactory(
                    new DefaultFormatterFactory(cpf));
            this.clienteView.getLbCpf().setText("CPF:");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void mascaraInscricaoEstadual() {
        try {
            this.clienteView.getTfRg().setValue(null);
            MaskFormatter ie = new MaskFormatter("###.###.###.###");
            this.clienteView.getTfRg().setFormatterFactory(new DefaultFormatterFactory(ie));
            this.clienteView.getLbRg().setText("IE:");
            this.clienteView.getLbDataNascimento().setText("Data Fundacão:");
            this.clienteView.getLbNome().setText("Razão Social:");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void mascaraRg() {
        try {
            this.clienteView.getTfRg().setValue(null);
            MaskFormatter rg = new MaskFormatter("##.###.###-#");
            this.clienteView.getTfRg().setFormatterFactory(new DefaultFormatterFactory(rg));
            this.clienteView.getLbRg().setText("RG:");
            this.clienteView.getLbDataNascimento().setText("Data Nascimento:");
            this.clienteView.getLbNome().setText("Nome:");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void bloqueioInicial() {
        mascaraRg();
        mascaraCpf();
        this.clienteView.getBtNovo().setEnabled(true);
        this.clienteView.getBtAlterar().setEnabled(true);
        this.clienteView.getBtExcluir().setEnabled(true);
        this.clienteView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.clienteView.getBtSalvarCliente().setEnabled(false);
        this.clienteView.getBtCancelar().setEnabled(false);

        bloquearCampos();
    }

    public void bloquearCampos() {

        //ação´para bloquear os botões de radiobuton
        this.clienteView.getRbPessoaFisica().setEnabled(false);
        this.clienteView.getRbPessoaJuridica().setEnabled(false);

        //ação para bloquear os campos de textfiels e combo box
        this.clienteView.getTfNome().setEditable(false);
        this.clienteView.getTfCpf().setEditable(false);
        this.clienteView.getTfRg().setEditable(false);
        this.clienteView.getTfDataNascimento().setEditable(false);

        this.clienteView.getTfEndereco().setEditable(false);
        this.clienteView.getTfNumeroEndereco().setEditable(false);
        this.clienteView.getTfComplemento().setEditable(false);
        this.clienteView.getTfBairro().setEditable(false);
        this.clienteView.getTfCep().setEditable(false);
        this.clienteView.getCbxUF().setEnabled(false);
        this.clienteView.getCbxCidade().setEnabled(false);
        this.clienteView.getTfTelefone().setEditable(false);
        this.clienteView.getTfCelular().setEditable(false);
        this.clienteView.getTfEmail().setEditable(false);
    }

    public void valida() {
        if (this.clienteView.getRbPessoaFisica().isSelected()) {
            this.clienteView.getRbPessoaJuridica().setEnabled(false);
        } else if (this.clienteView.getRbPessoaJuridica().isSelected()) {
            this.clienteView.getRbPessoaFisica().setEnabled(false);
        }
    }

    public void desbloquearCampos() {
        this.clienteView.getTfCpf().grabFocus();
        this.clienteView.getTfCpf().setEditable(true);
        this.clienteView.getTfRg().setEditable(true);
        this.clienteView.getTfDataNascimento().setEditable(true);
        this.clienteView.getTfNome().setEditable(true);
        this.clienteView.getTfEndereco().setEditable(true);
        this.clienteView.getTfNumeroEndereco().setEditable(true);
        this.clienteView.getTfBairro().setEditable(true);
        this.clienteView.getCbxCidade().setEnabled(true);
        this.clienteView.getCbxUF().setEnabled(true);
        this.clienteView.getTfTelefone().setEditable(true);
        this.clienteView.getTfCep().setEditable(true);
        this.clienteView.getTfEmail().setEditable(true);
        this.clienteView.getTfCelular().setEditable(true);
        this.clienteView.getTfComplemento().setEditable(true);
        this.clienteView.getTfDataNascimento().setEditable(true);
        this.clienteView.getRbPessoaFisica().setEnabled(true);
        this.clienteView.getRbPessoaJuridica().setEnabled(true);

    }

    public void limparCampos() {

        this.clienteView.getTfNome().setText(null);
        this.clienteView.getTfCpf().setValue(null);
        this.clienteView.getTfRg().setValue(null);
        this.clienteView.getTfDataNascimento().setValue(null);

        this.clienteView.getTfEndereco().setText(null);
        this.clienteView.getTfNumeroEndereco().setText(null);
        this.clienteView.getTfComplemento().setText(null);
        this.clienteView.getTfBairro().setText(null);
        this.clienteView.getTfCep().setValue(null);
        this.clienteView.getCbxUF().setSelectedIndex(0);
        this.clienteView.getCbxCidade().setSelectedIndex(0);
        this.clienteView.getTfTelefone().setValue(null);
        this.clienteView.getTfCelular().setValue(null);
        this.clienteView.getTfEmail().setText(null);
        this.clienteView.getGrpPessoa().clearSelection();

    }

    /*
     * método para carregar a combo de estado
     */
    public void carregarComboEstado() {
        listaEstados = new EstadoController().buscarTodos();
        this.clienteView.getCbxUF().addItem("- Selecionar Estado -");
        for (Estado estado : new EstadoController().buscarTodos()) {
            this.clienteView.getCbxUF().addItem(estado.getNome());
        }
    }

    /*
     * método para carregar a combo de cidade
     */
    public void carregarComboCidade() {
        int indice = this.clienteView.getCbxUF().getSelectedIndex() - 1;
        if (indice >= 0) {
            try {
                listaCidades = new CidadeController().buscarPorEstado(listaEstados.get(indice));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, Mensagem.erro_consultar_cidade);
                Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //removendo todos os dados da combo
            this.clienteView.getCbxCidade().removeAllItems();
            this.clienteView.getCbxCidade().addItem(" - Selecione Cidade -");

            for (Cidade cidade : listaCidades) {
                this.clienteView.getCbxCidade().addItem(cidade.getNome());

            }
            this.clienteView.getCbxCidade().setEnabled(true);

        }
    }

    /*
     * método responsável por chamar o DAO e arregador os clientes cadastrados 
     * no banco de dados
     */
    public ArrayList<Cliente> buscarTodos() {
        try {
            return listaClientes = new ClienteDAO().buscarPorEstado(null);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(clienteView, Mensagem.cliente_erro_consulta, Mensagem.cadastro_cliente, 0);
        }
        return null;
    }

    /*
     *Método para carregar a JTable de clientes
     */
    public void carregarTabela() {
        buscarTodos();
        DefaultTableModel modelo = (DefaultTableModel) clienteView.getTabelaClientes().getModel();
        // limpar a tabela
        modelo.setRowCount(0);
        // carregar a tabela
        for (Cliente cliente : listaClientes) {
            String nome = "";
            if (cliente.getTipoPessoa().equals(Pessoa.FISICO.getTipo())) {
                nome = cliente.getPessoaFisicaIdPessoaFisica().getNome();
            } else {
                nome = cliente.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial();
            }
            modelo.addRow(new String[]{nome, cliente.getEnderecoIdEndereco().getCidadeIdCidade().getNome(), cliente.getContatoIdContato().getCelular(), cliente.getContatoIdContato().getEmail()});
        }

    }

    private boolean validarDados() {
        //verificando o tipo de pessoa
        if (clienteView.getRbPessoaFisica().isSelected()) {
            //validar dados fisico
            if (Valida.isCpfVazio(this.clienteView.getTfCpf().getText())) {
                JOptionPane.showMessageDialog(clienteView, Mensagem.cpfVazio, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfCpf().grabFocus();
                return false;
            } else if (Valida.isCpfInvalido(this.clienteView.getTfCpf().getText())) {
                JOptionPane.showMessageDialog(clienteView, Mensagem.cpfInvalido, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfCpf().grabFocus();
                return false;
            }

            //validando o RG
            if (Valida.isRgVazio(clienteView.getTfRg().getText())) {
                JOptionPane.showMessageDialog(clienteView, Mensagem.rgVazio, Mensagem.cadastro_cliente, 0);
                clienteView.getTfRg().grabFocus();
                return false;
            }

            // validando o nome
            if (Valida.isEmptyOrNull(this.clienteView.getTfNome().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.nomeVazio, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfNome().grabFocus();
                return false;
            }

            // validando a data de nascimento
            if (Valida.isDataVazio(this.clienteView.getTfDataNascimento().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.dataNascimentoVazio, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfDataNascimento().grabFocus();
                return false;
            } else if (Valida.isDataInvalida(this.clienteView.getTfDataNascimento().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.dataInvalida, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfDataNascimento().grabFocus();
                return false;
            }
        } else {
            //validar dados juridico
            // validando o CNPJ
            if (Valida.isCnpjVazio(this.clienteView.getTfCpf().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.cnpjVazio, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfCpf().grabFocus();
                return false;
            } else if (Valida.isCnpjInvalido(this.clienteView.getTfCpf().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.cnpjInvalido, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfCpf().grabFocus();
                return false;
            }

            // validando a inscrição estadual
            if (Valida.isInscricaoEstadualVazio(this.clienteView.getTfRg().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.inscricaoEstadualVazio, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfRg().grabFocus();
                return false;
            } else if (Valida.isInscricaoEstadualInvalido(this.clienteView.getTfRg().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.inscricaoEstadualInvalida, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfRg().grabFocus();
                return false;
            }

            // validando a razão social
            if (Valida.isEmptyOrNull(this.clienteView.getTfNome().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.razaoSocialVazio, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfNome().grabFocus();
                return false;
            }

            // validando a data de fundação
            if (Valida.isDataVazio(this.clienteView.getTfDataNascimento().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.dataFundacaoVazio, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfDataNascimento().grabFocus();
                return false;
            } else if (Valida.isDataInvalida(this.clienteView.getTfDataNascimento().getText())) {
                JOptionPane.showMessageDialog(null, Mensagem.dataInvalida, Mensagem.cadastro_cliente, 0);
                this.clienteView.getTfDataNascimento().grabFocus();
                return false;
            }

        }

        // dados comuns - endereço e contato
        // validando o endereço
        if (Valida.isEmptyOrNull(this.clienteView.getTfEndereco().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.enderecoVazio, Mensagem.cadastro_cliente, 0);
            this.clienteView.getTfEndereco().grabFocus();
            return false;
        }

        // validando o numero
        if (!Valida.isInteger(this.clienteView.getTfNumeroEndereco().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.numeroVazio, Mensagem.cadastro_cliente, 0);
            this.clienteView.getTfNumeroEndereco().grabFocus();
            return false;
        }

        // validando o bairro
        if (Valida.isEmptyOrNull(this.clienteView.getTfBairro().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.bairroVazio, Mensagem.cadastro_cliente, 0);
            this.clienteView.getTfBairro().grabFocus();
            return false;
        }

        // validando o cep
        if (Valida.isCepVazio(this.clienteView.getTfCep().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.cepVazio, Mensagem.cadastro_cliente, 0);
            this.clienteView.getTfCep().grabFocus();
            return false;
        }

        // validando o estado
        if (Valida.isComboInvalida(this.clienteView.getCbxUF().getSelectedIndex())) {
            JOptionPane.showMessageDialog(null, Mensagem.estadoVazio, Mensagem.cadastro_cliente, 0);
            this.clienteView.getCbxUF().grabFocus();
            return false;
        }

        // validando a cidade
        if (Valida.isComboInvalida(this.clienteView.getCbxCidade().getSelectedIndex())) {
            JOptionPane.showMessageDialog(null, Mensagem.cidadeVazio, Mensagem.cadastro_cliente, 0);
            this.clienteView.getCbxCidade().grabFocus();
            return false;
        }

        // validando o celular
        if (Valida.isCelularVazio(this.clienteView.getTfCelular().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.celularVazio, Mensagem.cadastro_cliente, 0);
            this.clienteView.getTfCelular().grabFocus();
            return false;
        }

        // validando o email
        if (Valida.isEmptyOrNull(this.clienteView.getTfEmail().getText())) {
            JOptionPane.showMessageDialog(null, Mensagem.emailVazio, Mensagem.cadastro_cliente, 0);
            this.clienteView.getTfEmail().grabFocus();
            return false;
        }
        return true;

    }

    /*
     * método para retornar um novo objeto
     */
    private Endereco getEndereco() {
        endereco.setNome(this.clienteView.getTfEndereco().getText());
        endereco.setNumero(Util.getInteger(this.clienteView.getTfNumeroEndereco().getText()));
        endereco.setComplemento(this.clienteView.getTfComplemento().getText());
        endereco.setBairro(this.clienteView.getTfBairro().getText());
        endereco.setCep(this.clienteView.getTfCep().getText());
        endereco.setCidadeIdCidade(listaCidades.get(this.clienteView.getCbxCidade().getSelectedIndex() - 1));
        return endereco;
    }

    /*
     * método para retornar um novo objeto
     */
    private Contato getContato() {
        contato.setTelefone(this.clienteView.getTfTelefone().getText());
        contato.setCelular(this.clienteView.getTfCelular().getText());
        contato.setEmail(this.clienteView.getTfEmail().getText());
        return contato;
    }

    /*
     * método para retornar um objeto
     */
    private PessoaFisica getPessoaFisica() {
        pessoaFisica.setNome(clienteView.getTfNome().getText());
        pessoaFisica.setRg(clienteView.getTfRg().getText());
        pessoaFisica.setCpf(clienteView.getTfCpf().getText());
        pessoaFisica.setDataNascimento(clienteView.getTfDataNascimento().getText());
        return pessoaFisica;
    }

    /*
     * método para retornar um objeto
     */
    private PessoaJuridica getPessoaJuridica() {
        pessoaJuridica.setRazaoSocial(clienteView.getTfNome().getText());
        pessoaJuridica.setInscricaoEstadual(clienteView.getTfRg().getText());
        pessoaJuridica.setCnpj(clienteView.getTfCpf().getText());
        pessoaJuridica.setDataFundacao(clienteView.getTfDataNascimento().getText());
        return pessoaJuridica;
    }

    /*
     * método para retornar um objeto
     */
    private Cliente getCliente() {
        if (clienteView.getRbPessoaFisica().isSelected()) {
            cliente.setTipoPessoa(Pessoa.FISICO.getTipo());
            cliente.setPessoaFisicaIdPessoaFisica(pessoaFisica);
        } else {
            cliente.setTipoPessoa(Pessoa.JURIDICO.getTipo());
            cliente.setPessoaJuridicaIdPessoaJuridica(pessoaJuridica);
        }
        cliente.setEnderecoIdEndereco(endereco);
        cliente.setContatoIdContato(contato);
        return cliente;
    }

    /*
     *método para carregar a tela com dados do fonewcedor
     */
    private void carregarTela() {

        if (cliente.getTipoPessoa().equals(Pessoa.FISICO.getTipo())) {
            mascaraCpf();
            mascaraRg();
            clienteView.getRbPessoaFisica().setSelected(true);
            clienteView.getTfNome().setText(cliente.getPessoaFisicaIdPessoaFisica().getNome());
            clienteView.getTfCpf().setText(cliente.getPessoaFisicaIdPessoaFisica().getCpf());
            clienteView.getTfDataNascimento().setText(cliente.getPessoaFisicaIdPessoaFisica().getDataNascimento());
            clienteView.getTfRg().setText(cliente.getPessoaFisicaIdPessoaFisica().getRg());

        } else {
            mascaraCnpj();
            mascaraInscricaoEstadual();
            clienteView.getRbPessoaJuridica().setSelected(true);
            clienteView.getTfNome().setText(cliente.getPessoaJuridicaIdPessoaJuridica().getRazaoSocial());
            clienteView.getTfCpf().setText(cliente.getPessoaJuridicaIdPessoaJuridica().getCnpj());
            clienteView.getTfDataNascimento().setText(cliente.getPessoaJuridicaIdPessoaJuridica().getDataFundacao());
            clienteView.getTfRg().setText(cliente.getPessoaJuridicaIdPessoaJuridica().getInscricaoEstadual());
        }

        clienteView.getTfEndereco().setText(cliente.getEnderecoIdEndereco().getNome());
        clienteView.getTfNumeroEndereco().setText(cliente.getEnderecoIdEndereco().getNumero() + "");
        clienteView.getTfComplemento().setText(cliente.getEnderecoIdEndereco().getComplemento());
        clienteView.getTfBairro().setText(cliente.getEnderecoIdEndereco().getBairro());
        clienteView.getTfCep().setText((cliente.getEnderecoIdEndereco().getCep()));
        clienteView.getCbxUF().setSelectedItem(cliente.getEnderecoIdEndereco().getCidadeIdCidade().getEstadoIdEstado().getNome());
        clienteView.getCbxCidade().setSelectedItem(cliente.getEnderecoIdEndereco().getCidadeIdCidade().getNome());
        clienteView.getTfTelefone().setText(cliente.getContatoIdContato().getTelefone());
        clienteView.getTfCelular().setText(cliente.getContatoIdContato().getCelular());
        clienteView.getTfEmail().setText(cliente.getContatoIdContato().getEmail());

    }

    /*
     * método para bloquear os cmapos na ação do alterar
     */
    private void bloqueioAlterar() {
        this.clienteView.getBtNovo().setEnabled(false);
        this.clienteView.getBtAlterar().setEnabled(false);
        this.clienteView.getBtExcluir().setEnabled(false);
        this.clienteView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.clienteView.getBtSalvarCliente().setEnabled(true);
        this.clienteView.getBtCancelar().setEnabled(true);

        this.clienteView.getTfCpf().setEditable(false);
        this.clienteView.getTfRg().setEditable(false);
        this.clienteView.getTfDataNascimento().setEditable(false);

        this.clienteView.getTfNome().setEditable(true);
        this.clienteView.getTfEndereco().setEditable(true);
        this.clienteView.getTfNumeroEndereco().setEditable(true);
        this.clienteView.getTfComplemento().setEditable(true);
        this.clienteView.getTfBairro().setEditable(true);
        this.clienteView.getTfCep().setEditable(true);
        this.clienteView.getCbxUF().setEnabled(true);
        this.clienteView.getCbxCidade().setEnabled(true);
        this.clienteView.getTfTelefone().setEditable(true);
        this.clienteView.getTfCelular().setEditable(true);
        this.clienteView.getTfEmail().setEditable(true);

    }

}
