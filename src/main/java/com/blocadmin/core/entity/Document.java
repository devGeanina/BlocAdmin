package com.blocadmin.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "docs")
public class Document extends CommonEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "doc", nullable = false)
    private byte[] doc;
	
	@Column(name = "content_type", nullable = false)
	private String contentType;

	public Document(){}
	
	public Document(String name, byte[] doc, String contentType) {
		super();
		this.name = name;
		this.doc = doc;
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getDoc() {
		return doc;
	}

	public void setDoc(byte[] doc) {
		this.doc = doc;
	}

	@Override
	public String toString() {
		return "Document [name=" + name + "]";
	}
}
