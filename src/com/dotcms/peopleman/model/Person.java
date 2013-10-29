package com.dotcms.peopleman.model;

import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.liferay.portal.model.User;

public class Person {

	private User user = null;
	private boolean male = false;
	private String notes;
	private Contentlet personContent;
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the male
	 */
	public boolean isMale() {
		return male;
	}
	/**
	 * @param male the male to set
	 */
	public void setMale(boolean male) {
		this.male = male;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	public String printGender(){
		if(personContent == null){
			return "";
		}
		if(isMale()){
			return "Male";
		}else{
			return "Female";
		}
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return the personContent
	 */
	public Contentlet getPersonContent() {
		return personContent;
	}
	/**
	 * @param personContent the personContent to set
	 */
	public void setPersonContent(Contentlet personContent) {
		this.personContent = personContent;
	}
}
