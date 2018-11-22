package br.com.os.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Montadora extends GenericDomain {
	
	   @Column(nullable = false,length = 60 )
	    private String descricao;
	   
	   public String getDescricao() {
		return descricao;
	}
	   public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
