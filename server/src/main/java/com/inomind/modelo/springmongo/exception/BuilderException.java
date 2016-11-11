package com.inomind.modelo.springmongo.exception;

/**
 * @author vinicius
 */
public class BuilderException extends RuntimeException {

	/**	 */
	private static final long serialVersionUID = 4056434505045491492L;
	
	private String msg;
	
	public BuilderException(Class<?> builderClass, String msg) throws NullPointerException {
		this.msg = "[$1] was not able to build a new instance due to: $2";
		this.msg = this.msg.replace("$1", builderClass.getSimpleName()).replace("$2", msg);
	}

	@Override
	public String getMessage() {
		return this.msg;
	}
}