package com.lhh.security.neeqtsmarket.model;

	import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.base.BaseModel;

@Entity
@Table(name="security_market_period")
public class SecurityMarketPeriod extends BaseModel{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator="paymentableGenerator")
	@GenericGenerator(name="paymentableGenerator",strategy="uuid")
	@Column(name="id")
	private String idStr;
	@Column(name="xxzqdm")
	private String xxzqdm;
	@Column(name="xxzqjc")
	private String xxzqjc;
	@Column(name="xxgprq")
	private String xxgprq;
	@Column(name="xxzgb")
	private BigDecimal xxzgb;
	@Column(name="hqzxj")
	private BigDecimal hqzxj;
	@Column(name="hqzrsp")
	private BigDecimal hqzrsp;
	@Column(name="hqsly")
	private BigDecimal hqsly;
	@Column(name="hqcjl")
	private BigDecimal hqcjl;
	@Column(name="hqcje")
	private BigDecimal hqcje;
	@Column(name="hqsdate")
	private Date hqsdate;
	@Column(name="hqedate")
	private Date hqedate;
	@Column(name="hqperiod")
	private Integer hqperiod;
	@Column(name="insertTime")
	private Date insertTime;
	public void setIdStr(String id){
		this.idStr=id;
	}
	public String getIdStr(){
		return idStr;
	}
	public void setXxzqdm(String xxzqdm){
		this.xxzqdm=xxzqdm;
	}
	public String getXxzqdm(){
		return xxzqdm;
	}
	public void setXxzqjc(String xxzqjc){
		this.xxzqjc=xxzqjc;
	}
	public String getXxzqjc(){
		return xxzqjc;
	}
	public void setXxgprq(String xxgprq){
		this.xxgprq=xxgprq;
	}
	public String getXxgprq(){
		return xxgprq;
	}
	public void setXxzgb(BigDecimal xxzgb){
		this.xxzgb=xxzgb;
	}
	public BigDecimal getXxzgb(){
		return xxzgb;
	}
	public void setHqzxj(BigDecimal hqzxj){
		this.hqzxj=hqzxj;
	}
	public BigDecimal getHqzxj(){
		return hqzxj;
	}
	public void setHqzrsp(BigDecimal hqzrsp){
		this.hqzrsp=hqzrsp;
	}
	public BigDecimal getHqzrsp(){
		return hqzrsp;
	}
	public void setHqsly(BigDecimal hqsly){
		this.hqsly=hqsly;
	}
	public BigDecimal getHqsly(){
		return hqsly;
	}
	public void setHqcjl(BigDecimal hqcjl){
		this.hqcjl=hqcjl;
	}
	public BigDecimal getHqcjl(){
		return hqcjl;
	}
	public void setHqcje(BigDecimal hqcje){
		this.hqcje=hqcje;
	}
	public BigDecimal getHqcje(){
		return hqcje;
	}
	public void setHqsdate(Date hqsdate){
		this.hqsdate=hqsdate;
	}
	public Date getHqsdate(){
		return hqsdate;
	}
	public void setHqedate(Date hqedate){
		this.hqedate=hqedate;
	}
	public Date getHqedate(){
		return hqedate;
	}
	public void setInsertTime(Date insertTime){
		this.insertTime=insertTime;
	}
	public Date getInsertTime(){
		return insertTime;
	}
	public Integer getHqperiod() {
		return hqperiod;
	}
	public void setHqperiod(Integer hqperiod) {
		this.hqperiod = hqperiod;
	}
	
}

