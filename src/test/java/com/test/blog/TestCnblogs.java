package com.test.blog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.lhh.blog.model.LhhBlogArticleContentModel;
import com.lhh.blog.model.LhhBlogArticleModel;
import com.lhh.blog.service.LhhBlogArticleService;
import com.test.BaseServiceTest;
import com.test.blog.ReadCnblogsUtils.CnblogsArticle;
import com.utils.DateTimeUtils;

/**
 * 读取cnblogs 网站的数据
 * 
 * @author huage
 * @url http://www.cnblogs.com/hwaggLee
 */
public class TestCnblogs extends BaseServiceTest {

	@Resource
	private LhhBlogArticleService lhhBlogArticleService;
	
	
	/**
	 * 测试启动器，并结束异常打印
	 * 
	 */
	@Test
	public void testStart() {
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试执行程序
	 * 
	 */
	private void start(){
		readXML();
	}

	private void readXML() {
		ReadCnblogsUtils u = new ReadCnblogsUtils();
		List<CnblogsArticle> list = u.parserXML();
		if(list == null || list.isEmpty())return;
		
		List<LhhBlogArticleModel> articleList = new ArrayList<LhhBlogArticleModel>();
		
		Date date = new Date();
		for (CnblogsArticle cn : list) {
			LhhBlogArticleModel articleModel = new LhhBlogArticleModel();
			LhhBlogArticleContentModel articleContentModel = new LhhBlogArticleContentModel();
			
			articleModel.setArticle_author(cn.getAuthor());
			articleModel.setArticle_source(cn.getLink());
			articleModel.setArticle_status("0");
			articleModel.setArticle_title(cn.getTitle());
			articleModel.setInsert_time(date);
			Date pubDate = DateTimeUtils.str3Date( cn.getPubDate(),DateTimeUtils.FORMAT_gmt_002);
			
			articleModel.setPublish_time(pubDate);
			
			articleContentModel.setArticle_content(cn.getDescription());
			articleContentModel.setArticle_source_link(cn.getGuid());
			
			articleModel.setArticleContentModel(articleContentModel);
			articleList.add(articleModel);
		}
		lhhBlogArticleService.saveList(articleList);
		
		
	}
}
