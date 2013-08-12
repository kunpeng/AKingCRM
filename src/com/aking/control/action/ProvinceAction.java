package com.aking.control.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.ProvinceService;
import com.aking.model.subject.Province;
import com.aking.util.WriteResult;
import com.aking.view.vo.ProvinceVO;

@Component
public class ProvinceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @preserve
	 */
	@Autowired
	private ProvinceService provinceService;

	public void loadForCombo() throws Exception {
		String countryid = request.getParameter("countryid");
		List<ProvinceVO> provinceVOs = null;// provinceService.getByCountry(countryid);
		JSONArray provinces = new JSONArray();
		for (ProvinceVO provinceVO : provinceVOs) {
			JSONObject obj = new JSONObject();
			obj.element("id", provinceVO.getId());
			obj.element("name", provinceVO.getName());
			provinces.add(obj);
		}
		WriteResult.outputResult(provinces.toString(), response);
		// List<ProvinceVO> provinceVOs = provinceService.loadForCombo();

		// WriteResult.outPutListResult(provinceVOs, ServletActionContext.getResponse());
	}

	public void loadAll4Combo() {
		try {
			String countryId = request.getParameter("countryid");
			JSONArray objs = new JSONArray();
			List<Province> provinces = new ArrayList<Province>();
			if (countryId == null) {
				provinces = provinceService.loadAll();
			} else {
				provinces = provinceService.getByCountry(countryId);
			}
			for (Province province : provinces) {
				JSONObject obj = new JSONObject();
				obj.element("id", province.getId());
				obj.element("name", province.getName());
				obj.element("label", province.getName());
				obj.element("country", province.getCountry().getId());
				objs.add(obj);
			}
			WriteResult.outputResult(objs.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getByCountry() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String countryId = request.getParameter("countryId");
		List<ProvinceVO> provinceVOs = null;// provinceService.getByCountry(countryId);
		WriteResult.outPutListResult(provinceVOs, ServletActionContext.getResponse());
	}

}
