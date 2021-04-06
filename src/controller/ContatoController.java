
package controller;

import dao.ContatoDAO;
import javax.swing.JOptionPane;
import model.Contato;
import util.Mensagem;

/**
 * Classe responsável por armazenar os métodos de manutenção de bse de dados
 * @author boriani
 */
public class ContatoController {
 
    /*
    * método para incluir ou alterar um objeto no banco de dados
    */
    public void salvar(Contato contato) {
        try {
            new ContatoDAO().salvar(contato);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Mensagem.contato_erro, Mensagem.cadastro_contato, 0);
        }
    }
    
    public void excluir(Contato contato) {
        try {
            new ContatoDAO().excluir(contato);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir contato", "Cadastro ", 0);
        }
    }
}
