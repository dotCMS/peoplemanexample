package com.dotcms.peopleman.model;

import com.liferay.portal.model.User;

public class Person {

	private User user = null;
	private boolean male = false;
	private String notes;
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
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
