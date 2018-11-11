package io.jiayou.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jiayou.annotation.Login;
import io.jiayou.annotation.LoginUser;
import io.jiayou.common.utils.R;
import io.jiayou.entity.UserEntity;
import io.jiayou.service.AlipayService;
import io.jiayou.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 测试接口
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:47
 */
@RestController
@RequestMapping("/api")
@Api(tags="测试接口")
public class ApiTestController {

	@Autowired
	private TestService testService;
	@Autowired
    AlipayService alipayService;
	
    @Login
    @GetMapping("userInfo")
    @ApiOperation(value="获取用户信息", response=UserEntity.class)
    public R userInfo(@ApiIgnore @LoginUser UserEntity user){
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public R userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId){
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken(){
        return R.ok().put("msg", "无需token也能访问。。。");
    }
    
    @GetMapping("getCon")
    @ApiOperation("忽略Token验证测试")
    public R getCon(){
        return R.ok().put("msg", testService.getCon());
    }
    
    @PostMapping("notify")
    @ApiOperation("测试发放礼金券")
    public R testNotify(@RequestParam String out_trade_no,@RequestParam String trade_no){
    	int result = alipayService.notify(out_trade_no, trade_no,new Date());
    	if(result==0){
    		return R.ok("成功了");
    	}
    	return R.error("fail");
    }
}
