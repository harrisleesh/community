package org.kiworkshop.community.file.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiworkshop.community.file.domain.service.S3Uploader;
import org.kiworkshop.community.file.dto.FileUrlResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(S3Controller.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class S3ControllerTest {

  private @Autowired MockMvc mvc;
  private @MockBean S3Uploader s3Uploader;

  @Test
  void upload_ValidInput_ValidOutput() throws Exception {
    List<String> fileNames = Arrays.asList("bob.jpeg", "bob.jpeg", "bob.jpeg");
    FileUrlResponseDto fileUrlResponseDto = new FileUrlResponseDto(fileNames);
    when(s3Uploader.upload(anyList(), anyString())).thenReturn(fileUrlResponseDto);
    MockMultipartFile file = new MockMultipartFile(
        "data", "/resources/upload/bob.jpeg", "multipart/form-data", "bob.jpeg".getBytes());

    this.mvc.perform(fileUpload("/uploads")
        .file(file)
        .file(file)
        .file(file))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.urls", is(fileNames)))
        .andDo(document("files/upload-files",
            preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
  }
}