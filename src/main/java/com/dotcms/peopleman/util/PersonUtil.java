package com.dotcms.peopleman.util;

import java.util.List;

import com.dotcms.peopleman.model.Person;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.util.Logger;
import com.liferay.portal.model.User;

public class PersonUtil {
	private PersonUtil() {
	};

	private static final PersonUtil INSTANCE = new PersonUtil();

	public static PersonUtil getInstance() {

		return INSTANCE;

	}

	public Person lookupPerson(String userId) throws DotDataException {
		User user, sysUser;

		try {
			user = APILocator.getUserAPI().loadUserById(userId);
			sysUser = APILocator.getUserAPI().getSystemUser();
		} catch (Exception e) {
			Logger.error(PersonUtil.class, e.getMessage(), e);
			throw new DotDataException(e.getMessage(), e);
		}
		Person p = newPerson() ;
		p.setUser(user);
		List<Contentlet> ucs;
		try {
			ucs = APILocator.getContentletAPI().checkout("+type:content +structurename:person +person.userid:" + user.getUserId(), sysUser,
					true, 0, -1);
			Contentlet uc = null;
			if (ucs.size() > 0) {
				uc = ucs.get(0);

			} else {
				uc = new Contentlet();
				uc.setStringProperty("userid", userId);
				uc.setStructureInode("9dee9f0a-93d1-4b7c-9329-9810da35ab0f");
				uc.setLanguageId(1);
			}
			p.setPersonContent(uc);
		} catch (Exception e) {
			Logger.error(PersonUtil.class, e.getMessage(), e);
			throw new DotDataException(e.getMessage(), e);
		}
		return p;
	}
	
	public Person newPerson() throws DotDataException {
		Person p = new Person();

		Contentlet uc = new Contentlet();
		uc.setStructureInode("9dee9f0a-93d1-4b7c-9329-9810da35ab0f");
		uc.setLanguageId(1);
		p.setPersonContent(uc);
		return p;
	}
	

}
