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


import io.jiayou.dao.EventInfoDao;
import io.jiayou.datasources.DataSourceNames;
import io.jiayou.datasources.annotation.DataSource;
import io.jiayou.entity.coupon.vo.EventInfoEntity;
import io.jiayou.service.EventInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service("eventInfoService")
public class EventInfoServiceImpl extends ServiceImpl<EventInfoDao, EventInfoEntity> implements EventInfoService {

	@Autowired
	EventInfoDao eventInfoDao;

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public EventInfoEntity getEventById(EventInfoEntity eventInfo) {
		EventInfoEntity info = eventInfoDao.getEventById(eventInfo);
		if(info == null){
			return null;
		}
		String startTime = info.getStartTime();
		String endTime = info.getEndTime();
		if(startTime.length() > 10){
			info.setStartTime(startTime.substring(0, 10));
		}
		if(endTime.length() > 10){
			info.setEndTime(endTime.substring(0, 10));
		}
		return info;
	}
	
}
