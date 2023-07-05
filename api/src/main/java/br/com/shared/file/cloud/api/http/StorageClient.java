package br.com.shared.file.cloud.api.http;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.shared.file.cloud.api.domain.vo.StorageFilesVO;
import br.com.shared.file.cloud.api.domain.vo.StorageUploadVO;
import br.com.shared.file.cloud.api.domain.vo.UploadVO;

@FeignClient("storage-ms")
public interface StorageClient {

	@PostMapping("/files/lote/{directory}/{id}")
	StorageUploadVO saveUpload(@PathVariable("directory") String directory, @PathVariable("id") String id,
			@RequestBody List<UploadVO> files);
	
	@GetMapping("/files/lote/{directoryId}")
	List<StorageFilesVO> getUpload(@PathVariable("directoryId") String id);
}
