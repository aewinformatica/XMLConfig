/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aewinformatica.utilitario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Jessica
 */
public class Conexao {

    private boolean status = false;
    private String mensagem = "";   //variavel que vai informar o status da conexao
    private Connection conexao = null;  //variavel para conexao
    private Statement statement;
    private ResultSet resultSet;

    private static String usuario = "root";
    private static String senha = "testeabc";
    private static String servidor = "localhost";
    private static String nomeDoBanco = "aewinformatica";
    private static String tipoConexao = "jdbc:mysql:";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = tipoConexao + "//" + servidor + "/" + nomeDoBanco;
    private static final String TIMEZONE = "?allowPublicKeyRetrieval=true&useSSL=false&useTimezone=true&serverTimezone=" + TimeZone.getDefault().getID();

    public Conexao() {
        
    }

    public Conexao(String pServidor, String pNomeDoBanco, String pUsuario, String pSenha) {
        this.servidor = pServidor;
        this.nomeDoBanco = pNomeDoBanco;
        this.usuario = pUsuario;
        this.senha = pSenha;
    }

    /**
     * Abre uma conexao com o banco
     *
     * @return Connection
     */
    public Connection conectar() {
        try {
            
            //Driver para conexão
            Class.forName(getDriver()).newInstance();

             if (driver.contains("postgres")) {
                    url = url.replaceAll("jdbc:mysql:", "jdbc:postgresql:");
                    url = url.replaceAll("aewinformatica", "postgres");
                    usuario = usuario.replaceAll("root", "postgres");
            }
             else 
                if (driver.contains("mysql")) {
                    
                    url = url.replaceAll("aewinformatica", "mysql");
                    url  = url + TIMEZONE;
                }
            this.setConexao((Connection) DriverManager.getConnection(url, usuario, senha));

            //se ocorrer tudo bem, ou seja, se conectar a linha a segui é executada
            this.status = true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Conectar ao Banco de dados " + e.toString());
        }
        return this.getConexao();
    }
    
        public  void criarBancoDeDados(String driver) {
           try {
                Class.forName(driver);
                if (driver.contains("postgres")) {
                    url = url.replaceAll("jdbc:mysql:", "jdbc:postgresql:");
                    url = url.replaceAll("aewinformatica", "postgres");
                    usuario = usuario.replaceAll("root", "postgres");
                } else 
                if (driver.contains("mysql")) {
                    
                    url = url.replaceAll("aewinformatica", "mysql");
                    url  = url + TIMEZONE;
                }
                
                JOptionPane.showMessageDialog(null,"Pressione [ OK ] e aguarde alguns instantes para o sistema criar o Banco-de-dados: " + nomeDoBanco +" para você!");

            //createStatement de con para criar o Statement
            this.setStatement(getConexao().createStatement());
                 getStatement().executeUpdate("CREATE DATABASE aewinformatica");
                 this.statement.close();
                
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
    
    public  void criarBancoDeDadosUI(){
         UIManager.put("OptionPane.yesButtonText", "Criar Banco-de-dados (Postgres)");
         UIManager.put("OptionPane.noButtonText", "Criar Banco-de-dados (MySQL)");
         
         UIManager.put("OptionPane.CancelButtonText", "Desistir");
         int escolha = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja Criar o Banco-de-dados agora?", "Criação do banco-de-dados", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
         
        switch (escolha) {
            case JOptionPane.YES_OPTION:
                driver = "org.postgresql.Driver";
                criarBancoDeDados(driver);
                break;
            case JOptionPane.NO_OPTION:
                driver = "com.mysql.cj.jdbc.Driver";
                criarBancoDeDados(driver);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Você escolheu Desistir e o Banco-de-dados não foi criado!"
                        + "\nVocê pode criar o banco-de-dados manualmente através do gerenciador de banco-de-dados.");
                System.exit(0);
        }
    
    }

    /**
     * @return the conexao
     */
    public Connection getConexao() {
        return conexao;
    }

    /**
     * @param conexao the conexao to set
     */
    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * @return the driver
     */
    public  String getDriver() {
        return driver;
    }

    /**
     * @param aDriver the driver to set
     */
    public  void setDriver(String aDriver) {
        driver = aDriver;
    }

    /**
     * @return the statement
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * @param statement the statement to set
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * @return the resultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * @param resultSet the resultSet to set
     */
    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

}
