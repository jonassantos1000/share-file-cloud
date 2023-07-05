package br.com.project.bucket.domains;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class DataFileSave {

	private String id;
	private String name;

	public DataFileSave() {

	}
	
	public DataFileSave(String id) {
		this(id, StringUtils.EMPTY);
	}

	public DataFileSave(String id, String name) {
		this.id = id;
		this.name = name;
		encodeDirectory();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
        this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private void encodeDirectory() {
		this.id = Base64.encodeBase64String(this.id.getBytes(StandardCharsets.UTF_8));
	}

}
