package io.jiayou.controller;


import io.jiayou.common.utils.R;
import io.jiayou.entity.coupon.po.SmsMsgCodeEntity;
import io.jiayou.entity.coupon.vo.AreaEntity;
import io.jiayou.entity.coupon.vo.CityEntity;
import io.jiayou.entity.coupon.vo.CustInfoEntity;
import io.jiayou.entity.coupon.vo.EventInfoEntity;
import io.jiayou.entity.coupon.vo.ProveniceEntity;
import io.jiayou.form.EventInfoForm;
import io.jiayou.service.CodeService;
import io.jiayou.service.CustInfoService;
import io.jiayou.service.EventInfoService;
import io.jiayou.service.ProveniceService;
import io.jiayou.service.WechatPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;

/**
 * 测试接口
 *
 * @author vicky
 * @email angeljoy12138@gmail.com
 * @date 2018-09-13 17:30:29
 */
@RestController
@RequestMapping("/coupon")
@Api(tags="优惠券相关接口")
public class CouponAboutController{

	@Autowired
	ProveniceService proveniceService;
	
	@Autowired
	EventInfoService eventInfoService;
	
	@Autowired
	CodeService codeService;
	
	@Autowired
	CustInfoService custInfoService;
	
	@Autowired
	WechatPayService wechatPayService;
	
	
    @PostMapping("proveniceInfo")
    @ApiOperation(value="获取省级地址列表", response=List.class)
    public R areaInfo(){
    	List<ProveniceEntity> list = proveniceService.getProveniceList();
        return R.ok().put("result", list);
    }
    
    @PostMapping("cityInfo")
    @ApiOperation(value="获取市级地址列表", response=List.class)
    public R cityInfo(String lrgnCd){
    	CityEntity query = new CityEntity();
    	query.setLrgnCd(lrgnCd);
    	List<CityEntity> list = proveniceService.getCityList(query);
        return R.ok().put("result", list);
    }
    
    @PostMapping("areaInfo")
    @ApiOperation(value="获取区级地址列表", response=List.class)
    public R areaInfo(@RequestBody AreaEntity query){
    	List<AreaEntity> list = proveniceService.getAreaList(query);
        return R.ok().put("result", list);
    }
    
    @PostMapping("eventInfo")
    @ApiOperation(value="获取活动详细信息", response=EventInfoEntity.class)
    public R eventInfo(@RequestBody EventInfoForm eventForm) throws ParseException{
    	//表单校验
    	if(eventForm == null){
    		return R.error("参数不合法");
    	}
    	EventInfoEntity query = new EventInfoEntity();
    	query.setEventId(eventForm.getEventId());
    	EventInfoEntity eventInfoEntity = eventInfoService.getEventById(query);
    	if(StringUtils.isEmpty(eventInfoEntity.getIsManyOrder())){
    		eventInfoEntity.setIsManyOrder("N");
    	}
    	String eventCd = eventInfoEntity.getEventCd();
    	if("D".equals(eventCd)){//D为满减券，只允许购买一张
    		eventInfoEntity.setIsManyOrder("N");
    	}
    	String endTime = eventInfoEntity.getEndTime()+" 23:59:59";
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date endDate = sdf. parse(endTime);
    	int compare = endDate.compareTo(new Date());
    	if(compare < 0){
    		return R.error(666,"活动已结束");
    	}
    	return R.ok().put("result", eventInfoEntity);
    }
    
    
    @GetMapping("getImgCode")
    @ApiOperation(value="获取图形验证码")
    public void getImgVerificationCode(HttpServletResponse response,String phoneNo) throws IOException{
    	codeService.getCodeImg(response,phoneNo);
    }
    
    @PostMapping("checkImgCode")
    @ApiOperation(value="验证图形验证码")
    public R checkImgCode(HttpServletRequest request,String code,String phoneNo) {
    	
    	if(StringUtils.isEmpty(code)){
    		return R.error("请正确填写验证码");
    	}
    	if(StringUtils.isEmpty(phoneNo)){
    		return R.error("验证码已失效");
    	}
    	SmsMsgCodeEntity query = new SmsMsgCodeEntity();
    	query.setCode(code.toUpperCase());
    	query.setPhoneNo(phoneNo);
    	query.setCodeType(1);
    	SmsMsgCodeEntity result = codeService.getVerifiCode(query);
    	if(result != null){
    		return R.ok();
    	}
    	return R.error("验证码不正确！！");
    }

