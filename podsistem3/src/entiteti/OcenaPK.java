/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author JA
 */
@Embeddable
public class OcenaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "korisnik")
    private int korisnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "videosnimak")
    private int videosnimak;

    public OcenaPK() {
    }

    public OcenaPK(int korisnik, int videosnimak) {
        this.korisnik = korisnik;
        this.videosnimak = videosnimak;
    }

    public int getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(int korisnik) {
        this.korisnik = korisnik;
    }

    public int getVideosnimak() {
        return videosnimak;
    }

    public void setVideosnimak(int videosnimak) {
        this.videosnimak = videosnimak;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) korisnik;
        hash += (int) videosnimak;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcenaPK)) {
            return false;
        }
        OcenaPK other = (OcenaPK) object;
        if (this.korisnik != other.korisnik) {
            return false;
        }
        if (this.videosnimak != other.videosnimak) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.OcenaPK[ korisnik=" + korisnik + ", videosnimak=" + videosnimak + " ]";
    }
    
}
