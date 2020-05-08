package hu.ponte.hr.services;

import static org.junit.Assert.assertFalse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.exception.FileSizeTooLargeException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageStoreTest {

	@Autowired
	private ImageStore is;
	private String pathCat = "src/test/resources/images/cat.jpg";
	private String pathRnd = "src/test/resources/images/rnd.jpg";
	private String pathBs = "src/test/resources/images/bigsize.jpg";
	private List<ImageMeta> list = Collections.emptyList();
	
	private MockMultipartFile generateMockFile(String path) throws FileNotFoundException, IOException {
		String fileName = FilenameUtils.getName(path);
		return new MockMultipartFile("file",fileName,"image/jpeg",new FileInputStream(path));
	}

	@Before
	public void init() throws FileNotFoundException, IOException {
		is.generateImageMeta(generateMockFile(pathCat));
		is.generateImageMeta(generateMockFile(pathRnd));
		list = is.getAllImageMeta();
	}

	@Test
	public void testGetAllImageMeta() {
		assertFalse("Get all ImageMeta",is.getAllImageMeta().isEmpty());
	}

	@Test
	public void testGenerateImageMeta() throws FileNotFoundException, IOException {
		is.generateImageMeta(generateMockFile(pathCat));
	}
	
	@Test(expected = FileSizeTooLargeException.class)
	public void testGenerateWrongSizeImageMeta() throws FileNotFoundException, IOException {
		is.generateImageMeta(generateMockFile(pathBs));
	}

	@Test
	public void testGetImageMetaById() {
		is.getImageMetaById(list.get(0).getId());
	}

}
