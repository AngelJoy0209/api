package io.jiayou.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "支付宝支付入参")
public class AlipayForm {
	@ApiModelProperty(value = "活动id")
	private String event_id;
	@ApiModelProperty(value = "订购数量")
	private Integer ord_qty;
	@ApiModelProperty(value = "客户id")
	private String cust_id;
	
	
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public Integer getOrd_qty() {
		return ord_qty;
	}
	public void setOrd_qty(Integer ord_qty) {
		this.ord_qty = ord_qty;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	
}
