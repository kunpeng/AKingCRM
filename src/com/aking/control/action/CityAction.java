package com.aking.control.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.CityService;
import com.aking.model.subject.City;
import com.aking.util.WriteResult;
import com.aking.view.vo.CityVO;

@Component
public class CityAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @preserve
	 */
	@Autowired
	private CityService cityService;

	public void loadForCombo() throws Exception {
		String provinceid = request.getParameter("provinceid");
		List<CityVO> cityVOs = null;// cityService.getByProvince(provinceid);
		// List<CityVO> cityVOs = cityService.loadForCombo();
		JSONArray cities = new JSONArray();
		for (CityVO cityVO : cityVOs) {
			JSONObject obj = new JSONObject();
			obj.element("id", cityVO.getId());
			obj.element("name", cityVO.getName());
			cities.add(obj);
		}

		WriteResult.outputResult(cities.toString(), response);

		// WriteResult.outPutListResult(cityVOs, ServletActionContext.getResponse());
	}

	public void loadAll4Combo() {
		try {
			String provinceId = request.getParameter("provinceid");
			List<City> cities = new ArrayList<City>();
			if (provinceId == null) {
				cities = cityService.loadAll();
			} else {
				cities = cityService.getByProvince(provinceId);
			}

			// List<CityVO> cityVOs = cityService.loadForCombo();
			JSONArray objs = new JSONArray();
			for (City city : cities) {
				JSONObject obj = new JSONObject();
				obj.element("id", city.getId());
				obj.element("name", city.getName());
				obj.element("label", city.getName());
				objs.add(obj);
			}

			WriteResult.outputResult(objs.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// WriteResult.outPutListResult(cityVOs, ServletActionContext.getResponse());
	}

	public void getByProvince() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String provinceId = request.getParameter("provinceId");
		List<CityVO> cityVOs = null;// cityService.getByProvince(provinceId);
		WriteResult.outPutListResult(cityVOs, ServletActionContext.getResponse());
	}

}
