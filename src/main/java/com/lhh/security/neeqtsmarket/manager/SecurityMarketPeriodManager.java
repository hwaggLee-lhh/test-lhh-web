package com.lhh.security.neeqtsmarket.manager;

import org.springframework.stereotype.Repository;

import com.base.BaseManager;
import com.lhh.security.neeqtsmarket.model.SecurityMarketPeriod;

@Repository("securityMarketPeriodManager")
public class SecurityMarketPeriodManager extends BaseManager<SecurityMarketPeriod>{

	@Override
	public Class<SecurityMarketPeriod> getModelClass() {
		return SecurityMarketPeriod.class;
	}


}

