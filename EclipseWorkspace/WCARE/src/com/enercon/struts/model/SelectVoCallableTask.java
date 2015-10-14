package com.enercon.struts.model;

import java.util.List;
import java.util.concurrent.Callable;

import com.enercon.ajax.ajaxVo.SelectVo;
import com.enercon.struts.dao.SelectVoDao;

public class SelectVoCallableTask implements Callable<List<SelectVo>>{
	
	private String queryResolver;

	public SelectVoCallableTask(String queryResolver) {
		super();
//		System.out.println(queryResolver);
		this.queryResolver = queryResolver;
	}

	public String getQueryResolver() {
		return queryResolver;
	}

	public void setQueryResolver(String queryResolver) {
		this.queryResolver = queryResolver;
	}

	public List<SelectVo> call() throws Exception {
		return SelectVoDao.getSelectVo(queryResolver);
	}

}
