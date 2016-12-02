package com.base;

import org.apache.commons.lang.StringUtils;

public class Enumeration extends BaseObject {
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	public Enumeration(){
		
	}
	public Enumeration(String code) {
		this(code, null);
	}
	public Enumeration(String code,String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int hashCode() {
		return code==null?super.hashCode():code.hashCode();
	}
	public boolean equals(Object other) {
		if(other==null) {
			return false;
		}
		if(other == this) {
			return true;
		}
		if(StringUtils.isEmpty(code)) {
			return false;
		}
		if(other instanceof String) {
			return code.equals(other);
		}
		/*因为字节码增强的关系，getClass()不能用作判断的依据*/
		if(getClass().getPackage()!=other.getClass().getPackage()) {
			return false;
		}
		if(hashCode()==other.hashCode()) {
			return true;
		}
		return false;
	}
	
}
