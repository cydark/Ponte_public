package hu.ponte.hr.services;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import hu.ponte.hr.controller.ImageMeta;
import hu.ponte.hr.exception.FileSizeTooLargeException;
import hu.ponte.hr.exception.GenerateImageMetaException;
import hu.ponte.hr.exception.GenerateSignatureException;
import hu.ponte.hr.exception.ImageNotFoundException;
import hu.ponte.hr.repository.ImageRepository;

@Service
public class ImageStore {
	
	@Value("${filesize.max}")
	private long fileSizeMax;
	@Autowired
	private SignService signService;

	@Autowired
	private ImageRepository repository;
	
	private static final Logger log = LoggerFactory.getLogger(ImageStore.class);

	public List<ImageMeta> getAllImageMeta() {
		return repository.findAll();
	}

	public void generateImageMeta(MultipartFile file) {
		log.warn("generate ImageMeta:"+file.getName() +" "+file.getSize());
		
		if (file.getSize() > fileSizeMax) {
			log.warn(file.getOriginalFilename() + " file size is too large: " + file.getSize() + ", maximum upload size: 2 MB");
			throw new FileSizeTooLargeException(file);
		}
		ImageMeta imageMeta = new ImageMeta();
		imageMeta.setName(file.getOriginalFilename());
		imageMeta.setSize(file.getSize());
		imageMeta.setMimeType(file.getContentType());
		try {
			//TODO nem volt a kiirásban de célszerű tömöríteni az állomány tartalmát ha megjelenik szempontként a tárolás.... 
			imageMeta.setImage(Base64.getEncoder().encode(file.getBytes()));
			imageMeta.setDigitalSign(signService.generateSignature(file.getBytes()));
		} catch (GenerateSignatureException e) {
			//TODO a kiirásban az szerepelt hogy a signózás bónusz feladat ezért csak naplózási szintű hibaként veszem fel
			log.warn(file.getName() +" "+e.getMessage());
			imageMeta.setDigitalSign("Hiányzó signo!");
			
		} catch (IOException e) {
			log.error("Generate ImageMeta unsuccessfully: (" +file.getOriginalFilename() +") size:" +file.getSize() +" "+e.getMessage() );
			throw new GenerateImageMetaException(file);
		}
		repository.save(imageMeta);
		
	}

	public ImageMeta getImageMetaById(String id) throws ImageNotFoundException {
		return repository.findById(id).orElseThrow(() -> new ImageNotFoundException(id));
	}
}
