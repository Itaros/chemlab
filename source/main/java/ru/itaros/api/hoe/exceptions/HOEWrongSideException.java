package ru.itaros.api.hoe.exceptions;

/*
 * This is an indication of severe programming error!
 * You tried to call HOE from client-side, but this is not right way.
 * For client side you use standard ticking model to provide smooth experience,
 * while keeping server threadstepped. For exemplar use check ChemLab mod. Thank you! xD
 */
public class HOEWrongSideException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7392343122144143257L;

}
