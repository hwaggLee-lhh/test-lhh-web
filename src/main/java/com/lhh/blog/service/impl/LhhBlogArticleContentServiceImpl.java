package com.lhh.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.BaseManager;
import com.base.BaseServiceImpl;
import com.lhh.blog.manager.LhhBlogArticleContentManager;
import com.lhh.blog.model.LhhBlogArticleContentModel;
import com.lhh.blog.service.LhhBlogArticleContentService;

@Service("lhhBlogArticleContentService")
public class LhhBlogArticleContentServiceImpl extends BaseServiceImpl<LhhBlogArticleContentModel> implements LhhBlogArticleContentService{

	@Resource
	private LhhBlogArticleContentManager lhhBlogArticleContentManager;

	@Override
	protected BaseManager<LhhBlogArticleContentModel> getBaseManager() {
		return lhhBlogArticleContentManager;
	}

	@Override
	public LhhBlogArticleContentModel findByArticleFK(String articleID) {
		return null;
	}


}

