/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aewinformatica.utilitario.csv;

import com.aewinformatica.model.ModelSegmento;
import com.aewinformatica.utilitario.Conexao;
import com.aewinformatica.utilitario.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jessica
 */
public class ImportaSegmentos extends Conexao {
    
        String sql  = "";
    	private Scanner input;
        ModelSegmento reg;
        int codigo = 0;
        private int qtregi;

//    public ImportaSegmentos() {
//         
//    }
    
    public void ImportaArquivo()
	{
            conectar();
            processaSegmentos();
            fechaArquivo();
	}
    
        public void processaSegmentos()
        {
            
		String arqCSV = Utils.raiz+"segmentos.csv";
//		JOptionPane.showMessageDialog(null, "Nome do arquivo texto: " + arqcsv );
//		System.out.println("Nome do arquivo texto: " + arqcsv );
		
		try
		{
                    input = new Scanner(new File(arqCSV),"ISO-8859-1");
                    leRegistros();
		}
		catch (FileNotFoundException e)
		{
                    JOptionPane.showMessageDialog(null, "Não encontrou o arquivo: " + arqCSV + " e a Importação de Segmentos não será efetuada!");
		}
            
        }
        
	public void leRegistros(){
            
            try {
                // cria o statement para a conexao
                this.setStatement(getConexao().createStatement());
                sql = "select * from segmento";
                getStatement().executeQuery(sql);

            } catch (SQLException ex) {
                Logger.getLogger(ImportaSegmentos.class.getName()).log(Level.SEVERE, null, ex);
            }
     	
            reg = new ModelSegmento();
            int qtReg = 0;
            try
            {
                String frase = input.nextLine();
		while ( input.hasNextLine())
		{
                    frase = input.nextLine();
                    String delimita = ";";
                    qtReg++;
                    
                    //System.out.println("qtReg: "+qtReg);

                    StringTokenizer token = new StringTokenizer( frase , delimita);
                    int cpo = 0;
                    while ( token.hasMoreTokens() )
                    {
                        cpo++;
                        String wconteudo = token.nextToken();

                        switch (cpo%4)
                        {
                                case 1: 
                                    reg.setCodigo(Long.parseLong(wconteudo)); 
                                    System.out.print("Codigo: "+reg.getCodigo()+" ");
                                    break;
                                case 2: 
                                    reg.setNome(wconteudo.toUpperCase().trim()); 
                                    System.out.print("NOME: "+reg.getNome()+" ");
                                    break;
                                case 3: 
                                    reg.setCodigo_segmento(Integer.parseInt(wconteudo)); 
                                    System.out.println("CODIGO DO SEGMENTO: "+reg.getCodigo_segmento() +" ");
                                    insereRegistro();
                                    break;
                    	}
                    }
                }

                JOptionPane.showMessageDialog(null, "Foram Importados " + qtregi + " Registros de segmento.csv com Sucesso!");
	    }
            catch( NoSuchElementException e)
            {
            	System.err.println("Erro de tipo de dado incompativel!");
		System.exit(1);
            }
            catch (IllegalStateException e)
            {
            	System.err.println("Erro ao ler o arquivo!");
		System.exit(1);
            }
        }    
 		
        public void insereRegistro(){
	//  Inclusao do registro no banco de dados
        
        try
        {
                // cria o statement para a conexao
                this.setStatement(getConexao().createStatement());
                
                    
                    sql = "select * from segmento where codigo = "+reg.getCodigo();
                    
//                    Passando os dados da Execução da Query 
                    setResultSet( getStatement().executeQuery(sql));
                    
//                    verificando se ouve algum dado recebido
                    if (!getResultSet().first()){
                        
                        sql = "select max(codigo) as ultCod from segmento";
                        setResultSet( getStatement().executeQuery(sql));
                        while (getResultSet().next()) {
                            codigo = getResultSet().getInt("ultCod");
                        }
                        codigo++;
                        sql = "INSERT INTO segmento ("
                            + "codigo, "
                            + "nome, "
                            + "codigo_segmento "+
                            ") " 
                            +"values ("
                            + "'" + reg.getCodigo()+ "'" + ", "
                            + "'" + reg.getNome()+ "'" + ","
                            + "'" + reg.getCodigo_segmento()+ "'"
                            + ")";
                        
                        //System.out.println("SQL: "+sql);
                        getStatement().executeUpdate(sql);
                        
                        qtregi = qtregi + 1;

                    }
                }
                catch (SQLException erro)
                {
                    JOptionPane.showMessageDialog(null,"Erro a tentar Gravar o registro com codigo: "+reg.getCodigo()+" - NOME: "+reg.getNome()+" - Erro: "+erro);
                }
		
        }
        public void fechaArquivo()
	{
		if (input != null)
			input.close();
	}
	// Teste da classe
	public static void main( String args[] )
	{
		ImportaSegmentos leSegmento = new ImportaSegmentos();
		leSegmento.ImportaArquivo();
		//leSegmento.leRegistros();
		//leSegmento.fechaArquivo();
	}
        
        
}
