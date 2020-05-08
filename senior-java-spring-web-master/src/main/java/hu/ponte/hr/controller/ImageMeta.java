package hu.ponte.hr.controller;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;

/**
 * @author zoltan
 */
@Getter
@Entity
public class ImageMeta {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String name;
	private String mimeType;
	private long size;
	@Lob
	private String digitalSign;
	@Lob
	private byte[] image;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public ImageMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImageMeta [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", mimeType=");
		builder.append(mimeType);
		builder.append(", size=");
		builder.append(size);
		builder.append(", digitalSign=");
		builder.append(digitalSign);
		builder.append(", image=");
		builder.append(Arrays.toString(image));
		builder.append("]");
		return builder.toString();
	}

}
