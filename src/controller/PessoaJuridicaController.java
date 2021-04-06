package controller;

import dao.PessoaJuridicaDAO;
import javax.swing.JOptionPane;
import model.PessoaJuridica;
import util.Mensagem;

/**
 * Classe responsável por armazenar os métodos de manutenção de bse de dados
 *
 * @author boriani
 */
public class PessoaJuridicaController {

    /*
     * método para incluir ou alterar um objeto no banco de dados
     */
    public void salvar(PessoaJuridica pessoaJuridica) {
        try {
            new PessoaJuridicaDAO().salvar(pessoaJuridica);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.pessoa_juridica_erro, Mensagem.cadastro_pessoa_juridica, 0);
        }
    }

    public void excluir(PessoaJuridica pessoa) {
        try {
            new PessoaJuridicaDAO().excluir(pessoa);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir pessoa", "Cadastro ", 0);
        }
    }

}
