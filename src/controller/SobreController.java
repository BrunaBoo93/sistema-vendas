/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.MenuView;
import view.SobreView;


/**
 * Classe resopnsável por controlar os métodos de acesso a base de dados
 * @author Bruna Oriani
 * @since 24/03/2021
 * @version 1.0
 */
public class SobreController {
    private final SobreView sobreView;


    //public ClienteController() {
    //  }
    public SobreController(SobreView sobreView) {
        this.sobreView = sobreView;
    }

    
    public void acaoBotaoSair(MenuView menu) {
        MenuController menuController = new MenuController(menu);; 
        menuController.desbloquearMenu(menu);
        this.sobreView.dispose();

    }

    
}
