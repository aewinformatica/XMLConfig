/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aewinformatica.utilitario;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Jessica
 */
public class XMLReaderConfig {
    
    private String y_localsgbd = "", y_driver = "", y_url = "", y_usuarioBD = "", y_senhaBD = "", y_drive = "", 
                   y_nomebd, y_contato = "AewInformatica Ltda - aewinformatica@gmail.com.br";

    
    public void trataXML(String arquivoXML){
        
        File file = new File(arquivoXML);
        
//        parser para analisar o XML
        SAXBuilder builder = new SAXBuilder();
        
//        utilizado para processar a estrutura do documento para dentro de uma variável do tipo Document
        Document document;
        
      try{
            //O método utilizado para processar o XML é o build, que recebe como parâmetro o caminho do arquivo
            document = builder.build(file);
            Element el = document.getRootElement();
            
            trataElemento(el);
            
            gravaSaidaTeste(document);
            
         } catch(IOException | JDOMException e){
            JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo conexao.xml. \nErro: "+e);
        }
    }
private void trataElemento(Element el){
        List list = el.getChildren();
        int tamanho = list.size();
       
//        System.out.println("Elemento lido: \n "+el.getName()+"\n - tipo: \n"+el.getContent());
//        System.out.println("Tamanho da lista: "+tamanho);
        
        
        
        if (tamanho > 0){  //Elemento
            Iterator it = list.iterator();
            for (int i=0;i<tamanho;i++){
                Element el1 = (Element)it.next();
                String campo = el1.getQualifiedName().trim();
                
                
                
//                System.out.println("Elemento: "+el1.getQualifiedName().trim()+" - Valor: "+el1.getText());
               
                if (campo.equalsIgnoreCase(("localsgbd"))){
                    setY_localsgbd(el1.getText());
                } else
                if (campo.equalsIgnoreCase(("driver"))){
                    setY_driver(el1.getText());
                } else
                if (campo.equalsIgnoreCase(("url"))){
                    setY_url(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("usuariobd"))){
                    setY_usuarioBD(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("senhabd"))){
                    setY_senhaBD(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("drive"))){
                    setY_drive(el1.getText().toString());
                } else
                if (campo.equalsIgnoreCase(("nomebd"))){
                    setY_nomebd(el1.getText().toString());
                }
                else
                if (campo.equalsIgnoreCase(("contato"))){
                    setY_contato(el1.getText().toString());
                } 
            }
        } else {
        }
//        JOptionPane.showMessageDialog(null,"Metodo XMLReaderConfig().trataElemento() \nLocal SGBD: "+getY_localsgbd()+"\nDriver: "+getY_driver()+"\nUsuarioBD: "+getY_usuarioBD()+"\nSenhaBD: "+getY_senhaBD()+"\nDriver: "+getY_drive());
}
    private void gravaSaidaTeste(Document doc) {
		XMLOutputter xmlOutput = new XMLOutputter();

		// display nice nice
		xmlOutput.setFormat(Format.getPrettyFormat());
        try {
            xmlOutput.output(doc, new FileWriter(Utils.raiz + "Saida.xml"));
        } catch (IOException ex) {
            Logger.getLogger(XMLReaderConfig.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

  public static void main(String[] args){
        XMLReaderConfig xr = new XMLReaderConfig();
        
        xr.trataXML( Utils.raiz+ "config.xml");
    }

    /**
     * @return the y_localsgbd
     */
    public String getY_localsgbd() {
        return y_localsgbd;
    }

    /**
     * @param y_localsgbd the y_localsgbd to set
     */
    public void setY_localsgbd(String y_localsgbd) {
        this.y_localsgbd = y_localsgbd;
    }

    /**
     * @return the y_driver
     */
    public String getY_driver() {
        return y_driver;
    }

    /**
     * @param y_driver the y_driver to set
     */
    public void setY_driver(String y_driver) {
        this.y_driver = y_driver;
    }

    /**
     * @return the y_url
     */
    public String getY_url() {
        return y_url;
    }

    /**
     * @param y_url the y_url to set
     */
    public void setY_url(String y_url) {
        this.y_url = y_url;
    }

    /**
     * @return the y_usuarioBD
     */
    public String getY_usuarioBD() {
        return y_usuarioBD;
    }

    /**
     * @param y_usuarioBD the y_usuarioBD to set
     */
    public void setY_usuarioBD(String y_usuarioBD) {
        this.y_usuarioBD = y_usuarioBD;
    }

    /**
     * @return the y_senhaBD
     */
    public String getY_senhaBD() {
        return y_senhaBD;
    }

    /**
     * @param y_senhaBD the y_senhaBD to set
     */
    public void setY_senhaBD(String y_senhaBD) {
        this.y_senhaBD = y_senhaBD;
    }

    /**
     * @return the y_drive
     */
    public String getY_drive() {
        return y_drive;
    }

    /**
     * @param y_drive the y_drive to set
     */
    public void setY_drive(String y_drive) {
        this.y_drive = y_drive;
    }

    /**
     * @return the y_nomebd
     */
    public String getY_nomebd() {
        return y_nomebd;
    }

    /**
     * @param y_nomebd the y_nomebd to set
     */
    public void setY_nomebd(String y_nomebd) {
        this.y_nomebd = y_nomebd;
    }    

    /**
     * @return the y_contato
     */
    public String getY_contato() {
        return y_contato;
    }

    /**
     * @param y_contato the y_contato to set
     */
    public void setY_contato(String y_contato) {
        this.y_contato = y_contato;
    }
}
