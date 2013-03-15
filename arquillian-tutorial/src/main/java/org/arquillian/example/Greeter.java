package org.arquillian.example;

import java.io.PrintStream;

import javax.inject.Inject;


public class Greeter {

	private PhraseBuilder phraseBuilder;
	
	@Inject
	public Greeter(PhraseBuilder phraseBuilder) {
		System.out.println(this.getClass().getSimpleName() + " - Not instatiated");
		this.phraseBuilder = phraseBuilder; 
		System.out.println(this.getClass().getSimpleName() + " - Instantiated");
	}
	
	public void greet(PrintStream to, String name) {
		to.println(createGreeting(name));
	}

	public String createGreeting(String name) {
//		return "Hello, " + name + "!";
		return phraseBuilder.buildPhrase("hello", name);
	}
}
