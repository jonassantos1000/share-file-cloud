package br.com.project.bucket.domains;

public class InfoFile {
	private String fileName;
	private String extension;

	public InfoFile() {

	}

	public InfoFile(String fileName, String extension) {
		this.fileName = fileName;
		this.extension = extension;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getFileNameWithExtesion() {
		return getFileName() + getExtension();
	}
}