    @PostMapping("sendMsgCode")
    @ApiOperation(value="发送短信验证码接口")
    public R sendVerificationCode(String phoneNo){
    	codeService.sendCodeMsg(phoneNo);
		return R.ok();
    }
    
    @PostMapping("checkMsgCode")
    @ApiOperation(value="校验短信验证码接口")
    public R checkMsgCode(HttpServletRequest request,@RequestBody SmsMsgCodeEntity msgCode){
    	String phoneNo = msgCode.getPhoneNo();
    	String code = msgCode.getCode();
    	msgCode.setCodeType(2);
    	SmsMsgCodeEntity result = codeService.getVerifiCode(msgCode);
    	
    	if(StringUtils.isEmpty(phoneNo)){
    		return R.error("手机号不能为空。");
    	}
    	if(StringUtils.isEmpty(code)){
    		return R.error("验证码不能为空。");
    	}
    	if(result == null){
    		return R.error("验证码错误，或是手机号有误");
    	}
    	//校验用户是否是老用户，根据手机号校验
    	Integer custId  = 0;//TODO
    	
    	Map<String,Object> checkResult = custInfoService.getCustlvlByTelephone(phoneNo, "127.0.0.1");
    	if(checkResult != null){
    		String custIdStr = checkResult.get("cust_id") != null?checkResult.get("cust_id").toString():"";
    		if(!StringUtils.isEmpty(custIdStr)){
    			custId = Integer.parseInt(custIdStr);
    		}
    	}
    	
    	if(custId != 0){
    		CustInfoEntity query = new CustInfoEntity();
    		query.setCustId(custId);
    		CustInfoEntity cust = custInfoService.getCustInfoById(query);
    		this.setPro(cust);
    		
    		return R.ok().put("result", cust);
    	}
    	
    	return R.ok().put("result", null);
    	
    }
    
    @GetMapping("wechatPay")
    @ApiOperation(value="微信支付")
    public void wechatPay(HttpServletResponse response,String eventId,String custId,String cardCount) throws Throwable{
    	if(StringUtils.isEmpty(eventId)){
    		return;
    	}
    	if(StringUtils.isEmpty(custId)){
    		return;
    	}
    	if(StringUtils.isEmpty(cardCount)){
    		return;
    	}
    	Integer count = Integer.parseInt(cardCount);
    	String url = wechatPayService.pay(eventId,custId,count); 
    	response.sendRedirect(url);
    }
    
    
    @PostMapping("wxnotifyUrl")
    @ApiOperation(value="微信回调地址2")
    public String wxNotifyUrl2(HttpServletRequest request,@RequestBody String xml) throws Exception {
        // 微信支付回调
    	
    	return wechatPayService.notify(xml);
       
        
    }
    
    @PostMapping("test")
    @ApiOperation(value="测试")
    public void test(String eventId,String custId) {
    	System.out.println("eventId:"+eventId +",custId:"+custId);
    }
    
    
    

    /**
     * 给用户信息赋值，主要查询用户的地址信息
     * @param cust
     */
	private void setPro(CustInfoEntity cust) {
		String lrgnCd = cust.getLrgnCd();
		String mrgnCd = cust.getMrgnCd();
		String srgnCd = cust.getSrgnCd();
		ProveniceEntity pQuery = new ProveniceEntity();
		pQuery.setLrgnCd(lrgnCd);
		CityEntity cQuery = new CityEntity();
		cQuery.setLrgnCd(lrgnCd);
		cQuery.setMrgnCd(mrgnCd);
		AreaEntity aQuery = new AreaEntity();
		aQuery.setLrgnCd(lrgnCd);
		aQuery.setMrgnCd(mrgnCd);
		aQuery.setSrgnCd(srgnCd);
		ProveniceEntity provenice = proveniceService.getNameByLrgnCd(pQuery);
		CityEntity city = proveniceService.getNameByMrgnCd(cQuery);
		AreaEntity area = proveniceService.getNameBySrgnCd(aQuery);
		String lrgnNm = provenice != null ? provenice.getLrgnNm():"";
		String mrgnNm = city != null ? city.getMrgnNm():"";
		String srgnNm = area != null ? area.getSrgnNm():"";
		cust.setLrgnNm(lrgnNm);
		cust.setMrgnNm(mrgnNm);
		cust.setSrgnNm(srgnNm);
	}

}
