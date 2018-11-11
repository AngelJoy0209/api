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

package io.jiayou.service;

import java.util.Date;

import com.baomidou.mybatisplus.service.IService;

import io.jiayou.entity.coupon.vo.EventInfoEntity;
import io.jiayou.form.AlipayForm;

/**
 * 用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 */
public interface AlipayService extends IService<EventInfoEntity> {

	public String pay(AlipayForm payInfo);
	
	public int notify(String out_trade_no,String trade_no,Date pay_time);
}
