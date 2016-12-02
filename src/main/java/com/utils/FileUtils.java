package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class FileUtils {

	public static void main(String[] args) throws Exception {
		String y = "D:\\Program Files\\maven\\Respositories\\maven\\com\\qinghuainvest\\n3b_db\\0.0.1-SNAPSHOT\\n3b_db-0.0.1-SNAPSHOT\\com";
		List<String> list = readChildFiles(new File(y));
		String p = "D:\\Workspace\\eclipse\\eclipse-birt-2.2.0\\workspace\\n3b_yjdb\\src\\main\\java\\com";
		for (String string : list) {
			//System.out.println(string.replace(y, p));
			String newname = string;
			if( string.endsWith(".class")){
				newname = string.replace(".class", ".java");
			}
			newname = newname.replace(y, p);
			renameFile(string, newname);
		}
		System.out.println("完成");
		
	}

	/**
	 * 文件重命名
	 * @param oldname:原来的文件名
	 * @param newname:新文件名
	 */
	public static void renameFile(String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File( oldname);
			File newfile = new File( newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if( !newfile.exists()){
				(new File(newfile.getParent())).mkdirs();
				try {
					newfile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				System.out.println(newname + "已经存在！"+oldname);
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			System.out.println("新文件名和旧文件名相同..."+oldname);
		}
	}

	/**
	 * 修改文件名称或者目录名称
	 * 
	 * @param oldFile
	 *            :旧文件或目录
	 * @param newsFilename
	 *            :新文件名称
	 * @param isPx
	 *            :如果是文件，是否使用旧文件的后缀,true为使用旧文件后缀，如果是目录则固定值false
	 * @throws Exception
	 */
	public static void updateFileName(File oldFile, String newsFilename,
			boolean isPx) throws Exception {
		if (oldFile == null)
			return;
		String newPath = oldFile.getPath().replace(oldFile.getName(), "")
				+ newsFilename;
		if (oldFile.isDirectory()) {// 目录
			String name = oldFile.getName();
			if (name.lastIndexOf(".") > -1) {
				newPath += name.substring(name.lastIndexOf("."));
			}
		} else {// 文件
			if (isPx) {
				String name = oldFile.getName();
				if (name.lastIndexOf(".") > -1) {
					newPath += name.substring(name.lastIndexOf("."));
				}
			}
		}
		File fn = findOrCreateFile(newPath);
		oldFile.renameTo(fn);
	}

	/**
	 * 读取文件夹的所有文件（包含所有子文件）
	 * 
	 * @param file
	 * @return
	 */
	public static List<String> readChildFiles(File file) {
		if (file == null)
			return null;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			List<String> fileList = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				readChildFiles(files[i], fileList);
			}
			return fileList;
		}
		return null;
	}

	/**
	 * 读取文件目录，包括文件夹中的子文件
	 * 
	 * @param f
	 * @param fileList
	 */
	private static void readChildFiles(File f, List<String> fileList) {
		if (f == null)
			return;
		if (fileList == null)
			fileList = new ArrayList<String>();
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				readChildFiles(files[i], fileList);
			}
		} else {
			fileList.add(f.getAbsolutePath());
		}
	}

	/**
	 * 在文件的最后追加内容
	 * 
	 * @param file
	 * @param conent
	 */
	public static void writerEndAdd(String file, String conent) {
		if (StringUtils.isBlank(file) || StringUtils.isBlank(conent))
			return;
		BufferedWriter out = null;
		try {
			File f = findOrCreateFile(file);
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f, true)));
			out.write(conent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 * @param conent
	 */
	public static void writer(String file, String conent) {
		if (StringUtils.isBlank(file) || StringUtils.isBlank(conent))
			return;
		FileOutputStream fos = null;
		try {
			File txt = findOrCreateFile(file);
			byte bytes[] = new byte[512];
			bytes = conent.getBytes();
			int b = conent.length();
			fos = new FileOutputStream(txt);
			fos.write(bytes, 0, b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static String readFileToString(String filePath) throws Exception {
		if (StringUtils.isBlank(filePath))
			return null;
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath)));
		StringBuffer content = new StringBuffer();
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			content.append(str).append("\n");
		}
		return content.toString();
	}

	/**
	 * 读取文件，文件不存在创建文件(创建文件时包括创建所有的父祖籍类文件夹) (创建时包含付文件夹，和文件)
	 * 
	 * @param path
	 * @return
	 */
	public static File findOrCreateFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			(new File(file.getParent())).mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
}
