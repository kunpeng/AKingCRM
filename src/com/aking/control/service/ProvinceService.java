package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.ProvinceDao;
import com.aking.model.subject.Province;
import com.aking.util.BeanUtil;
import com.aking.view.vo.ProvinceVO;

@Component
public class ProvinceService extends BaseService<Province, String> {

	@Autowired
	private ProvinceDao provinceDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		// TODO Auto-generated method stub
		return provinceDao;
	}

	public List<ProvinceVO> loadForCombo() throws Exception {
		List<ProvinceVO> provinceVOs = new ArrayList<ProvinceVO>();
		List<Province> provinces = provinceDao.loadAll();
		for (Province province : provinces) {
			ProvinceVO provinceVO = new ProvinceVO();
			BeanUtil.copyProperties(province, provinceVO);
			provinceVO.setProvinceId(province.getId());
			provinceVOs.add(provinceVO);
		}
		return provinceVOs;
	}

	// public List<ProvinceVO> getByCountry(String countryId) throws Exception {
	// List<ProvinceVO> provinceVOs = new ArrayList<ProvinceVO>();
	// String hql = "From Province p where p.country = " + countryId;
	// List<Province> provinces = provinceDao.find(hql);
	// provinceVOs = this.copy2VO(provinces);
	// return provinceVOs;
	// }

	public List<Province> getByCountry(String countryId) throws Exception {
		// List<ProvinceVO> provinceVOs = new ArrayList<ProvinceVO>();
		String hql = "From Province p where p.country = " + countryId;
		List<Province> provinces = provinceDao.find(hql);
		// provinceVOs = this.copy2VO(provinces);
		return provinces;
	}

	public List<ProvinceVO> copy2VO(List<Province> provinces) throws Exception {
		List<ProvinceVO> provinceVOs = new ArrayList<ProvinceVO>();
		for (Province province : provinces) {
			ProvinceVO provinceVO = new ProvinceVO();
			BeanUtil.copyProperties(province, provinceVO);
			provinceVO.setProvinceId(province.getId());
			provinceVOs.add(provinceVO);
		}
		return provinceVOs;
	}

}
