package io.jiayou.service.impl;

import io.jiayou.dao.CardOrderDao;
import io.jiayou.dao.CustCouponDao;
import io.jiayou.dao.EventInfoDao;
import io.jiayou.datasources.DataSourceNames;
import io.jiayou.datasources.annotation.DataSource;
import io.jiayou.entity.coupon.po.CardOrderEntity;
import io.jiayou.entity.coupon.vo.CustCouponEntity;
import io.jiayou.entity.coupon.vo.EventInfoEntity;
import io.jiayou.service.CustCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service("custCardService")
public class CustCardServiceImpl  extends ServiceImpl<CustCouponDao, CustCouponEntity> implements CustCardService {

	@Autowired
	EventInfoDao eventInfoDao;
	@Autowired
	CustCouponDao custCouponDao;
	@Autowired
	CardOrderDao cardOrderDao;
	
	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public boolean setLjqToCust(String orderCode) {
		CardOrderEntity query = new  CardOrderEntity();
        query.setOrderNo(orderCode);
        CardOrderEntity entity = cardOrderDao.getCardOrder(query);
        Integer eventId = entity.getEventId();
        Integer custId = entity.getCustId();
        EventInfoEntity queryEvent = new EventInfoEntity();
        queryEvent.setEventId(eventId);
        EventInfoEntity eventInfo = eventInfoDao.getEventById(queryEvent);
        String eventCd = eventInfo.getEventCd();//
        CustCouponEntity ljqInfo = custCouponDao.getEventDetail(entity.getEventId());
        if("ZD".equals(eventCd)){//礼金券赋予形式
        	Integer seq = eventInfoDao.getMaxSeq(eventId.toString(), custId.toString());
        	for(int i = seq + 1;i<=seq +entity.getAmount();i++){
        		CustCouponEntity info = new CustCouponEntity();
        		info.setEventId(eventId.toString());
        		info.setCustId(custId.toString());
        		info.setLjAmt(ljqInfo.getLjAmt());
        		info.setLjseq(i);
        		info.setFrDate(ljqInfo.getFrDate());
        		info.setEndDate(ljqInfo.getEndDate());
        		info.setLowAmt(ljqInfo.getLowAmt());
        		String disType = ljqInfo.getDisType();
        		if("10".equals(disType)||"20".equals(disType)){
        			info.setDisType("10");
        		}else if("30".equals(disType)){
        			info.setDisType("20");
        		}else{
        			info.setDisType(disType);
        		}
        		custCouponDao.addCustLjq(info);
        	}
        	return true;
        }else if("D".equals(eventCd)){//满额满赠活动赋予形式
        	CustCouponEntity info = new CustCouponEntity();
    		info.setEventId(eventId.toString());
    		info.setCustId(custId.toString());
    		custCouponDao.addCustZkq(info);
        	return true;
        }else{
        	return false;
        }
	}

	
}
