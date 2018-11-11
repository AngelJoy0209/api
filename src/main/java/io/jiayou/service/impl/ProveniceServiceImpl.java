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


import io.jiayou.dao.ProveniceDao;
import io.jiayou.datasources.DataSourceNames;
import io.jiayou.datasources.annotation.DataSource;
import io.jiayou.entity.coupon.vo.AreaEntity;
import io.jiayou.entity.coupon.vo.CityEntity;
import io.jiayou.entity.coupon.vo.ProveniceEntity;
import io.jiayou.service.ProveniceService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;

@Service("proveniceService")
public class ProveniceServiceImpl extends ServiceImpl<ProveniceDao, ProveniceEntity> implements ProveniceService {

	@Autowired
	ProveniceDao proveniceDao;
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<ProveniceEntity> getProveniceList() {

		
		List<ProveniceEntity> provenices = proveniceDao.getProveniceList();
		
		return provenices;
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<CityEntity> getCityList(CityEntity query) {
		List<CityEntity> list = proveniceDao.getCityList(query);
		if(list == null || list.size() == 0){
			return null;
		}
		CityEntity entity = list.get(0);
		List<CityEntity> list2 = new ArrayList<CityEntity>();
		if("北京市".equals(entity.getMrgnNm())||"重庆市".equals(entity.getMrgnNm())||"天津市".equals(entity.getMrgnNm())||"上海市".equals(entity.getMrgnNm())){
			list2.add(entity);
			return list2;
		}
		return list;
	}
	
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public List<AreaEntity> getAreaList(AreaEntity query) {
		String lrgnCd = query.getLrgnCd();
		if("110000".equals(lrgnCd)||"120000".equals(lrgnCd)||"500000".equals(lrgnCd)||"310000".equals(lrgnCd)){
			List<AreaEntity> list2 = proveniceDao.getAreaListByProvenice(query);
			return list2;
		}
		List<AreaEntity> list = proveniceDao.getAreaList(query);
		return list;
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public ProveniceEntity getNameByLrgnCd(ProveniceEntity query) {
		
		return proveniceDao.getNameByLrgnCd(query);
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public CityEntity getNameByMrgnCd(CityEntity query) {
		CityEntity result = new CityEntity();
		if(StringUtils.isEmpty(query.getMrgnCd())){
			return null;
		}
		result = proveniceDao.getNameByMrgnCd(query);
		return result;
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public AreaEntity getNameBySrgnCd(AreaEntity query) {
		if(StringUtils.isEmpty(query.getMrgnCd())){
			return null;
		}
		if(StringUtils.isEmpty(query.getSrgnCd())){
			return null;
		}
		return proveniceDao.getNameBySrgnCd(query);
	}

	
	
	

}
