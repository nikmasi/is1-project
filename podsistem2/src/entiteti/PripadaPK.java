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
public class PripadaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "videoSnimakP")
    private int videoSnimakP;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kategorijaP")
    private int kategorijaP;

    public PripadaPK() {
    }

    public PripadaPK(int videoSnimakP, int kategorijaP) {
        this.videoSnimakP = videoSnimakP;
        this.kategorijaP = kategorijaP;
    }

    public int getVideoSnimakP() {
        return videoSnimakP;
    }

    public void setVideoSnimakP(int videoSnimakP) {
        this.videoSnimakP = videoSnimakP;
    }

    public int getKategorijaP() {
        return kategorijaP;
    }

    public void setKategorijaP(int kategorijaP) {
        this.kategorijaP = kategorijaP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) videoSnimakP;
        hash += (int) kategorijaP;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PripadaPK)) {
            return false;
        }
        PripadaPK other = (PripadaPK) object;
        if (this.videoSnimakP != other.videoSnimakP) {
            return false;
        }
        if (this.kategorijaP != other.kategorijaP) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.PripadaPK[ videoSnimakP=" + videoSnimakP + ", kategorijaP=" + kategorijaP + " ]";
    }
    
}
