/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aewinformatica;

import com.aewinformatica.utilitario.Conexao;
import com.aewinformatica.utilitario.Utils;
import com.aewinformatica.utilitario.XMLReaderConfig;

/**
 *
 * @author Jessica
 */
public class ConfigProjeto extends  Conexao{
    

    public ConfigProjeto() {

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        XMLReaderConfig xr = new XMLReaderConfig();
                        xr.trataXML( Utils.raiz+ "config.xml");
        
        ConfigProjeto conf = new ConfigProjeto();
                      conf.setDriver(xr.getY_driver());
                      conf.setUsuario(xr.getY_usuarioBD());
//                      conf.criarBancoDeDadosUI();
                      conf.conectar();
                      conf.VerificarTabelas();
            
}
        

    

    
}
