/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aewinformatica.utilitario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;
import javax.swing.JOptionPane;

/**
 *
 * @author Jessica
 */
public class Utils {
    public static final String fileSeparator = System.getProperty("file.separator");
    public static final String raiz = System.getProperty("user.dir") + fileSeparator;
    private static final String TIMEZONE = "?allowPublicKeyRetrieval=true&useSSL=false&useTimezone=true&serverTimezone=" + TimeZone.getDefault().getID();
    
    private static Connection conexao = null;  //variavel para conexao
    private static String usuario = "root";
    private static String senha = "testeabc";
    private static String servidor = "localhost";
    private static String nomeDoBanco = "aewinformatica";
    private static String tipoConexao = "jdbc:mysql:";
//    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = tipoConexao + "//" + servidor + "/" + nomeDoBanco;
    
    public static void criarBancoDeDados(String driver) {
           try {
                Class.forName(driver);
                if (driver.indexOf("postgres") != -1) {
                    url = url.replaceAll("jdbc:mysql:", "jdbc:postgresql:");
                    url = url.replaceAll("aewinformatica", "postgres");
                    usuario = usuario.replaceAll("root", "postgres");
                } else 
                if (driver.indexOf("mysql") != -1) {
                    
                    url = url.replaceAll("aewinformatica", "mysql");
                    url  = url + TIMEZONE;
                }
                
                JOptionPane.showMessageDialog(null,"Pressione [ OK ] e aguarde alguns instantes para o sistema criar o Banco-de-dados: " + nomeDoBanco +" para você!");
                conexao = DriverManager.getConnection(url, usuario, senha);
                Statement s = conexao.createStatement();
                s.executeUpdate("CREATE DATABASE aewinformatica");
                s.close();
                
               JOptionPane.showMessageDialog(null,"O banco de dados foi criado e o aplicativo será fechado agora!"
                       + "\nEntre novamente para criar as tabelas automaticamente! ");
               System.exit(0);
           }
            catch(ClassNotFoundException Driver) 
            {
               JOptionPane.showMessageDialog(null,"Driver nao localizado: "+Driver);
            }
            catch(SQLException Fonte) 
            {
                JOptionPane.showMessageDialog(null,"Deu erro na conexao "+
                        "com a fonte de dados!\n Verifique se o local ["+url+"] esta correto!"+
                        "\nErro: "+Fonte.getMessage());
                System.out.println("Deu erro na conexao com a fonte de dados: "+Fonte);
            }
            catch(Exception e) 
            {
                JOptionPane.showMessageDialog(null,"Deu erro na conexao: "+e);
            }
       }
}
