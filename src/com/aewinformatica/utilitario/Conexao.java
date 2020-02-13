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
                    usuario = usuario.replaceAll("root", "postgres");
            }
             else 
                if (driver.contains("mysql")) {
                   
                    url  = url + TIMEZONE;
                }
            this.setConexao((Connection) DriverManager.getConnection(url, getUsuario(), senha));

            //se ocorrer tudo bem, ou seja, se conectar a linha a segui é executada
            this.status = true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao Conectar ao Banco de dados " + e.toString());
        }
        return this.getConexao();
    }
    
        public  void criarBancoDeDados(String pDriver) {
           try {
                Class.forName(pDriver);
                if (pDriver.contains("postgres")) {
                    url = url.replaceAll("jdbc:mysql:", "jdbc:postgresql:");
                    url = url.replaceAll("aewinformatica", "postgres");
                    setUsuario(getUsuario().replaceAll("root", "postgres"));
                } else 
                if (pDriver.contains("mysql")) {
                    
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
    public int VerificarTabelas(){
     String sql  = "";
     int retorno = 0,nTab = 0,passou = 0;

      
        try {
            //createStatement de conexao para criar o Statement
            this.setStatement(getConexao().createStatement());
            
            sql = "select * from cfop limit 1";
            
                 getStatement().executeQuery(sql);
               
                 this.statement.close(); 
        } catch (SQLException e) {
            
//            Verificar CSVs IBGE e CFOP
            Utils.VerificaCFOPeIBGE();
        }
        
        passou++;
        
        try {
            //createStatement de conexao para criar o Statement
            this.setStatement(getConexao().createStatement());
            nTab++;
            
            sql = "CREATE TABLE cfop ( "
                  +"codigo integer NOT NULL, "
                  +"cfop integer, "
                  +"descricao character varying(120), "
                  +"observacao character varying(120), "
                  +"faturamento boolean NOT NULL, "
                  +"financeiro boolean, "
                  +"seqcfop integer, "
                  +"operacao character(1), "
                  +"CONSTRAINT cfop_pkey PRIMARY KEY (codigo) "
                  +")";// WITH ( OIDS=FALSE )";
            
           retorno =  getStatement().executeUpdate(sql);
           
           nTab++;
           passou++;
           
           sql = "CREATE TABLE ibge ( "
                  +"codigo integer NOT NULL, "
                  +"cidade character varying(40), "
                  +"codcidade integer, "
                  +"distrito character varying(40), "
                  +"uf character varying(2), "
                  +"CONSTRAINT ibge_pkey PRIMARY KEY (codigo) "
                  +")";// WITH ( OIDS=FALSE )";
           
            retorno =  getStatement().executeUpdate(sql);
//                    executarUpdateDeleteSQL(sql);
           
           nTab++;
           passou++;
           
           sql = "CREATE TABLE segmento ( "
                  +"codigo SERIAL NOT NULL, "
                  +"nome character varying(1024), "
                  +"codigo_segmento integer, "
                  +"CONSTRAINT segmento_pkey PRIMARY KEY (codigo) "
                  +")";//WITH ( OIDS=FALSE )";
           
            retorno = getStatement().executeUpdate(sql);
            
           nTab++;
           passou++;
           
           sql = "CREATE TABLE item_segmento ( "
                  +"codigo SERIAL NOT NULL, "
                  +"item_codigo character varying(1024) , "
                  +"cest character varying(1024) , "
                  +"ncm_sh character varying(1024), "
                  +"descricao character varying(1024),"
                  +"CONSTRAINT item_segmento_pk PRIMARY KEY (codigo) "
                  +")";//WITH ( OIDS=FALSE )";
           
           retorno = getStatement().executeUpdate(sql);
           
            nTab++;
            passou++;
           
                    
           
        } catch (SQLException e) {
        }
        
         return retorno;
    }
    
        public boolean executarUpdateDeleteSQL(String pSQL){
        try {
            
            //createStatement de con para criar o Statement
            this.setStatement(getConexao().createStatement());

            // Definido o Statement, executamos a query no banco de dados
            getStatement().executeUpdate(pSQL);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
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

    /**
     * @return the usuario
     */
    public static String getUsuario() {
        return usuario;
    }

    /**
     * @param aUsuario the usuario to set
     */
    public  void setUsuario(String aUsuario) {
        usuario = aUsuario;
    }

}
