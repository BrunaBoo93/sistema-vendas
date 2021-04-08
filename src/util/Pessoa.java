/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *Classe responsavel por armazenar os atributos de pessoa fisica ou juridica
 * @author Michele Oliveira de Araujo
 * @since  07/04/2021
 * @version 1.0
 */
public enum Pessoa {
    
    FISICO("F","Pessoa Fisica"),
    JURIDICO("J","Pessoa Juridica");
    
    private String tipo;
    private String descricao;

    private Pessoa(String tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }
    
}
