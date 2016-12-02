package com.lhh.blog.service;

import java.util.List;

import com.base.BaseService;
import com.base.Page;
import com.base.PageParam;
import com.lhh.blog.model.LhhBlogArticleModel;

public interface LhhBlogArticleService extends BaseService<LhhBlogArticleModel>{


	void saveList(List<LhhBlogArticleModel> list);
	Page<LhhBlogArticleModel> findPage(PageParam pageParam);
}

