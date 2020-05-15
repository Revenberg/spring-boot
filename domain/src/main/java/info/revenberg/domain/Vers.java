package info.revenberg.domain;

import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vers_sequence")
    @GenericGenerator(name = "vers_sequence", strategy = "native")
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_song", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Song song;

    @OneToMany(mappedBy = "vers", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Line> lines;

    public void setLines(Set<Line> lines) {
        this.lines = lines;
    }

    public Set<Line> getLines() {
        return this.lines;
    }

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
