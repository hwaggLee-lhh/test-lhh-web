package com.base;

import java.util.LinkedHashMap;
/**
 * 抽象字典元素
 * @author yejia
 *
 */
public class Dictionary extends BaseObject {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	/**元素容器，LinkedHashMap是为了保证顺序*/
	private LinkedHashMap<String, Enumeration> enumMap = new LinkedHashMap<String, Enumeration>();
	
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
	public Dictionary() {
		
	}
	public Dictionary(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getEnumName(String enumCode) {
		Enumeration enumeration = getEnumeration(enumCode);
		if(enumeration==null) {
			return null;
		}
		return enumeration.getName();
	}
	public Enumeration getEnumeration(String key) {
		return enumMap.get(key);
	}
	public void addEnum(Enumeration enums) {
		if(enums==null) return;
		enumMap.put(enums.getCode(), enums);
	}

    /**
     * 设置字典中的元素
     * @param key
     * @param enumeration
     */
    public void addEnum(String key, Enumeration enumeration) {
        enumMap.put(key, enumeration);
    }

    public void addEnum(String enumCode, String enumName) {
        enumMap.put(enumCode, new Enumeration(enumCode, enumName));
    }
    
    public void remove(String enumCode) {
        enumMap.remove(enumCode);
    }

    public LinkedHashMap<String, Enumeration> getEnumMap() {
        return enumMap;
    }

    public void setEnumMap(LinkedHashMap<String, Enumeration> enumMap) {
        this.enumMap = enumMap;
    }

    public Object clone() {
        Dictionary dictionary = (Dictionary) super.clone();
        if (enumMap != null) {
            dictionary.setEnumMap(enumMap);
        }
        return dictionary;
    }
}
