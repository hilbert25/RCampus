package org.sunhp.rcampus.components;

/**
 * 常量
 * 
 * @author fuyufeng
 * 
 */
public final class Constants {

	private Constants() {
	}
	
	public static final String NEGATIVE_NUMBER = "-1";
	
	public static final String POINT = ".";

	/**
	 * insert
	 */
	public static final String INSERT = "insert";

	/**
	 * delete
	 */
	public static final String DELETE = "deleteByPrimaryKey";

	/**
	 * update
	 */
	public static final String UPDATE = "updateByPrimaryKeySelective";

	/**
	 * select
	 */
	public static final String SELECT = "selectByPrimaryKey";

	/**
	 * selectAll
	 */
	public static final String SELECT_ALL = "selectAll";

	/**
	 * 分页查询
	 */
	public static final String FIND_PAGE = "findPage";

	/**
	 * 记录条数
	 */
	public static final String COUNT = "count";
	/**
	 * 出现最多的属性值
	 */
	public static final String SELECTMOSTATTRIBUTE = "selectMostAttribute";
	
	/**
	 * 初始化
	 */
	public static final String DELETEALL = "deleteAll";
	
	public static String UPLOAD_PATH = "file/";

	/**
	 * openCPU地址
	 */
//	public static final String OPENCPU_HOST = "http://10.211.55.11";
	public static final String OPENCPU_HOST = "http://103.253.146.183";
	/**
	 * openCPU地址
	 */
	public static final String OPENCPU_NAME = "ocpu";
}
