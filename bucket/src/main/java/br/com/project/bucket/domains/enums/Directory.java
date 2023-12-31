package br.com.project.bucket.domains.enums;

public enum Directory {
	USER("users"),
	COMPANY("companies"),
	EMPLOYEE("employees"),
	SHARED("shared");
	
	private String value;
	private Directory(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}
	
}
