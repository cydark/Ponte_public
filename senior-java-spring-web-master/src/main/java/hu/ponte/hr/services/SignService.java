package hu.ponte.hr.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import hu.ponte.hr.exception.GenerateSignatureException;

@Service
//FIXME időbélyeg nincs figyelembe véve (az example-ben ugyanaz a signatura két valószínűleg eltérő időben feltölétött ugyanazon filenak)    
public class SignService {

	private static final Logger log = LoggerFactory.getLogger(SignService.class);

	@Value("${keystore.type}")
	private String keystoreType;

	@Value("${key.private.resource}")
	private Resource privateKeyResource;
	
	@Value("${signature.instance}")
	private String signatureInstance;
	
	private PrivateKey getPrivateKey() throws Exception {
		byte[] keyBytes = Files.readAllBytes(Paths.get(this.privateKeyResource.getURI()));
		// RSA
		KeyFactory keyFactory = KeyFactory.getInstance(keystoreType);
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
	}

	public String generateSignature(byte[] src) throws GenerateSignatureException {
		try {
			// SHA256withRSA
			Signature signature = Signature.getInstance(signatureInstance);
			signature.initSign(getPrivateKey());
			//FIXME itt célszerű lenne csak a hasht signálni!!!  
			signature.update(src);
			return Base64.getEncoder().encodeToString(signature.sign());

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GenerateSignatureException(e.getMessage());
		}
	}
	
	//TODO
	public void generateSignatureTest(String fileName) throws GenerateSignatureException {
		try {
			// SHA256withRSA
			Signature signature = Signature.getInstance(signatureInstance);
			signature.initSign(getPrivateKey());
			byte[] bytes = fileName.getBytes();
		      
		      //Adding data to the signature
			signature.update(bytes);
			System.out.println(Base64.getEncoder().encodeToString(signature.sign()));
//			signature.initVerify(getPublicKey());
			signature.update(bytes);
		      
		      //Verifying the signature
		      boolean bool = signature.verify(bytes);
		      
		      if(bool) {
		          System.out.println("Signature verified");   
		       } else {
		          System.out.println("Signature failed");
		       }

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GenerateSignatureException(e.getMessage());
		}
	}

}
