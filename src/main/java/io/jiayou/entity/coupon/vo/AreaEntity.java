package io.jiayou.entity.coupon.vo;

/**
 * 省份实体
 * @author AngelJoy
 *
 */
public class AreaEntity {
	
	/**
	 * 小区域编号
	 */
	private String srgnCd;
	/**
	 * 小区域名称
	 */
	private String srgnNm;
	/**
	 * 是否可用
	 */
	private String mrgnCd;
	
	private String lrgnCd;
	
	public String getLrgnCd() {
		return lrgnCd;
	}
	public void setLrgnCd(String lrgnCd) {
		this.lrgnCd = lrgnCd;
	}
	public String getMrgnCd() {
		return mrgnCd;
	}
	public void setMrgnCd(String mrgnCd) {
		this.mrgnCd = mrgnCd;
	}
	public String getSrgnCd() {
		return srgnCd;
	}
	public void setSrgnCd(String srgnCd) {
		this.srgnCd = srgnCd;
	}
	public String getSrgnNm() {
		return srgnNm;
	}
	public void setSrgnNm(String srgnNm) {
		this.srgnNm = srgnNm;
	}
	
}
