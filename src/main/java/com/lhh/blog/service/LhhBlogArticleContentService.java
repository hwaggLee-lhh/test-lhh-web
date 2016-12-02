package com.lhh.blog.service;

import com.base.BaseService;
import com.lhh.blog.model.LhhBlogArticleContentModel;

public interface LhhBlogArticleContentService extends BaseService<LhhBlogArticleContentModel>{


	LhhBlogArticleContentModel findByArticleFK(String articleID);
}

