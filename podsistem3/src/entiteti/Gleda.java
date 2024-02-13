/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JA
 */
@Entity
@Table(name = "gleda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gleda.findAll", query = "SELECT g FROM Gleda g"),
    @NamedQuery(name = "Gleda.findByIdgleda", query = "SELECT g FROM Gleda g WHERE g.idgleda = :idgleda"),
    @NamedQuery(name = "Gleda.findByDatum", query = "SELECT g FROM Gleda g WHERE g.datum = :datum"),
    @NamedQuery(name = "Gleda.findBySekZap", query = "SELECT g FROM Gleda g WHERE g.sekZap = :sekZap"),
    @NamedQuery(name = "Gleda.findBySekOdg", query = "SELECT g FROM Gleda g WHERE g.sekOdg = :sekOdg")})
public class Gleda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgleda")
    private Integer idgleda;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    private int datum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekZap")
    private int sekZap;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekOdg")
    private int sekOdg;
    @JoinColumn(name = "idkorisnik", referencedColumnName = "idkorisnik")
    @ManyToOne(optional = false)
    private Korisnik idkorisnik;
    @JoinColumn(name = "idvideosnimak", referencedColumnName = "idvideosnimak")
    @ManyToOne(optional = false)
    private Videosnimak idvideosnimak;

    public Gleda() {
    }

    public Gleda(Integer idgleda) {
        this.idgleda = idgleda;
    }

    public Gleda(Integer idgleda, int datum, int sekZap, int sekOdg) {
        this.idgleda = idgleda;
        this.datum = datum;
        this.sekZap = sekZap;
        this.sekOdg = sekOdg;
    }

    public Integer getIdgleda() {
        return idgleda;
    }

    public void setIdgleda(Integer idgleda) {
        this.idgleda = idgleda;
    }

    public int getDatum() {
        return datum;
    }

    public void setDatum(int datum) {
        this.datum = datum;
    }

    public int getSekZap() {
        return sekZap;
    }

    public void setSekZap(int sekZap) {
        this.sekZap = sekZap;
    }

    public int getSekOdg() {
        return sekOdg;
    }

    public void setSekOdg(int sekOdg) {
        this.sekOdg = sekOdg;
    }

    public Korisnik getIdkorisnik() {
        return idkorisnik;
    }

    public void setIdkorisnik(Korisnik idkorisnik) {
        this.idkorisnik = idkorisnik;
    }

    public Videosnimak getIdvideosnimak() {
        return idvideosnimak;
    }

    public void setIdvideosnimak(Videosnimak idvideosnimak) {
        this.idvideosnimak = idvideosnimak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgleda != null ? idgleda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gleda)) {
            return false;
        }
        Gleda other = (Gleda) object;
        if ((this.idgleda == null && other.idgleda != null) || (this.idgleda != null && !this.idgleda.equals(other.idgleda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Gleda[ idgleda=" + idgleda + " ]";
    }
    
}
