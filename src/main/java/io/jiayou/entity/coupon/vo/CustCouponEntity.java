package io.jiayou.entity.coupon.vo;

import java.math.BigDecimal;
import java.util.Date;

public class CustCouponEntity {
	
	private String eventId;
	private String custId;
	private Integer ljseq;
	private BigDecimal ljAmt;
	private Date frDate;
	private Date endDate;
	private BigDecimal lowAmt;
	private String disType;
	
	
	public String getDisType() {
		return disType;
	}
	public void setDisType(String disType) {
		this.disType = disType;
	}
	public Date getFrDate() {
		return frDate;
	}
	public void setFrDate(Date frDate) {
		this.frDate = frDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getLowAmt() {
		return lowAmt;
	}
	public void setLowAmt(BigDecimal lowAmt) {
		this.lowAmt = lowAmt;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public Integer getLjseq() {
		return ljseq;
	}
	public void setLjseq(Integer ljseq) {
		this.ljseq = ljseq;
	}
	public BigDecimal getLjAmt() {
		return ljAmt;
	}
	public void setLjAmt(BigDecimal ljAmt) {
		this.ljAmt = ljAmt;
	}
	
	

	
}
