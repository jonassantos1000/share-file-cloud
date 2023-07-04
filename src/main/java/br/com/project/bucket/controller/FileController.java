package br.com.project.bucket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.bucket.domains.DataFileSave;
import br.com.project.bucket.domains.InfoFile;
import br.com.project.bucket.domains.ResponseData;
import br.com.project.bucket.domains.enums.Directory;
import br.com.project.bucket.service.StorageService;

@RestController
@RequestMapping(value = "/files")
public class FileController {

	@Autowired
	private StorageService service;

	@PostMapping(value = "/{directory}/{directoryId}")
	public ResponseEntity<DataFileSave> saveFile(@PathVariable String directory, @PathVariable String directoryId, @RequestBody InfoFile file){
		try {
			DataFileSave dataFile = service.saveFile(Directory.valueOf(directory.toUpperCase()), directoryId, file);
			return ResponseEntity.status(HttpStatus.CREATED).body(dataFile);
		}catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Diretório informado é inválido.");
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> removeFile(@PathVariable String id){
		service.deleteFileById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponseData> getFileById(@PathVariable String id){
		return ResponseEntity.ok(service.findFileById(id));
	}
	
	@GetMapping(value = "/{directory}/{directoryId}")
	public ResponseEntity<List<ResponseData>> getDirectory(@PathVariable String directory, @PathVariable String directoryId){
		List<ResponseData> listResponse = service.findDirectory(Directory.valueOf(directory.toUpperCase()), directoryId);
		return ResponseEntity.ok(listResponse);
	}
}
