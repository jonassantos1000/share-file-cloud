package br.com.project.bucket.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import br.com.project.bucket.domains.DataFileSave;
import br.com.project.bucket.domains.InfoFile;
import br.com.project.bucket.domains.enums.Directory;
import br.com.project.bucket.storages.AbstractStorage;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class FileControllerTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	private JacksonTester<InfoFile> infoFileJson;

	@Autowired
	private JacksonTester<DataFileSave> dataFileSaveJson;
	
	@MockBean
	private AbstractStorage storage; 

	private static final String fileName = "test.txt";
	private static final Directory directory = Directory.SHARED;
	private static final String directoryId = "123";
	private static final String directoryFull = directory.getValue().concat("/").concat(directoryId).concat("/").concat(fileName);
	private static final String base64 = Base64.encodeBase64String("testebasae64".getBytes(StandardCharsets.UTF_8));
	private static final String idFileInvalid = "0000000";
	private static final String idFileValid = Base64.encodeBase64String(fileName.getBytes(StandardCharsets.UTF_8));
	
	@Test
	@DisplayName("The method should return a 400 code, indicating a request without a file in the body. / "
			+ "o metodo deveria retornar codigo 400, requisição sem arquivo no corpo")
	void saveFile1() throws Exception {
		MockHttpServletResponse response = mvc.perform(post("/files/%s/%s".formatted(directory, directoryId)))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	@DisplayName("The method should save the file and return a 200 code. / "
			+ "o metodo deveria salvar o aquivo e retornar codigo 200")
	void saveFile2() throws Exception {
		when(storage.saveFile(any(), any(), any())).thenReturn(getDateFileSave());
		
		MockHttpServletResponse response = mvc.perform(post("/files/%s/%s".formatted(directory.getValue(), directoryId))
				.contentType(MediaType.APPLICATION_JSON)
				.content(infoFileJson.write(getInfoFile()).getJson()))
				.andReturn().getResponse();
		
		String responseExpected = dataFileSaveJson.write(getDateFileSave()).getJson();
		
		assertEquals(responseExpected, response.getContentAsString());
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
	
	@Test
	@DisplayName("The response of the request should indicate that no file was found with an invalid ID.. / "
			+ "A resposta da requisição deve retornar 404 informando que não encontrou um arquivo com id invalido")
	void deleteFile1() throws Exception {
		when(storage.deleteFileById(idFileInvalid)).thenReturn(false);
		
		MockHttpServletResponse response = mvc.perform(delete("/files/%s".formatted(idFileInvalid)))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	@DisplayName("The response of the request should indicate that no file was found with an invalid ID.. / "
			+ "A resposta da requisição deve retornar 204 informando que o arquivo foi excluido")
	void deleteFile2() throws Exception {
		when(storage.deleteFileById(fileName)).thenReturn(true);
		
		MockHttpServletResponse response = mvc.perform(delete("/files/%s".formatted(idFileValid)))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}

	private InfoFile getInfoFile() {
		return new InfoFile(fileName, base64);
	}
	
	private DataFileSave getDateFileSave() {
		return new DataFileSave(directoryFull);
	}

}
