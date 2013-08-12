package com.aking.control.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.CountryService;
import com.aking.util.WriteResult;
import com.aking.view.vo.CountryVO;

@Component
public class CountryAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @preserve
	 */
	@Autowired
	private CountryService countryService;

	public void loadForCombo() throws Exception {
		List<CountryVO> countryVOs = countryService.loadForCombo();
		JSONArray countrys = new JSONArray();
		for (CountryVO countryVO : countryVOs) {
			JSONObject countryObj = new JSONObject();
			countryObj.element("id", countryVO.getId());
			countryObj.element("name", countryVO.getName());
			countryObj.element("label", countryVO.getName());
			countrys.add(countryObj);
		}

		WriteResult.outputResult(countrys.toString(), response);

		// WriteResult.outPutListResult(countryVOs, ServletActionContext.getResponse());
	}

}
