/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aewinformatica;

import com.aewinformatica.utilitario.Conexao;
import com.aewinformatica.utilitario.Utils;
import com.aewinformatica.utilitario.XMLReaderConfig;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Jessica
 */
public class ConfigProjeto extends  Conexao{
    
    
    private static String driver;

    public ConfigProjeto() {
//        testeConnection = getConexao();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ConfigProjeto conf = new ConfigProjeto();
        
         UIManager.put("OptionPane.yesButtonText", "Conectar com Banco-de-dados (Postgres)");
         UIManager.put("OptionPane.noButtonText", "Conectar  com Banco-de-dados (MySQL)");
         UIManager.put("OptionPane.CancelButtonText", "Desistir");
         
         int escolha = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Conectar com o Banco-de-dados agora?", "Conectar com o banco-de-dados", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
         
        switch (escolha) {
            case JOptionPane.YES_OPTION:
                driver = "org.postgresql.Driver";
                conf.setDriver(driver);
                break;
            case JOptionPane.NO_OPTION:
                driver = "com.mysql.cj.jdbc.Driver";
                conf.setDriver(driver);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Você escolheu Desistir de Conectar com o Banco-de-dados!");
                System.exit(0);
        }
                      
            conf.conectar();
            conf.criarBancoDeDadosUI();
            
        /*XMLReaderConfig xr = new XMLReaderConfig();
                        xr.trataXML( Utils.raiz+ "config.xml");
        */
        
        
//        criarBancoDados();
        

}
        

    

    
}
