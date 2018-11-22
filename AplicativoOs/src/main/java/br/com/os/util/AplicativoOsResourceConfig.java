package br.com.os.util;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

//http://localhost:8080/LojaVirtual/rest
@ApplicationPath("rest")
public class AplicativoOsResourceConfig extends ResourceConfig {
	public AplicativoOsResourceConfig(){
		packages("br.com.loja.service");
	}
}
