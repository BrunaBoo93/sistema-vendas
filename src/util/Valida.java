package util;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.ie.IESaoPauloValidator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Classe responsável por armazenar os método de validação do sistema
 *
 * @author Bruna Oriani
 * @versio 1.0
 */
public class Valida {

    /*
     * método para verificar se o CNPJ foi preenchido
     */
    public static boolean isCnpjVazio(String args) {
        return args.equals(Mascara.MASCARA_CNPJ);
    }

    /*
     *método para verificar se o CNPJ é valido
     */
    public static boolean isCnpjInvalido(String args) {
        CNPJValidator validador = new CNPJValidator();
        try {
            validador.assertValid(args);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /*
     * método para verificar se o CPF foi preenchido
     */
    public static boolean isCpfVazio(String args) {
        return args.equals(Mascara.MASCARA_CPF);
    }

    /*
     *método para verificar se o CPF é valido
     */
    public static boolean isCpfInvalido(String args) {
        CPFValidator validador = new CPFValidator();
        try {
            validador.assertValid(args);
            return false;
        } catch (Exception e) {
            return true;
        }

    }

    /*
     * método para verificar se a Inscrição Estadual foi preenchidA
     */
    public static boolean isInscricaoEstadualVazio(String args) {
        return args.equals(Mascara.MASCARA_IE);
    }

    /*
     *método para verificar se a Inscrição Estadual é validA
     */
    public static boolean isInscricaoEstadualInvalido(String args) {
        IESaoPauloValidator validador = new IESaoPauloValidator();
        try {
            validador.assertValid(args);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /*
     *método para verificar se uma String está vazia ou nula
     */
    public static boolean isEmptyOrNull(String args) {
        return (args.trim().equals("") || args == null);
    }

    /*
     * método para verificar se a DATA foi preenchida
     */
    public static boolean isDataVazio(String args) {
        return args.equals(Mascara.MASCARA_DATA);
    }

    /*
     *método para verificar se a DATA é valida
     */
    public static boolean isDataInvalida(String args) {

        String formato = "dd/MM/uuuu";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(formato)
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate date = LocalDate.parse(args, dateTimeFormatter);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }

    }

    /*
     * metodo para verificar s eo ampo e do tipo inteiro
     */
    public static boolean isInteger(String args) {
        try {
            Integer.parseInt(args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * metodo para verificar s eo ampo e do tipo double
     */
    public static boolean isDouble(String args) {
        try {
            Double.parseDouble(args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * método para vlaidar selecao da combobox
     */
    public static boolean isComboInvalida(int index) {
        return index == 0;
    }

    /*
     * método para verificar se o CEP foi preenchido
     */
    public static boolean isCepVazio(String args) {
        return args.equals(Mascara.MASCARA_CEP);
    }

    /*
     * método para verificar se o CELULAR foi preenchido
     */
    public static boolean isCelularVazio(String args) {
        return args.equals(Mascara.MASCARA_CELULAR);
    }

    /*
     * método para verificar se o RG foi preenchido
     */
    public static boolean isRgVazio(String args) {
        return args.equals(Mascara.MASCARA_RG);
    }

}
