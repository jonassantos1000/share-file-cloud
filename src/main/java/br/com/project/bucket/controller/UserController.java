package br.com.project.bucket.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.project.bucket.domains.InfoFile;
import br.com.project.bucket.domains.ResponseData;
import br.com.project.bucket.domains.enums.Directory;
import br.com.project.bucket.service.StorageService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private StorageService service;

	@PostMapping(value = "/{id}")
	public ResponseEntity<ResponseData> saveFile(@PathVariable String id, @RequestBody InfoFile file) throws URISyntaxException{
		String path = service.saveFile(Directory.USER, id, file);
		URI uri = new URI(path);
		return ResponseEntity.created(uri).body(new ResponseData(file.getFileName(), path, true));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ResponseData> removeFile(@PathVariable String id, @RequestBody InfoFile infoFile){
		return ResponseEntity.ok(new ResponseData(infoFile.getFileName(), infoFile.getFileName(), service.deleteFile(Directory.USER, id, infoFile.getFileName())));
	}
	
	@GetMapping(value = "/{name}")
	public ResponseEntity<ResponseData> getFileByName(@PathVariable String name, @RequestBody InfoFile infoFile){
		return ResponseEntity.ok(service.findFileByName(Directory.USER, name, infoFile.getFileName()));
	}
	
	@GetMapping(value = "/directory/{id}")
	public ResponseEntity<List<ResponseData>> getDirectory(@PathVariable String id){
		List<ResponseData> listResponse = service.findDirectory(Directory.USER, id);
		return ResponseEntity.ok(listResponse);
	}
}
