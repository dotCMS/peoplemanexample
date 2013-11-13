package com.dotcms.peopleman.viewtools;

import java.util.ArrayList;
import java.util.List;

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

public class UserTool implements ViewTool {

	private User sysUser;
	private Field genderField;
	private Field notesField;
	
	@Override
	public void init(Object initData) {
		try {
			sysUser = APILocator.getUserAPI().getSystemUser();
		} catch (DotDataException e) {
			Logger.error(UserTool.class,e.getMessage(),e);
		}
		List<Field> fields = FieldsCache.getFieldsByStructureVariableName("person");
		for (Field field : fields) {
			if(field.getVelocityVarName().equalsIgnoreCase("gender")){
				genderField = field;
			}else if(field.getVelocityVarName().equalsIgnoreCase("notes")){
				notesField = field;
			}
		}
	}

	public List<Person> getUsersAnRolesByName(String filter, int start,int limit) {
		List<User> users;
		List<Person> people = new ArrayList<Person>();
		try {
			users = APILocator.getUserAPI().getUsersByName(filter, start, limit, sysUser, true);
		} catch (DotDataException e) {
			Logger.error(this, e.getMessage(),e);
			return new ArrayList<Person>();
		}
		for (User user : users) {
			Person p = new Person();
			p.setUser(user);
			try {
				List<Contentlet> ucs = APILocator.getContentletAPI().search("+type:content +working:true +structurename:person +person.userid:" + user.getUserId(), -1, 0, "moddate", sysUser, true);
				if(ucs.size()>0){
					Contentlet uc = ucs.get(0);
					p.setMale(APILocator.getContentletAPI().getFieldValue(uc, genderField).equals("Male")?true:false);
					p.setNotes(APILocator.getContentletAPI().getFieldValue(uc, notesField).toString());
					p.setPersonContent(uc);
				}
				people.add(p);
			} catch (DotDataException e) {
				Logger.error(UserTool.class,e.getMessage(),e);
			} catch (DotSecurityException e) {
				Logger.error(UserTool.class,e.getMessage(),e);
			}
		}
		return people;
	}

}
