package br.com.shared.file.cloud.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.shared.file.cloud.api.domain.Upload;

public interface UploadRepository extends JpaRepository<Upload, Long> {
	Upload findByToken(String token);
}
