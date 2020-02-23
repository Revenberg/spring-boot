package info.revenberg.loader.objects;

public class DataObject {
    private String filename;
    private String bundleName;
    private  String songName;	

    public DataObject() {
    }

    public DataObject(String filename) {
        this.filename = filename;
        this.bundleName = null;
        this.songName = null;
    }

    public DataObject(String filename, String bundleName, String songName) {
        this.filename = filename;
        this.bundleName = bundleName;
        this.songName = songName;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBundleName() {
        return this.bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public DataObject filename(String filename) {
        this.filename = filename;
        return this;
    }

    public DataObject bundleName(String bundleName) {
        this.bundleName = bundleName;
        return this;
    }

    public DataObject songName(String songName) {
        this.songName = songName;
        return this;
    }
    }
