package com.lhh.blog.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lhh.blog.service.LhhBlogArticleContentService;
import com.lhh.blog.service.LhhBlogArticleService;
import com.lhh.config.SystemConfig;
import com.lhh.config.SystemName;

@Controller
public class IndexCtrl {
	private static Logger logger = Logger.getLogger(IndexCtrl.class);
	private static String filePath = "page/blog";
	@Resource
	private LhhBlogArticleService lhhBlogArticleService;
	@Resource
	private LhhBlogArticleContentService lhhBlogArticleContentService;
	


	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse res) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sys_index_name", SystemConfig.getConfig(SystemName.sys_index_name));
		return new ModelAndView(filePath + "/blog-index" , map);
	}
	
}
