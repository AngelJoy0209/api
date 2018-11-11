package io.jiayou.entity.coupon.vo;

import java.math.BigDecimal;
import java.util.Date;

public class EventInfoEntity {
	/**
	 * 活动ID
	 */
	private Integer eventId;
	/**
	 *活动说明 
	 */
	private String eventDesc;
	/**
	 * 活动名称
	 */
	private String eventNm;
	/**
	 * 是否支持购买多张 Y：支持，N：不支持
	 */
	private String isManyOrder;
	/**
	 * 优惠券面值
	 */
	private BigDecimal faceValue;
	/**
	 * 活动开始时间
	 */
	private String startTime;
	/**
	 * 活动结束时间
	 */
	private String endTime;
	/**
	 * 购买金额
	 */
	private BigDecimal buyAmt;
	/**
	 * 礼金券有效开始时间
	 */
	private Date frDate;
	/**
	 * 礼金券有效结束时间
	 */
	private Date endDate;
	/**
	 * 礼金券使用最低订单金额
	 */
	private BigDecimal lowAmt;
	/**
	 * 满减活动标识 D：满减 ZD：扫码购优惠券 
	 */
	private String eventCd;
	/**
	 * 活动页面展示名称
	 */
	private	String eventTitle;
	
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getEventCd() {
		return eventCd;
	}
	public void setEventCd(String eventCd) {
		this.eventCd = eventCd;
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getBuyAmt() {
		return buyAmt;
	}
	public void setBuyAmt(BigDecimal buyAmt) {
		this.buyAmt = buyAmt;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public String getEventNm() {
		return eventNm;
	}
	public void setEventNm(String eventNm) {
		this.eventNm = eventNm;
	}
	public String getIsManyOrder() {
		return isManyOrder;
	}
	public void setIsManyOrder(String isManyOrder) {
		this.isManyOrder = isManyOrder;
	}
	public BigDecimal getFaceValue() {
		return faceValue;
	}
	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}
	
}
