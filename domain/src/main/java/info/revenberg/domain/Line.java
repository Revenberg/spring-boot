package info.revenberg.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import info.revenberg.domain.AuditModel;

@Entity
@Table(name = "line")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Line extends AuditModel  {
    private static final long serialVersionUID = -8627990442911682692L;

    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "line_sequence")
    @GenericGenerator(name = "line_sequence", strategy = "native")
    private long id;

    @Column(nullable = false)
    private int rank;

    @Column(nullable = false)
    private String text;

    private String location;

    double minY;
    double MaxY;
    double minX;
    double MaxX;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_vers", referencedColumnName = "versid")
    @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonBackReference
    private Vers vers;

    public Line() {
    }

    public Line(int rank, String text, String location,
            double minY, double MaxY, double minX, double MaxX, Vers vers) {
        this.rank = rank;
        this.text = text;
        this.minY = minY;
        this.MaxY = MaxY;
        this.minX = minX;
        this.MaxX = MaxX;
        this.location = location;

        setVers(vers);
    }

    public long getId() {
        return this.id;
    }

    public void setVers(Vers vers) {
        this.vers = vers;
    }

    public Vers getVers() {
        return this.vers;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Line))
            return false;
        return id == ((Line) o).getId();
    }

}
