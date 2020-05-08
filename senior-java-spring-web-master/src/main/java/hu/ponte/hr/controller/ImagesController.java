package hu.ponte.hr.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.ponte.hr.exception.ImageNotFoundException;
import hu.ponte.hr.services.ImageStore;

@RestController()
@RequestMapping("api/images")
public class ImagesController {

	private static final Logger log = LoggerFactory.getLogger(ImagesController.class);

	@Autowired
	private ImageStore imageStore;

	@GetMapping("meta")
	public List<ImageMeta> listImages() {

		List<ImageMeta> imageList = imageStore.getAllImageMeta();

		if (imageList.size() > 0) {
			return imageList;
		}

		return Collections.emptyList();
	}

	@GetMapping("preview/{id}")
	public void getImage(@PathVariable("id") String id, HttpServletResponse response)
			throws IOException, ImageNotFoundException {

		log.info("get id:" + id);

		response.setStatus(HttpStatus.OK.value());
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(imageStore.getImageMetaById(id));
		out.flush();
	}
}
