package com.lhh.security.neeqtsmarket.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.base.BaseManager;
import com.base.BaseServiceImpl;
import com.lhh.security.neeqtsmarket.manager.SecurityMarketPeriodManager;
import com.lhh.security.neeqtsmarket.model.SecurityMarketPeriod;
import com.lhh.security.neeqtsmarket.service.SecurityMarketPeriodService;

@Service("securityMarketPeriodService")
public class SecurityMarketPeriodServiceImpl extends BaseServiceImpl<SecurityMarketPeriod> implements SecurityMarketPeriodService{

	@Resource
	private SecurityMarketPeriodManager securityMarketPeriodManager;

	@Override
	protected BaseManager<SecurityMarketPeriod> getBaseManager() {
		return securityMarketPeriodManager;
	}


}

