package br.com.shared.file.cloud.api.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.shared.file.cloud.api.domain.vo.StorageUploadVO;

@Entity
public class Upload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime createDate;
	private String storageId;
	private String token;

	public Upload() {
	}
	
	public Upload(String token, StorageUploadVO storageUpload) {
		this(null, token, storageUpload.getId());	
	}

	public Upload(Long id, String token, String storageId) {
		this.id = id;
		this.token = token;
		this.createDate = LocalDateTime.now();
		this.storageId = storageId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getStorageId() {
		return storageId;
	}

	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, storageId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Upload other = (Upload) obj;
		return Objects.equals(id, other.id) && Objects.equals(storageId, other.storageId);
	}

}
