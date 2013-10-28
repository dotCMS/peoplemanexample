package com.dotcms.peopleman.spring;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.dotcms.peopleman.model.Person;
import com.dotcms.peopleman.viewtools.UserTool;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.NoSuchUserException;
import com.dotmarketing.cache.FieldsCache;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.portlets.structure.model.Field;
import com.dotmarketing.util.Logger;
import com.liferay.portal.model.User;

@EnableWebMvc
@Configuration
@RequestMapping ("/usercontroller")
@Controller
public class UserController {

	private User sysUser;
	private Field genderField;
	private Field notesField;
	
	public UserController() {
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
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savePerson() {
    	Logger.info( this.getClass(), "Received request to Save" );
        
        return "index";
    }

	@RequestMapping(value = "/load/{userId}", method = RequestMethod.GET)
    public String loadPerson(@PathVariable String userId, Model model) {
    	Logger.info( this.getClass(), "Received request to Load Person" );
    	User user;
        try {
			user = APILocator.getUserAPI().loadUserById(userId);
		} catch (NoSuchUserException e) {
			Logger.error(UserController.class,e.getMessage(),e);
			return "index";
		} catch (DotDataException e) {
			Logger.error(UserController.class,e.getMessage(),e);
			return "index";
		} catch (DotSecurityException e) {
			Logger.error(UserController.class,e.getMessage(),e);
			return "index";
		}
        Person p = new Person();
        p.setUser(user);
        List<Contentlet> ucs;
		try {
			ucs = APILocator.getContentletAPI().search("+type:content +structurename:person +person.userid:" + user.getUserId(), -1, 0, "moddate", sysUser, true);
			if(ucs.size()>0){
				Contentlet uc = ucs.get(0);
				p.setMale(APILocator.getContentletAPI().getFieldValue(uc, genderField).equals("Male")?true:false);
				p.setNotes(APILocator.getContentletAPI().getFieldValue(uc, notesField).toString());
			}
		} catch (DotDataException e) {
			Logger.error(UserController.class,e.getMessage(),e);
		} catch (DotSecurityException e) {
			Logger.error(UserController.class,e.getMessage(),e);
		}
		model.addAttribute("person", p);
        return "edit";
    }
	
}
