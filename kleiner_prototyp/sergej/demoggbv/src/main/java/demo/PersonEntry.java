package demo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PersonEntry {

	private @Id @GeneratedValue Long id;
	private String name, lastname, text;
	private Date date;

	public PersonEntry(String name, String lastname, String text) {
		this.name = name;
		this.lastname = lastname;
		this.text = text;
		this.date = new Date();
	}

	PersonEntry() {}

	public String getName() {
		return name;
	}

	public String getLastname() {
		return lastname;
	}
	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getText() {
		return text;
	}
}

