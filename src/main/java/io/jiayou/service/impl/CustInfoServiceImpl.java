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

package io.jiayou.service.impl;


import io.jiayou.dao.CustInfoDao;
import io.jiayou.dao.EventInfoDao;
import io.jiayou.datasources.DataSourceNames;
import io.jiayou.datasources.annotation.DataSource;
import io.jiayou.entity.coupon.vo.CustInfoEntity;
import io.jiayou.entity.coupon.vo.EventInfoEntity;
import io.jiayou.form.RegisterCustForm;
import io.jiayou.service.CustInfoService;
import io.jiayou.utils.CcsUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service("custInfoService")
public class CustInfoServiceImpl extends ServiceImpl<CustInfoDao, CustInfoEntity> implements CustInfoService {

	@Autowired
	CustInfoDao custInfoDao;
	@Autowired
	EventInfoDao eventInfoDao;
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public CustInfoEntity getCustInfoById(CustInfoEntity query) {

		CustInfoEntity result = custInfoDao.getCustInfoById(query);
		
		return result;
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public Map<String, Object> getCustlvlByTelephone(String mobile,String ip) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> list = CcsUtil.getCustIdListByCall(mobile, ip);
		String custlvl = "";
		String cust_id = "";
		if(null == list || list.size()<=0){
			result.put("success", false);
			result.put("message", "该手机号没有对应的客户信息");
			return result;
		}
		for(String custId:list){
			String temp;
			try {
				CustInfoEntity query = new CustInfoEntity();
				query.setCustId(Integer.parseInt(custId));
				temp = custInfoDao.getCustlvlByCustid(query);
			} catch (Exception e) {
				result.put("success", false);
				result.put("message", "查询客户等级异常");
				return result;
			}
			if(StringUtils.isBlank(custlvl)){
				custlvl = temp;
				cust_id = custId;
				continue;
			}
			if(Integer.parseInt(temp)>70){
				continue;
			}
			if(Integer.parseInt(custlvl)>70){
				custlvl = temp;
				cust_id = custId;
				continue;
			}
			if(Integer.parseInt(custlvl)<Integer.parseInt(temp)){
				custlvl = temp;
				cust_id = custId;
				continue;
			}
		}
		result.put("success", true);
		result.put("custlvl", custlvl);
		result.put("cust_id", cust_id);
		
		return result;
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public Map<String, Object> createOrUpdateCust(RegisterCustForm form) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		form.setHp_teld(form.getMobile().substring(0, 3));
		form.setHp_telh(form.getMobile().substring(3, 7));
		form.setHp_teln(form.getMobile().substring(7));
		if(form.getLrgn_cd().equals("110000")||form.getLrgn_cd().equals("120000")||form.getLrgn_cd().equals("310000")||form.getLrgn_cd().equals("500000")){
			form.setMrgn_cd(form.getSrgn_cd().substring(0, 4)+"00");
		}
		if(form.getCust_id()!=null&&form.getCust_id()!=0){
			EventInfoEntity queryEvent = new EventInfoEntity();
	        queryEvent.setEventId(form.getEvent_id());
	        EventInfoEntity eventInfo = eventInfoDao.getEventById(queryEvent);
	        if("ZD".equals(eventInfo.getEventCd())){
	        	String flag = custInfoDao.validateCanBuy(form.getCust_id(), form.getEvent_id());
				if(flag.equals("N")){
					result.put("cust_id", form.getCust_id());
					result.put("can_do", "N");
					return result;
				}
	        }else if("D".equals(eventInfo.getEventCd())){
	        	int flag = custInfoDao.validateCanBuyd(form.getCust_id(), form.getEvent_id());
	        	if(flag>0){
	        		result.put("cust_id", form.getCust_id());
					result.put("can_do", "N");
					return result;
	        	}
	        }
			
			CustInfoEntity query = new CustInfoEntity();
			query.setCustId(form.getCust_id());
			query.setLrgnCd(form.getLrgn_cd());
			query.setMrgnCd(form.getMrgn_cd());
			query.setSrgnCd(form.getSrgn_cd());
			query.setAreaDetail(form.getAddr_2());
			int r = custInfoDao.validateCustAddr(query);
			if(r<=0){
				custInfoDao.addCustAddr(form);
			}
			result.put("cust_id", form.getCust_id());
			result.put("can_do", "Y");
			return result;
		}else{
			custInfoDao.addCustInfo(form);
			custInfoDao.addCustAddr(form);
			result.put("cust_id", form.getCust_id());
			result.put("can_do", "Y");
			return result;
		}
		
	}
	

}
