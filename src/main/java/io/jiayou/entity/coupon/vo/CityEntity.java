package io.jiayou.entity.coupon.vo;


/**
 * 市区实体
 * @author AngelJoy
 *
 */
public class CityEntity {
	/**
	 * 小区域编号
	 */
	private String mrgnCd;
	/**
	 * 小区域名称
	 */
	private String mrgnNm;

	/**
	 * 省级地址编码
	 */
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
	public String getMrgnNm() {
		return mrgnNm;
	}
	public void setMrgnNm(String mrgnNm) {
		this.mrgnNm = mrgnNm;
	}
	
}
