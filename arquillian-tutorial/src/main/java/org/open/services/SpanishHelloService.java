package org.open.services;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateless
@EJB(beanInterface = HelloService.class, beanName = "EnglishHelloService", name = "myBeanName")
@Default
public class SpanishHelloService implements HelloService {

	@Override
	public String sayHello() {
		return "Hola!";
	}
	
	public String helloFromOther() {
		try {
			HelloService helloService = (HelloService) new InitialContext().lookup("java:comp/env/myBeanName");
			return "Other hello way: " + helloService.sayHello();
		} catch (NamingException e) {
			throw new EJBException(e);
		}
	}

}
