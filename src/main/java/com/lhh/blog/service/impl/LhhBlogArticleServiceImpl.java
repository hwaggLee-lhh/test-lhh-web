package com.lhh.blog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.BaseManager;
import com.base.BaseServiceImpl;
import com.base.Page;
import com.base.PageParam;
import com.lhh.blog.manager.LhhBlogArticleContentManager;
import com.lhh.blog.manager.LhhBlogArticleManager;
import com.lhh.blog.model.LhhBlogArticleContentModel;
import com.lhh.blog.model.LhhBlogArticleModel;
import com.lhh.blog.service.LhhBlogArticleService;

@Service("lhhBlogArticleService")
public class LhhBlogArticleServiceImpl extends BaseServiceImpl<LhhBlogArticleModel> implements LhhBlogArticleService{

	@Resource
	private LhhBlogArticleManager lhhBlogArticleManager;
	@Resource
	private LhhBlogArticleContentManager lhhBlogArticleContentManager;

	@Override
	protected BaseManager<LhhBlogArticleModel> getBaseManager() {
		return lhhBlogArticleManager;
	}

	@Override
	public void saveList(List<LhhBlogArticleModel> list) {
		if( list == null || list.isEmpty())return;
		for (LhhBlogArticleModel m : list) {
			lhhBlogArticleManager.save(m);
			LhhBlogArticleContentModel m1 = m.getArticleContentModel();
			if( m1 == null )continue;
			m1.setArticleFK(m);
			lhhBlogArticleContentManager.save(m1);
		}
	}

	@Override
	public Page<LhhBlogArticleModel> findPage(PageParam pageParam) {
		return this.lhhBlogArticleManager.findPage(pageParam);
	}


}

