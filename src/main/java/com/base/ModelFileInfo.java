package com.base;

import java.io.InputStream;

/**
 * 
 * 文件model
 * @author huage
 *
 */
public class ModelFileInfo {
	private InputStream inputStream;//文件流
	private String suffix;//后缀
	private String filename;//文件名称
	private String key;
	private String filePath;//全文件路径(包含文件名和后缀)
	private String name;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
