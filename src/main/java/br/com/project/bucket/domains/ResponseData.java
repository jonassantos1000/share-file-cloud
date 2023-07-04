package br.com.project.bucket.domains;

public class ResponseData {
	private String id;
	private String name;
	private String file;
	private boolean result;

	public ResponseData() {

	}

	public ResponseData(String id, String name, String file, boolean result) {
		this.id = id;
		this.name = name;
		this.file = file;
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isResult() {
		return result;
	}

	public String getFile() {
		return file;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

}