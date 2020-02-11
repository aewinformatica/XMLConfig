/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aewinformatica;

import com.aewinformatica.utilitario.Utils;
import com.aewinformatica.utilitario.XMLReaderConfig;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Jessica
 */
public class ConfigProjeto {
    
    private static String driver = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*XMLReaderConfig xr = new XMLReaderConfig();
                        xr.trataXML( Utils.raiz+ "config.xml");
        */
        
         UIManager.put("OptionPane.yesButtonText", "Criar Banco-de-dados (Postgres)");
         UIManager.put("OptionPane.noButtonText", "Criar Banco-de-dados (MySQL)");
         
         UIManager.put("OptionPane.CancelButtonText", "Desistir");
         int escolha = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Criar o Banco-de-dados agora?", "Criação do banco-de-dados", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
         
        switch (escolha) {
            case JOptionPane.YES_OPTION:
                driver = "org.postgresql.Driver";
                Utils.criarBancoDeDados(driver);
                break;
            case JOptionPane.NO_OPTION:
                driver = "com.mysql.cj.jdbc.Driver";
                Utils.criarBancoDeDados(driver);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Você escolheu Desistir e o Banco-de-dados não foi criado!"
                        + "\nVocê pode criar o banco-de-dados manualmente através do gerenciador de banco-de-dados.");
                System.exit(0);
        }
        

    }
    
}
