package org.open.services;

import javax.ejb.Stateless;

@Stateless
public interface HelloService {

	public String sayHello();

	public String helloFromOther();
	
}
