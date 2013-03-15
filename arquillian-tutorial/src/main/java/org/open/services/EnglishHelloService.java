package org.open.services;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.naming.InitialContext;
import javax.naming.NamingException;


@Stateless
@EJB(beanInterface = HelloService.class, beanName = "SpanishHelloService", name = "myBeanName")
@Alternative
public class EnglishHelloService implements HelloService {

	@Override
	public String sayHello() {
		return "Hello!";
	}
	
	public String helloFromOther() {
		try {
			HelloService helloService = (HelloService) new InitialContext().lookup("java:comp/env/myBeanName");
			return "Otra forma de decir: " + helloService.sayHello();
		} catch (NamingException e) {
			throw new EJBException(e);
		}
	}

}
