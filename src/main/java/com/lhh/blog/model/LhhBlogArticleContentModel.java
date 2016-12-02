package com.lhh.blog.model;

	import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.base.BaseModel;

@Entity
@Table(name="lhh_blog_article_content")
public class LhhBlogArticleContentModel extends BaseModel{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="paymentableGenerator")
	@GenericGenerator(name="paymentableGenerator",strategy="uuid")
	@Column(name="idStr")
	private String idStr;
	@OneToOne(targetEntity = LhhBlogArticleModel.class)
	@JoinColumn(name="articleFK")
	private LhhBlogArticleModel articleFK;
	@Column(name="article_source_link")
	private String article_source_link;
	@Column(name="article_content")
	private String article_content;
	@Column(name="article_img_link")
	private String article_img_link;
	public void setIdStr(String idStr){
		this.idStr=idStr;
	}
	public String getIdStr(){
		return idStr;
	}
	public void setArticleFK(LhhBlogArticleModel articleFK){
		this.articleFK=articleFK;
	}
	public LhhBlogArticleModel getArticleFK(){
		return articleFK;
	}
	public void setArticle_source_link(String article_source_link){
		this.article_source_link=article_source_link;
	}
	public String getArticle_source_link(){
		return article_source_link;
	}
	public void setArticle_content(String article_content){
		this.article_content=article_content;
	}
	public String getArticle_content(){
		return article_content;
	}
	public void setArticle_img_link(String article_img_link){
		this.article_img_link=article_img_link;
	}
	public String getArticle_img_link(){
		return article_img_link;
	}
}

