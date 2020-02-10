package info.revenberg.domain;

import java.io.File;

import javax.xml.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
/*
 * a simple domain entity doubling as a DTO
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TempFile {
    public final static String secretKey = "ssshhhhhhhhhhh!!!!";

    private String originalFilename;
    private String filename;

    private long size;

    private String contentType;

    private String name;

    public TempFile() {
    }

    public TempFile(String filename) {
        this.filename = filename;
    }
    
    public TempFile(MultipartFile file, String filename) {
        this.filename = filename;
        this.originalFilename = file.getOriginalFilename();
        this.size = file.getSize();
        this.contentType = file.getContentType();
        this.name = file.getName();
    }

    public TempFile(String originalFilename, String filename, long size, String contentType, String name) {
        this.originalFilename = originalFilename;
        this.filename = filename;
        this.size = size;
        this.contentType = contentType;
        this.name = name;
    }

    public TempFile(File file) {
        this.originalFilename = file.getName();
        this.filename = file.getName();
        this.size = file.length();
        this.contentType = "";
        this.name = file.getName();
	}

	public String getOriginalFilename() {
        return this.originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        filename = filename.replace(".png", "").replace(".jpg", "").replace(".jpeg", "");
        this.filename = filename;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TempFile originalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
        return this;
    }

    public TempFile filename(String filename) {
        this.filename = filename;
        return this;
    }

    public TempFile size(long size) {
        this.size = size;
        return this;
    }

    public TempFile contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public TempFile name(String name) {
        this.name = name;
        return this;
    }

    
    
    }
