package com.aking.control.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.SystemFunctionDao;
import com.aking.model.constant.Constants;
import com.aking.model.system.SystemFunction;
import com.aking.util.BeanUtil;
import com.aking.view.vo.SystemFunctionVO;

/**
 * @author AKing
 * @version 1.0
 */
@Component
public class SystemFunctionService extends BaseService<SystemFunction, java.lang.String> {

	@Autowired
	private SystemFunctionDao systemFunctionDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return systemFunctionDao;
	}

	public List<SystemFunctionVO> getRoot() throws Exception {
		String hql = "From SystemFunction where parent = null";
		List<SystemFunction> systemFunctions = systemFunctionDao.find(hql);
		return this.copy2VO(systemFunctions);
	}

	/**
	 * 加载功能配置文件
	 * 
	 * @throws Exception
	 */
	public void loadFunctionFromMap() throws Exception {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(Constants.FUNCTIONMAPPATH);
		Element root = doc.getRootElement();
		// Iterator<Element> iterator = root.elementIterator();
		List<SystemFunction> functions = this.getFunctionFromElement(root);
		for (SystemFunction systemFunction : functions) {
			systemFunctionDao.save(systemFunction);
		}
	}

	/**
	 * 解析功能配置文件，递归解析
	 * 
	 * @param element
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<SystemFunction> getFunctionFromElement(Element element) throws Exception {
		List<SystemFunction> functions = new ArrayList<SystemFunction>();
		for (Iterator<Element> iterator = element.elementIterator(); iterator.hasNext();) {
			SystemFunction function = new SystemFunction();
			Element nodeElement = iterator.next();
			Element codeElement = nodeElement.element("code");
			Element nameElement = nodeElement.element("name");
			function.setCode(codeElement.getText());
			function.setName(nameElement.getText());
			System.out.println(codeElement.getText() + "|" + nameElement.getText());
			// 解析子功能
			Element childElement = nodeElement.element("children");
			if (childElement != null) {
				List<SystemFunction> children = getFunctionFromElement(childElement);
				function.setChildren(new HashSet<SystemFunction>(children));
			}
			functions.add(function);
		}
		return functions;
	}

	public static void main(String[] args) {
		SystemFunctionService service = new SystemFunctionService();
		try {
			service.loadFunctionFromMap();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<SystemFunctionVO> copy2VO(List<SystemFunction> systemFunctions) throws Exception {
		List<SystemFunctionVO> systemFunctionVOs = new ArrayList<SystemFunctionVO>();
		for (SystemFunction systemFunction : systemFunctions) {
			SystemFunctionVO systemFunctionVO = new SystemFunctionVO();
			BeanUtil.copyProperties(systemFunction, systemFunctionVO);
			List<SystemFunction> children = new ArrayList<SystemFunction>(systemFunction.getChildren());
			if (children.size() > 0) {
				systemFunctionVO.setChildren(this.copy2VO(children));
			}
			systemFunctionVOs.add(systemFunctionVO);
		}
		return systemFunctionVOs;
	}

}
