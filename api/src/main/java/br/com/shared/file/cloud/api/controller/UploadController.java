package br.com.shared.file.cloud.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.shared.file.cloud.api.domain.dto.UploadDTO;
import br.com.shared.file.cloud.api.domain.vo.StorageFilesVO;
import br.com.shared.file.cloud.api.domain.vo.UploadVO;
import br.com.shared.file.cloud.api.service.UploadService;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

	@Autowired
	UploadService service;

	@PostMapping
	public ResponseEntity<UploadDTO> executeUpload(@RequestBody List<UploadVO> files) {
		return ResponseEntity.ok(new UploadDTO(service.saveUpload(files)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<StorageFilesVO>> listUpload(@PathVariable String id) {
		return ResponseEntity.ok(service.findUpload(id));
	}
}
