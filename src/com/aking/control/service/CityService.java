package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.CityDao;
import com.aking.model.subject.City;
import com.aking.util.BeanUtil;
import com.aking.view.vo.CityVO;

@Component
public class CityService extends BaseService<City, String> {

	@Autowired
	private CityDao cityDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		// TODO Auto-generated method stub
		return cityDao;
	}

	public List<CityVO> loadForCombo() throws Exception {
		List<CityVO> cityVOs = new ArrayList<CityVO>();
		List<City> cities = cityDao.loadAll();
		for (City city : cities) {
			CityVO cityVO = new CityVO();
			BeanUtil.copyProperties(city, cityVO);
			cityVO.setCityId(city.getId());
			cityVOs.add(cityVO);
		}
		return cityVOs;
	}

	// public List<CityVO> getByProvince(String provinceId) throws Exception {
	// List<CityVO> cityVOs = new ArrayList<CityVO>();
	// String hql = "from City c where c.province = " + provinceId;
	// List<City> cities = cityDao.find(hql);
	// cityVOs = this.copy2VO(cities);
	// return cityVOs;
	// }

	public List<City> getByProvince(String provinceId) throws Exception {
		// List<CityVO> cityVOs = new ArrayList<CityVO>();
		String hql = "from City c where c.province.id = '" + provinceId + "'";
		List<City> cities = cityDao.find(hql);
		// cityVOs = this.copy2VO(cities);
		return cities;
	}

	public List<CityVO> copy2VO(List<City> cities) throws Exception {
		List<CityVO> cityVOs = new ArrayList<CityVO>();
		for (City city : cities) {
			CityVO cityVO = new CityVO();
			BeanUtil.copyProperties(city, cityVO);
			cityVO.setCityId(city.getId());
			cityVOs.add(cityVO);
		}
		return cityVOs;
	}

}
