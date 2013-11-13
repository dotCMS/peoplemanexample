package com.dotcms.peopleman.viewtools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.velocity.tools.view.tools.ViewTool;

import com.dotcms.peopleman.model.Person;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.cache.FieldsCache;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.portlets.structure.model.Field;
import com.dotmarketing.util.Logger;
import com.liferay.portal.model.User;

public class PersonTool implements ViewTool {

	private User sysUser;
	private Field genderField;
	private Field notesField;

	@Override
	public void init(Object initData) {
		try {
			sysUser = APILocator.getUserAPI().getSystemUser();
		} catch (DotDataException e) {
			Logger.error(PersonTool.class, e.getMessage(), e);
		}
		List<Field> fields = FieldsCache.getFieldsByStructureVariableName("person");
		for (Field field : fields) {
			if (field.getVelocityVarName().equalsIgnoreCase("gender")) {
				genderField = field;
			} else if (field.getVelocityVarName().equalsIgnoreCase("notes")) {
				notesField = field;
			}
		}
	}

	public List<Person> getPeople(String filter, int start, int limit) {
		List<User> users = new ArrayList<User>();
		List<Person> people = new ArrayList<Person>();
		Set<User> systemUsers = new HashSet<User>();

		try {
			users = APILocator.getUserAPI().getUsersByName(filter, start, limit, sysUser, true);
			systemUsers.add(APILocator.getUserAPI().getAnonymousUser());
			systemUsers.add(APILocator.getUserAPI().getDefaultUser());
			systemUsers.add(APILocator.getUserAPI().getSystemUser());
		} catch (DotDataException e) {
			Logger.error(this, e.getMessage(), e);

		}
		for (User user : users) {

			if (systemUsers.contains(user)) {
				continue;
			}

			Person p = new Person();
			p.setUser(user);
			try {
				List<Contentlet> ucs = APILocator.getContentletAPI().search("+type:content +working:true +structurename:person +person.userid:" + user.getUserId(), -1, 0, "moddate", sysUser, true);
				if (ucs.size() > 0) {
					Contentlet uc = ucs.get(0);

					p.setPersonContent(uc);
				}
				people.add(p);
			} catch (DotDataException e) {
				Logger.error(PersonTool.class, e.getMessage(), e);
			} catch (DotSecurityException e) {
				Logger.error(PersonTool.class, e.getMessage(), e);
			}
		}
		return people;
	}

}
