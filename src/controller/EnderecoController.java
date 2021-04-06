package controller;

import dao.EnderecoDAO;
import javax.swing.JOptionPane;
import model.Endereco;
import util.Mensagem;

/**
 * Classe responsável por armazenar os métodos de manutenção de bse de dados
 *
 * @author boriani
 */
public class EnderecoController {

    /*
     * método para incluir ou alterar um objeto no banco de dados
     */
    public void salvar(Endereco endereco) {
        try {
            new EnderecoDAO().salvar(endereco);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.endereco_erro, Mensagem.cadastro_endereco, 0);
        }
    }

    public void excluir(Endereco endereco) {
        try {
            new EnderecoDAO().excluir(endereco);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir endereço", "Cadastro ", 0);
        }
    }

}
