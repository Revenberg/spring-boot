package info.revenberg.domain;

import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Proxy;

import info.revenberg.domain.AuditModel;

@Entity
@Table(name = "song", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
@Proxy(lazy = false)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Song extends AuditModel {
    private static final long serialVersionUID = -522275488406568162L;

    public final static String secretKey = "ssshhhhhhhhhhh!!!!";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_sequence")
    @GenericGenerator(name = "song_sequence", strategy = "native")
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String source;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_bundle", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bundle bundle;

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Vers> verses;

    public void setVerses(Set<Vers> verses) {
        this.verses = verses;
    }

    public Set<Vers> getVerses() {
        return this.verses;
    }

    public Song() {
    }

    public Song(long id, String name, String source, Bundle bundle) {
        this.id = id;
        this.name = name;
        this.source = source;
        setBundle(bundle);
    }

    public long getId() {
        return this.id;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsource() {
        return source;
    }

    public void setsource(String source) {
        this.source = source;
    }

    public void addVers(Vers vers) {
        verses.add(vers);
        vers.setSong(this);
    }

    public void removeVers(Vers vers) {
        verses.remove(vers);
        vers.setSong(null);
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Song))
            return false;
        return id == ((Song) o).getId();
    }
}
