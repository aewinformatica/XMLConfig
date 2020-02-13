/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aewinformatica.model;

/**
 *
 * @author Jessica
 */
public class ModelSegmento {
    
    private Long codigo;
    private String nome;
    private Integer codigo_segmento;

    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the codigo_segmento
     */
    public Integer getCodigo_segmento() {
        return codigo_segmento;
    }

    /**
     * @param codigo_segmento the codigo_segmento to set
     */
    public void setCodigo_segmento(Integer codigo_segmento) {
        this.codigo_segmento = codigo_segmento;
    }
    
    
    
}
