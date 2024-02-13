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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JA
 */
@Entity
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIdpretplata", query = "SELECT p FROM Pretplata p WHERE p.idpretplata = :idpretplata"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena"),
    @NamedQuery(name = "Pretplata.findByDatum", query = "SELECT p FROM Pretplata p WHERE p.datum = :datum"),
    @NamedQuery(name = "Pretplata.findByIdKorisnika", query = "SELECT p FROM Pretplata p WHERE p.ikorisnik.idkorisnik = :ikorisnik")})
public class Pretplata implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cena")
    private String cena;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "datum")
    private String datum;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpretplata")
    private Integer idpretplata;
    @JoinColumn(name = "ikorisnik", referencedColumnName = "idkorisnik")
    @ManyToOne(optional = false)
    private Korisnik ikorisnik;
    @JoinColumn(name = "paket", referencedColumnName = "idpaket")
    @ManyToOne(optional = false)
    private Paket paket;

    public Pretplata() {
    }

    public Pretplata(Integer idpretplata) {
        this.idpretplata = idpretplata;
    }

    public Pretplata(Integer idpretplata, String cena, String datum) {
        this.idpretplata = idpretplata;
        this.cena = cena;
        this.datum = datum;
    }

    public Integer getIdpretplata() {
        return idpretplata;
    }

    public void setIdpretplata(Integer idpretplata) {
        this.idpretplata = idpretplata;
    }


    public Korisnik getIkorisnik() {
        return ikorisnik;
    }

    public void setIkorisnik(Korisnik ikorisnik) {
        this.ikorisnik = ikorisnik;
    }

    public Paket getPaket() {
        return paket;
    }

    public void setPaket(Paket paket) {
        this.paket = paket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpretplata != null ? idpretplata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.idpretplata == null && other.idpretplata != null) || (this.idpretplata != null && !this.idpretplata.equals(other.idpretplata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Pretplata[ idpretplata=" + idpretplata + " ]";
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
    
}
