package com.aewinformatica.utilitario;

import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Jessica
 */
public class Utils {
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final String raiz = System.getProperty("user.dir") + fileSeparator;
    
    public static boolean ExistenciaArquivo(String pArquivo) {
           boolean retorno = true;
        try {
            File arquivo = new File(pArquivo);
            if (!arquivo.exists()) {
            JOptionPane.showMessageDialog(null, "Não encontrou o arquivo: " + pArquivo + "\nCopie o arquivo do googledrive para a pasta: " + raiz + " e tente novamente!"
                    + "\nSe o problema persistir, favor pedir ajuda ao suporte!!");
            retorno = false;
            }
        } 
	catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar a existencia do arquivo: " + pArquivo + ".\nErro: " + ex);
            retorno = false;
        }
        return retorno;
       }
    
   public static void VerificaCFOPeIBGE(){
     String arqCSV;
     boolean existeArquivo;
   
       //            verificar o CSV do IBGE
            arqCSV = Utils.raiz + "ibge.csv";
            existeArquivo = ExistenciaArquivo(arqCSV);
            
       //            verificar o CSV do CFOP
            arqCSV = Utils.raiz + "cfop.csv";
            existeArquivo = ExistenciaArquivo(arqCSV);
            
      // Fechar o Aplicativo caso não exista os CSV
            if(!existeArquivo){
            System.exit(0);
            }
   } 
}
