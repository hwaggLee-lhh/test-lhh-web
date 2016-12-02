package com.lhh.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

/**
 * 系统配置文件读取
 * @author huage
 *
 */
public class SystemConfig {
    private static Properties props = null;    
    private static File configFile = null; 
    private static long fileLastModified = 0L; 
    
    private static String configFileName = "properties/sys/system.properties";
    
    private static void init() { 
        URL url = SystemConfig.class.getClassLoader().getResource(configFileName); 
        

        configFile = new File(url.getFile()); 
        fileLastModified = configFile.lastModified();      
        props = new Properties(); 
        load(); 
    } 
    
    private static void load() { 
        try { 
            props.load(new InputStreamReader(new FileInputStream(configFile),"UTF-8")); 
            fileLastModified = configFile.lastModified(); 
        } catch (IOException e) {            
            throw new RuntimeException(e); 
        } 
    } 

    public static String getConfig(String key) { 
        if ((configFile == null) || (props == null)) init(); 
        if (configFile.lastModified() > fileLastModified) load(); //当检测到文件被修改时重新加载配置文件
        return props.getProperty(key); 
    } 
    
    public static void main(String[] args){
        System.out.println(getConfig("sys.index.name")); 
    }}
