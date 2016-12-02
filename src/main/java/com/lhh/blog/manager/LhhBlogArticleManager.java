package com.lhh.blog.manager;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.base.BaseManager;
import com.base.Page;
import com.base.PageParam;
import com.lhh.blog.model.LhhBlogArticleModel;

@Repository("lhhBlogArticleManager")
public class LhhBlogArticleManager extends BaseManager<LhhBlogArticleModel>{

	@Override
	public Class<LhhBlogArticleModel> getModelClass() {
		return LhhBlogArticleModel.class;
	}

	public Page<LhhBlogArticleModel> findPage(PageParam pageParam) {
		Criteria c = super.getCriteria();
		c.addOrder(Order.desc("publish_time"));
		return super.findPage(c, pageParam);
	}
}

