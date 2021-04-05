/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import model.Cidade;
import model.Estado;
import view.ClienteView;
import view.MenuView;

/**
 *
 * @author boriani
 */
public class ClienteController {

    private final ClienteView clienteView;
    private ArrayList<Estado> listaEstados;
    private ArrayList<Cidade> listaCidades;

    //public ClienteController() {
    //  }
    public ClienteController(ClienteView clienteView) {
        this.clienteView = clienteView;
    }

    public void acaoBotaoNovo() {
        this.clienteView.getBtNovo().setEnabled(false);
        this.clienteView.getBtAlterar().setEnabled(false);
        this.clienteView.getBtExcluir().setEnabled(false);
        this.clienteView.getBtSair().setEnabled(false);
        //habilitando os botões de controle
        this.clienteView.getBtSalvarCliente().setEnabled(true);
        this.clienteView.getBtCancelar().setEnabled(true);

        this.clienteView.getRbPessoaFisica().setEnabled(true);
        this.clienteView.getRbPessoaJuridica().setEnabled(true);
        
    }

    public void acaoBotaoAlterar() {

    }

    public void acaoBotaoExcluir() {

    }

    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);; 
        menuController.desbloquearMenu(menu);
        this.clienteView.dispose();

    }

    public void acaoBotaoSalvar() {

    }

    public void acaoBotaoCancelar() {
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
        } catch (Exception e) {
        }
    }

    public void mascaraCpf() {
        try {
            this.clienteView.getTfCpf().setValue(null);
            MaskFormatter cpf = new MaskFormatter("###.###.###-##");
            this.clienteView.getTfCpf().setFormatterFactory(new DefaultFormatterFactory(cpf));
            this.clienteView.getLbCpf().setText("CPF:");
        } catch (Exception e) {
        }
    }

    public void mascaraInscricaoEstadual() {
        try {
            this.clienteView.getTfRg().setValue(null);
            MaskFormatter ie = new MaskFormatter("###.###.###");
            this.clienteView.getTfRg().setFormatterFactory(new DefaultFormatterFactory(ie));
            this.clienteView.getLbRg().setText("IE:");
            this.clienteView.getLbDataNascimento().setText("Data Fundacão:");
            this.clienteView.getLbNome().setText("Razão Social:");
        } catch (Exception e) {
        }
    }

    public void mascaraRg() {
        try {
            this.clienteView.getTfRg().setValue(null);
            MaskFormatter rg = new MaskFormatter("###.###.###");
            this.clienteView.getTfRg().setFormatterFactory(new DefaultFormatterFactory(rg));
            this.clienteView.getLbRg().setText("RG:");
            this.clienteView.getLbDataNascimento().setText("Data Nascimento:");
            this.clienteView.getLbNome().setText("Nome:");
        } catch (Exception e) {
        }
    }

    public void bloqueioInicial() {
        this.clienteView.getBtNovo().setEnabled(true);
        this.clienteView.getBtAlterar().setEnabled(true);
        this.clienteView.getBtExcluir().setEnabled(true);
        this.clienteView.getBtSair().setEnabled(true);
        //habilitando os botões de controle
        this.clienteView.getBtSalvarCliente().setEnabled(false);
        this.clienteView.getBtCancelar().setEnabled(false);

        bloquearCampos();
    }

    private void bloquearCampos() {

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
        this.clienteView.getTfNome().grabFocus();
        this.clienteView.getTfNome().setEditable(true);
        this.clienteView.getTfCpf().setEditable(true);
        this.clienteView.getTfRg().setEditable(true);
        this.clienteView.getTfDataNascimento().setEditable(true);

        this.clienteView.getTfEndereco().setEditable(true);
        this.clienteView.getTfNumeroEndereco().setEditable(true);
        this.clienteView.getTfComplemento().setEditable(true);
        this.clienteView.getTfBairro().setEditable(true);
        this.clienteView.getTfCep().setEditable(true);
        this.clienteView.getCbxUF().setEnabled(true);
        this.clienteView.getTfTelefone().setEditable(true);
        this.clienteView.getTfCelular().setEditable(true);
        this.clienteView.getTfEmail().setEditable(true);
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
                JOptionPane.showMessageDialog(null, "Erro ao consultar cidades");
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
}
