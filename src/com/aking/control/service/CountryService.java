package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.CountryDao;
import com.aking.model.subject.Country;
import com.aking.util.BeanUtil;
import com.aking.view.vo.CountryVO;

@Component
public class CountryService extends BaseService<Country, String> {

	@Autowired
	private CountryDao countryDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		// TODO Auto-generated method stub
		return countryDao;
	}

	public List<CountryVO> loadForCombo() throws Exception {
		List<CountryVO> countryVOs = new ArrayList<CountryVO>();
		List<Country> countries = countryDao.loadAll();
		for (Country country : countries) {
			CountryVO countryVO = new CountryVO();
			BeanUtil.copyProperties(country, countryVO);
			countryVO.setCountryId(country.getId());
			countryVOs.add(countryVO);
		}
		return countryVOs;
	}

}
