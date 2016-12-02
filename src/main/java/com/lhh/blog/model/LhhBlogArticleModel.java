package com.lhh.blog.model;

	import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.base.BaseModel;

@Entity
@Table(name="lhh_blog_article")
public class LhhBlogArticleModel extends BaseModel{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="paymentableGenerator")
	@GenericGenerator(name="paymentableGenerator",strategy="uuid")
	@Column(name="idStr")
	private String idStr;
	@Column(name="article_title")
	private String article_title;
	@Column(name="article_author")
	private String article_author;
	@Column(name="article_abstract")
	private String article_abstract;
	@Column(name="article_source")
	private String article_source;
	@Column(name="article_keyword")
	private String article_keyword;
	@Column(name="article_status")
	private String article_status;
	@Column(name="publish_time")
	private Date publish_time;
	@Column(name="insert_time")
	private Date insert_time;
	@Column(name="update_time")
	private Date update_time;
	
	@Transient
	private LhhBlogArticleContentModel articleContentModel;
	
	public void setIdStr(String idStr){
		this.idStr=idStr;
	}
	public String getIdStr(){
		return idStr;
	}
	public void setArticle_title(String article_title){
		this.article_title=article_title;
	}
	public String getArticle_title(){
		return article_title;
	}
	public void setArticle_author(String article_author){
		this.article_author=article_author;
	}
	public String getArticle_author(){
		return article_author;
	}
	public void setArticle_abstract(String article_abstract){
		this.article_abstract=article_abstract;
	}
	public String getArticle_abstract(){
		return article_abstract;
	}
	public void setArticle_source(String article_source){
		this.article_source=article_source;
	}
	public String getArticle_source(){
		return article_source;
	}
	public void setArticle_keyword(String article_keyword){
		this.article_keyword=article_keyword;
	}
	public String getArticle_keyword(){
		return article_keyword;
	}
	public void setArticle_status(String article_status){
		this.article_status=article_status;
	}
	public String getArticle_status(){
		return article_status;
	}
	public void setPublish_time(Date publish_time){
		this.publish_time=publish_time;
	}
	public Date getPublish_time(){
		return publish_time;
	}
	public void setInsert_time(Date insert_time){
		this.insert_time=insert_time;
	}
	public Date getInsert_time(){
		return insert_time;
	}
	public void setUpdate_time(Date update_time){
		this.update_time=update_time;
	}
	public Date getUpdate_time(){
		return update_time;
	}
	public LhhBlogArticleContentModel getArticleContentModel() {
		return articleContentModel;
	}
	public void setArticleContentModel(
			LhhBlogArticleContentModel articleContentModel) {
		this.articleContentModel = articleContentModel;
	}
	
}

