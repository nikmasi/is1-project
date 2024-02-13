/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JA
 */
@Entity
@Table(name = "videosnimak")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Videosnimak.findAll", query = "SELECT v FROM Videosnimak v"),
    @NamedQuery(name = "Videosnimak.findByIdvideosnimak", query = "SELECT v FROM Videosnimak v WHERE v.idvideosnimak = :idvideosnimak"),
    @NamedQuery(name = "Videosnimak.findByNaziv", query = "SELECT v FROM Videosnimak v WHERE v.naziv = :naziv"),
    @NamedQuery(name = "Videosnimak.findByTrajanje", query = "SELECT v FROM Videosnimak v WHERE v.trajanje = :trajanje"),
    @NamedQuery(name = "Videosnimak.findByDatum", query = "SELECT v FROM Videosnimak v WHERE v.datum = :datum")})
public class Videosnimak implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "datum")
    private String datum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idvideosnimak")
    private List<Gleda> gledaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videosnimak1")
    private List<Ocena> ocenaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvideosnimak")
    private Integer idvideosnimak;
    @JoinColumn(name = "vlasnik", referencedColumnName = "idkorisnik")
    @ManyToOne(optional = false)
    private Korisnik vlasnik;

    public Videosnimak() {
    }

    public Videosnimak(Integer idvideosnimak) {
        this.idvideosnimak = idvideosnimak;
    }

    public Videosnimak(Integer idvideosnimak, String naziv, int trajanje, String datum) {
        this.idvideosnimak = idvideosnimak;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datum = datum;
    }

    public Integer getIdvideosnimak() {
        return idvideosnimak;
    }

    public void setIdvideosnimak(Integer idvideosnimak) {
        this.idvideosnimak = idvideosnimak;
    }


    public Korisnik getVlasnik() {
        return vlasnik;
    }

    public void setVlasnik(Korisnik vlasnik) {
        this.vlasnik = vlasnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvideosnimak != null ? idvideosnimak.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Videosnimak)) {
            return false;
        }
        Videosnimak other = (Videosnimak) object;
        if ((this.idvideosnimak == null && other.idvideosnimak != null) || (this.idvideosnimak != null && !this.idvideosnimak.equals(other.idvideosnimak))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Videosnimak[ idvideosnimak=" + idvideosnimak + " ]";
    }


    @XmlTransient
    public List<Ocena> getOcenaList() {
        return ocenaList;
    }

    public void setOcenaList(List<Ocena> ocenaList) {
        this.ocenaList = ocenaList;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    @XmlTransient
    public List<Gleda> getGledaList() {
        return gledaList;
    }

    public void setGledaList(List<Gleda> gledaList) {
        this.gledaList = gledaList;
    }
    
}
