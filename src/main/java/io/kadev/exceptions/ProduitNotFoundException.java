package io.kadev.exceptions;

public class ProduitNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ProduitNotFoundException(String message) {
		super(message);
	}
	
}
