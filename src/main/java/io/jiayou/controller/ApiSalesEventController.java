package io.jiayou.controller;


import io.jiayou.common.utils.R;
import io.jiayou.common.validator.ValidatorUtils;
import io.jiayou.form.AlipayForm;
import io.jiayou.form.RegisterCustForm;
import io.jiayou.service.AlipayService;
import io.jiayou.service.CustInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注册接口
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/api/event")
@PropertySource(value = {"classpath:system.properties"},encoding="utf-8")
@Api(tags="促销活动接口")
public class ApiSalesEventController {
    @Autowired
    CustInfoService custInfoService;
    @Autowired
    AlipayService alipayService;
    @Value("${ALIPAY_PUBLIC_KEY}")
    private String key;

    @PostMapping("custLogin")
    @ApiOperation("参加活动用户登入系统")
    public R register(@RequestBody RegisterCustForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        try {
			Map<String, Object> map = custInfoService.createOrUpdateCust(form);
			return R.ok(map);
		} catch (Exception e) {
			return R.error(e.getMessage());
		}       
    }
    
    @GetMapping("payOnline")
    @ApiOperation("支付宝支付")
    public void userInfo(HttpServletResponse response,AlipayForm payInfo){
    	String form = alipayService.pay(payInfo);
    	
    	try {
			response.setContentType("text/html;charset=UTF-8"); 
		    response.getWriter().write(form);//直接将完整的表单html输出到页面 
		    response.getWriter().flush(); 
		    response.getWriter().close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    @PostMapping("notify")
    @ApiOperation("支付宝支付")
    public void userInfo(HttpServletRequest request,HttpServletResponse response){
    	Map<String,String> params = new HashMap<String,String>();
    	Map requestParams = request.getParameterMap();
    	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
    		String name = (String) iter.next();
    		String[] values = (String[]) requestParams.get(name);
    		String valueStr = "";
    		for (int i = 0; i < values.length; i++) {
    			valueStr = (i == values.length - 1) ? valueStr + values[i]
    					: valueStr + values[i] + ",";
    		}
    		//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
    		//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
    		params.put(name, valueStr);
    	}
    	try {
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			String gmt_payment = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"),"UTF-8");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			boolean verify_result = AlipaySignature.rsaCheckV1(params, key, "UTF-8","RSA2");
			if(verify_result){
				if (trade_status.equals("TRADE_SUCCESS")){
					int result = alipayService.notify(out_trade_no, trade_no,sf.parse(gmt_payment));
					if(result == 0){
						PrintWriter out = response.getWriter();
						out.println("success");
					}
				}
			}else{
				PrintWriter out = response.getWriter();
				out.println("fail");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
