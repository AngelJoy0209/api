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


import java.util.Map;

import io.jiayou.entity.coupon.vo.CustInfoEntity;
import io.jiayou.form.RegisterCustForm;

import com.baomidou.mybatisplus.service.IService;

/**
 * 用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 */
public interface CustInfoService extends IService<CustInfoEntity> {

	CustInfoEntity getCustInfoById(CustInfoEntity custInfo);
	
	public Map<String, Object> getCustlvlByTelephone(String mobile,String ip);
	
	public Map<String, Object> createOrUpdateCust(RegisterCustForm form);
}
