package io.jiayou.service.impl;

import io.jiayou.dao.CodeDao;
import io.jiayou.datasources.DataSourceNames;
import io.jiayou.datasources.annotation.DataSource;
import io.jiayou.entity.coupon.po.SmsMsgCodeEntity;
import io.jiayou.entity.coupon.vo.VerifiCode;
import io.jiayou.service.CodeService;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service("codeService")
public class CodeServiceImpl extends ServiceImpl<CodeDao, SmsMsgCodeEntity> implements CodeService{
	
	@Autowired
	CodeDao codeDao;

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public void getCodeImg(HttpServletResponse response,String phoneNo) throws IOException {
		VerifiCode code = new VerifiCode();
		BufferedImage img = code.getImage();
		String text = code.getText();
		SmsMsgCodeEntity codeEntity = new SmsMsgCodeEntity();
		codeEntity.setCode(text.toUpperCase());
		codeEntity.setPhoneNo(phoneNo);
		codeEntity.setCodeType(1);
		Integer ifExit = codeDao.getCount(codeEntity);
		if(ifExit==0){
			codeDao.saveVerifiCode(codeEntity);
		}else{
			codeDao.updateByType(codeEntity);
		}
		code.output(img, response.getOutputStream());
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public Integer sendCodeMsg(String phoneNo) {
		VerifiCode code = new VerifiCode();
		String msgCode = code.getMsgCode();
		SmsMsgCodeEntity codeEntity = new SmsMsgCodeEntity();
		codeEntity.setCode(msgCode);
		codeEntity.setPhoneNo(phoneNo);
		codeEntity.setMsg("您的短信验证码是："+msgCode+"。如非您本人操作，请忽略。");
		Integer count = codeDao.save(codeEntity);
		codeEntity.setCodeType(2);//短信验证码
		Integer ifExit = codeDao.getCount(codeEntity);
		if(ifExit==0){
			codeDao.saveVerifiCode(codeEntity);
		}else{
			codeDao.updateByType(codeEntity);
		}
		return count;
	}

	@Override
	@DataSource(name = DataSourceNames.SECOND)
	public SmsMsgCodeEntity getVerifiCode(SmsMsgCodeEntity entity) {
		SmsMsgCodeEntity result = codeDao.getVerifiCode(entity);
		return result;
	}
	
}
