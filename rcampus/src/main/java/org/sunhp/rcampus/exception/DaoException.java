package org.sunhp.rcampus.exception;

/**
 * 数据访问层异常
 * 
 * @author fuyufeng
 * 
 */
public class DaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3679480724623190204L;

	/**
	 * 无参数
	 */
	public DaoException() {
		super();
	}

	/**
	 * 异常信息以及异常链的构造方法
	 * 
	 * @param message
	 * @param cause
	 */
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 异常信息构造方法
	 * 
	 * @param message
	 */
	public DaoException(String message) {
		super(message);
	}

	/**
	 * 异常链
	 * 
	 * @param cause
	 */
	public DaoException(Throwable cause) {
		super(cause);
	}

}
