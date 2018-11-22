package br.com.os.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class ContasReceber extends GenericDomain {

	@ManyToOne
	@JoinColumn(nullable = false)
	private Os os;

	public Os getOs() {
		return os;
	}

	public void setOs(Os os) {
		this.os = os;
	}

	@Override
	public String toString() {
		return "ContasReceber [os=" + os + ", getOs()=" + getOs() + ", getCodigo()=" + getCodigo() + ", toString()="
				+ super.toString() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + "]";
	}
	
	

}
