package info.revenberg.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import info.revenberg.domain.AuditModel;

@Entity
@Table(name = "vers")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Vers extends AuditModel {
    private static final long serialVersionUID = -3744664716090284011L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @Column(nullable = false)
    private long versid;

    @Column(nullable = false)
    private int rank;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String name;
    
    private String location;
    private int versLines;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_song", referencedColumnName = "songid")
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonBackReference
    private Song song;

    public Vers() {
    }

    public Vers(long songid, long versid, int rank, String title, String name, Song song) {
        this.versid = versid;
        this.rank = rank;
        this.title = title;
        this.name = name;
        setSong(song);
    }

    public long getId() {
        return this.id;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return this.song;
    }

    public long getVersid() {
        return versid;
    }

    public void setVersid(long versid) {
        this.versid = versid;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public int getVersLines() {
        return versLines;
    }

    public void setVersLines(int versLines) {
        this.versLines = versLines;
    } 

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Vers))
            return false;
        return id == ((Vers) o).getId();
    }

}
