package org.open.services;

import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HelloServiceTest {

	private static final String JNDI_NAME = "java:module/SpanishHelloService!org.open.services.HelloService";
	private static final int LOOKUP_TRIES = 1000;
	private long difference;
	private long total;
	private long max;
	private long min;
	private int indexMax;
	private int indexMin;
	
//	@EJB(lookup = "java:module/EnglishHelloService!org.open.services.HelloService")
//	@EJB(lookup = JNDI_NAME)
	private HelloService helloService;
	private Context context;
	
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive beansFile = ShrinkWrap.create(JavaArchive.class)
	            .addClasses(HelloService.class, SpanishHelloService.class, EnglishHelloService.class)
	            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		System.out.println(beansFile);
		return beansFile;
	}
	
//	@Test
//	public void testEnglish() throws Exception {
//		Assert.assertNotNull(helloService);
//        Assert.assertEquals("Hello!", helloService.sayHello());
//        Assert.assertEquals("Otra forma de decir: Hola!", helloService.helloFromOther());
//    }

	@Before
	public void setUp() {
		difference = 0L;
		total = 0L;
		max = 0L;
		min = Long.MAX_VALUE;
		indexMax = 0;
		indexMin = 0;
		setContext();
	}
	
	private void setContext() {
		Hashtable<String, Object> jndiProperties = new Hashtable<String, Object>();
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

		context = null;
		try {
			context = new InitialContext(jndiProperties);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
//	@Test
//    public void testSpanish() throws Exception {
//		Assert.assertNotNull(helloService);
//		Assert.assertEquals("Hola!", helloService.sayHello());
//		Assert.assertEquals("Other hello way: Hello!", helloService.helloFromOther());
//	}
	
		
	@Test
	public void testLookUp() throws Exception {
		System.out.println("LookUp Test");
		try {
			for (int i = 0; i < LOOKUP_TRIES; i++) {
				difference = (new Date()).getTime();
				
				context.lookup(JNDI_NAME);
				
				difference = (new Date()).getTime() - difference;

				total += difference;
				if (difference > max) {
					max = difference;
					indexMax = i;
				}

				if (difference < min) {
					min = difference;
					indexMin = i;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("AVG=" + ((double) total / LOOKUP_TRIES) + " . Total=" + total + " . [Max=" + max + " . Index="
				+ indexMax + "] . [Min=" + min + " . Index=" + indexMin + "]");
		

    }
	
}
