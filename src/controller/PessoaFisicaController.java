package controller;

import dao.PessoaFisicaDAO;
import javax.swing.JOptionPane;
import model.PessoaFisica;
import util.Mensagem;

/**
 *
 * @author boriani
 */
public class PessoaFisicaController {
        /*
     * método para incluir ou alterar um objeto no banco de dados
     */
    public void salvar(PessoaFisica pessoaFisica) {
        try {
            new PessoaFisicaDAO().salvar(pessoaFisica);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.pessoa_fisica_erro, Mensagem.cadastro_pessoa_fisica, 0);
        }
    }

    public void excluir(PessoaFisica pessoa) {
        try {
            new PessoaFisicaDAO().excluir(pessoa);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir pessoa", "Cadastro ", 0);
        }
    }
}
