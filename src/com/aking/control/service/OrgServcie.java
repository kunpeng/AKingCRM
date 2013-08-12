package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.OrgDao;
import com.aking.model.subject.Org;
import com.aking.util.BeanUtil;
import com.aking.view.vo.OrgVO;

@Component
public class OrgServcie extends BaseService<Org, String> {

	@Autowired
	private OrgDao orgDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return orgDao;
	}

	public List<OrgVO> getRoot() throws Exception {
		String hql = "From Org o where o.parent = null";
		List<Org> orgs = orgDao.find(hql);
		List<OrgVO> orgVOs = this.copy2VO(orgs);
		return orgVOs;
	}

	public Org getTop() throws Exception {
		String hql = "From Org o where o.parent = null";
		List<Org> orgs = orgDao.find(hql);
		if (orgs.size() > 0) {
			return orgs.get(0);
		}
		return null;
	}

	public List<Org> getByParent(String pId) throws Exception {
		List<Org> orgs = new ArrayList<Org>();
		String hql = "";
		if (pId == null || pId.length() == 0) {
			hql = "From Org o where o.parent = null";
		} else {
			hql = "From Org o where o.parent =" + pId;
		}
		orgs = orgDao.find(hql);
		return orgs;
	}

	private List<OrgVO> copy2VO(List<Org> orgs) throws Exception {
		List<OrgVO> orgVOs = new ArrayList<OrgVO>();
		for (Org org : orgs) {
			OrgVO orgVO = new OrgVO();
			BeanUtil.copyProperties(org, orgVO);
			List<Org> children = new ArrayList<Org>(org.getChildren());
			if (children.size() > 0) {
				List<OrgVO> childrenvo = this.copy2VO(children);
				orgVO.setChildren(childrenvo);
			}
			Org parent = org.getParent();
			orgVO.setParentId(parent == null ? null : parent.getId());
			orgVO.setParentName(parent == null ? null : parent.getName());
			orgVO.setParentCode(parent == null ? null : parent.getCode());
			orgVOs.add(orgVO);
		}
		return orgVOs;
	}
}
