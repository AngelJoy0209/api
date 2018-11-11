/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.jiayou.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 注册表单
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.1.0 2018-01-25
 */
@ApiModel(value = "注册LD用户")
public class RegisterCustForm {
	@ApiModelProperty(value = "客户id")
	private Integer cust_id;
	@ApiModelProperty(value = "活动id")
	private Integer event_id;
	@ApiModelProperty(value = "客户姓名",required = true)
	private String cust_nm;
	@ApiModelProperty(value = "性别",required = true)
	private String sex_cd;
	@ApiModelProperty(value = "手机号",required = true)
    private String mobile;
	@ApiModelProperty(value = "省市区地址",required = true)
	private String addr_1;
	@ApiModelProperty(value = "详细地址",required = true)
	private String addr_2;
	@ApiModelProperty(value = "省编码",required = true)
	private String lrgn_cd;
	@ApiModelProperty(value = "市编码",required = true)
	private String mrgn_cd;
	@ApiModelProperty(value = "区编码",required = true)
	private String srgn_cd;
	
	private String hp_teld;
	
	private String hp_telh;
	
	private String hp_teln;
		
	public Integer getEvent_id() {
		return event_id;
	}
	public void setEvent_id(Integer event_id) {
		this.event_id = event_id;
	}
	public Integer getCust_id() {
		return cust_id;
	}
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	public String getCust_nm() {
		return cust_nm;
	}
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}
	public String getSex_cd() {
		return sex_cd;
	}
	public void setSex_cd(String sex_cd) {
		this.sex_cd = sex_cd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddr_1() {
		return addr_1;
	}
	public void setAddr_1(String addr_1) {
		this.addr_1 = addr_1;
	}
	public String getAddr_2() {
		return addr_2;
	}
	public void setAddr_2(String addr_2) {
		this.addr_2 = addr_2;
	}
	public String getLrgn_cd() {
		return lrgn_cd;
	}
	public void setLrgn_cd(String lrgn_cd) {
		this.lrgn_cd = lrgn_cd;
	}
	public String getMrgn_cd() {
		return mrgn_cd;
	}
	public void setMrgn_cd(String mrgn_cd) {
		this.mrgn_cd = mrgn_cd;
	}
	public String getSrgn_cd() {
		return srgn_cd;
	}
	public void setSrgn_cd(String srgn_cd) {
		this.srgn_cd = srgn_cd;
	}
	public String getHp_teld() {
		return hp_teld;
	}
	public void setHp_teld(String hp_teld) {
		this.hp_teld = hp_teld;
	}
	public String getHp_telh() {
		return hp_telh;
	}
	public void setHp_telh(String hp_telh) {
		this.hp_telh = hp_telh;
	}
	public String getHp_teln() {
		return hp_teln;
	}
	public void setHp_teln(String hp_teln) {
		this.hp_teln = hp_teln;
	}
    
	
    
}
