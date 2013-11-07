package com.dotcms.peopleman.spring;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.dotcms.peopleman.model.Person;
import com.dotcms.peopleman.util.PersonUtil;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.DuplicateUserException;
import com.dotmarketing.cache.FieldsCache;
import com.dotmarketing.exception.DotDataException;
import com.dotmarketing.exception.DotSecurityException;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.portlets.structure.model.Field;
import com.dotmarketing.util.Logger;
import com.liferay.portal.model.User;

@EnableWebMvc
@Configuration
@RequestMapping("/personcontroller")
@Controller
public class PersonController {

	private User sysUser;

	public PersonController() {
		try {
			sysUser = APILocator.getUserAPI().getSystemUser();
		} catch (DotDataException e) {
			Logger.error(PersonController.class, e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/save")
	public String savePerson(@RequestParam Map<String, String> params) {
		Logger.info(this.getClass(), "Received request to Save");
		Person p;
		try {
			p = PersonUtil.getInstance().lookupPerson(params.get("userId"));
		} catch (DotDataException e) {
			Logger.error(PersonController.class, e.getMessage(), e);
			return "index.html";
		}
		Contentlet uc = p.getPersonContent();

		List<Field> fields = FieldsCache.getFieldsByStructureVariableName("person");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			for (Field field : fields) {
				if (key != null && key.equals(field.getVelocityVarName())) {
					APILocator.getContentletAPI().setContentletProperty(uc, field, value);
				}
			}
		}

		try {
			uc = APILocator.getContentletAPI().checkin(uc, sysUser, true);
			boolean isIndexed = APILocator.getContentletAPI().isInodeIndexed(uc.getInode(), 10);
			int counter = 0;
			while (!isIndexed) {
				isIndexed = APILocator.getContentletAPI().isInodeIndexed(uc.getInode(), 10);
				if (counter++ > 10)
					continue;
			}
		} catch (Exception e) {
			Logger.error(PersonController.class, e.getMessage() + " Unable to save Person Content", e);
		}
		return "redirect:/peopleman/index.html?r=" + Math.random();
	}

	@RequestMapping(value = "/load/{userId}", method = RequestMethod.GET)
	public String loadPerson(@PathVariable String userId, Model model) {
		Logger.info(this.getClass(), "Received request to Load Person");
		Person p;
		try {
			p = PersonUtil.getInstance().lookupPerson(userId);
		} catch (DotDataException e) {
			Logger.error(PersonController.class, e.getMessage(), e);
			return "index.html";
		}
		model.addAttribute("person", p);
		return "edit";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String loadPerson(Model model) {
		Logger.info(this.getClass(), "Received request to add Person");

		return "add";
	}

	@RequestMapping(value = "/saveAdd")
	public String addSavePerson(@RequestParam Map<String, String> params) throws DuplicateUserException, DotDataException,
			DotSecurityException {
		try {
			Logger.info(this.getClass(), "Received request to Add");

			User user = APILocator.getUserAPI().createUser(null, params.get("email"));

			user.setFirstName(params.get("firstName"));
			user.setLastName(params.get("lastName"));
			user.setPassword(params.get("password"));

			APILocator.getUserAPI().save(user, sysUser, false);

			Contentlet uc = new Contentlet();
			uc.setStringProperty("userid", user.getUserId());
			uc.setStructureInode("9dee9f0a-93d1-4b7c-9329-9810da35ab0f");
			uc.setLanguageId(1);

			List<Field> fields = FieldsCache.getFieldsByStructureVariableName("person");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				for (Field field : fields) {
					if (key != null && key.equals(field.getVelocityVarName())) {
						APILocator.getContentletAPI().setContentletProperty(uc, field, value);
					}
				}
			}

			uc = APILocator.getContentletAPI().checkin(uc, sysUser, true);
			boolean isIndexed = APILocator.getContentletAPI().isInodeIndexed(uc.getInode(), 10);
			int counter = 0;
			while (!isIndexed) {
				isIndexed = APILocator.getContentletAPI().isInodeIndexed(uc.getInode(), 10);
				if (counter++ > 10)
					continue;
			}
		} catch (Exception e) {
			Logger.error(PersonController.class, e.getMessage() + " Unable to save Person Content", e);
			return "redirect:/app/spring/personcontroller/add?e=" + java.net.URLEncoder.encode(e.toString());
		}
		return "redirect:/peopleman/index.html?r=" + Math.random();
	}

}
