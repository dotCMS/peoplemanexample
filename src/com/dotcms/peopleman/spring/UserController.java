package com.dotcms.peopleman.spring;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.dotcms.peopleman.model.Person;
import com.dotcms.peopleman.viewtools.UserTool;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.cache.FieldsCache;
import com.dotmarketing.exception.DotDataException;
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
	private Field userIdField;
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
			}else if(field.getVelocityVarName().equalsIgnoreCase("userid")){
				userIdField = field;
			}
		}
	}
	
	@RequestMapping(value = "/save")
    public String savePerson(@RequestParam String gender, @RequestParam String notes, @RequestParam String userId) {
    	Logger.info( this.getClass(), "Received request to Save" );
    	Person p;
    	try {
			p = lookupPerson(userId);
		} catch (DotDataException e) {
			Logger.error(UserController.class,e.getMessage(),e);
			return "index.html";
		}
    	Contentlet uc = p.getPersonContent();
    	if(uc == null){
    		uc = new Contentlet();
    		uc.setStructureInode("9dee9f0a-93d1-4b7c-9329-9810da35ab0f");
    		uc.setLanguageId(1);
    	}
        APILocator.getContentletAPI().setContentletProperty(uc, genderField, gender);
        APILocator.getContentletAPI().setContentletProperty(uc, userIdField, userId);
        APILocator.getContentletAPI().setContentletProperty(uc, notesField, notes);
        try {
			uc = APILocator.getContentletAPI().checkin(uc, sysUser, true);
			APILocator.getContentletAPI().isInodeIndexed(uc.getInode(), 10);
		} catch (Exception e) {
			Logger.error(UserController.class,e.getMessage() + " Unable to save Person Content",e);
		}
        return "redirect:/peopleman/index.html";
    }

	@RequestMapping(value = "/load/{userId}", method = RequestMethod.GET)
    public String loadPerson(@PathVariable String userId, Model model) {
    	Logger.info( this.getClass(), "Received request to Load Person" );
    	Person p;
		try {
			p = lookupPerson(userId);
		} catch (DotDataException e) {
			Logger.error(UserController.class,e.getMessage(),e);
			return "index.html";
		}
		model.addAttribute("person", p);
        return "edit";
    }
	
	private Person lookupPerson(String userId) throws DotDataException{
		User user;
        try {
			user = APILocator.getUserAPI().loadUserById(userId);
		} catch (Exception e) {
			Logger.error(UserController.class,e.getMessage(),e);
			throw new DotDataException(e.getMessage(), e);
		}
		Person p = new Person();
        p.setUser(user);
        List<Contentlet> ucs;
		try {
			ucs = APILocator.getContentletAPI().checkout("+type:content +structurename:person +person.userid:" + user.getUserId(), sysUser, true,0,-1);
			if(ucs.size()>0){
				Contentlet uc = ucs.get(0);
				p.setPersonContent(uc);
				p.setMale(APILocator.getContentletAPI().getFieldValue(uc, genderField).equals("Male")?true:false);
				p.setNotes(APILocator.getContentletAPI().getFieldValue(uc, notesField).toString());
			}
		} catch (Exception e) {
			Logger.error(UserController.class,e.getMessage(),e);
			throw new DotDataException(e.getMessage(), e);
		}
		return p;
	}
}
