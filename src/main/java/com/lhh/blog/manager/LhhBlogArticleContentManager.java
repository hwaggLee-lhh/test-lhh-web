package com.lhh.blog.manager;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.base.BaseManager;
import com.lhh.blog.model.LhhBlogArticleContentModel;

@Repository("lhhBlogArticleContentManager")
public class LhhBlogArticleContentManager extends BaseManager<LhhBlogArticleContentModel>{

	@Override
	public Class<LhhBlogArticleContentModel> getModelClass() {
		return LhhBlogArticleContentModel.class;
	}

	public LhhBlogArticleContentModel findByArticleFK(String articleID) {
		DetachedCriteria c = super.getDetachedCriteria();
		c.add(Restrictions.eq("articleFK.idStr", articleID));
		c.addOrder(Order.desc("publish_time"));
		List<LhhBlogArticleContentModel> list = super.findList(c);
		if( list !=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

