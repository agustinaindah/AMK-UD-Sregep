package com.amkuds.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemKaryawan implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("jk")
    @Expose
    private String jk;
    @SerializedName("nip")
    @Expose
    private String nip;
    @SerializedName("no_ktp")
    @Expose
    private String noKtp;
    @SerializedName("no_hp")
    @Expose
    private String noHp;
    @SerializedName("log_kontrak")
    @Expose
    private String logKontrak;
    @SerializedName("log_salary")
    @Expose
    private String logSalary;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("tgl_masuk")
    @Expose
    private String tglMasuk;
    @SerializedName("tgl_resign")
    @Expose
    private String tglResign;
    @SerializedName("tgl_lahir")
    @Expose
    private String tglLahir;
    @SerializedName("tempat_kelahiran")
    @Expose
    private String tempatKelahiran;
    @SerializedName("domisili")
    @Expose
    private String domisili;
    @SerializedName("jabatan")
    @Expose
    private String jabatan;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("foto_ktp")
    @Expose
    private String fotoKtp;
    @SerializedName("doc")
    @Expose
    private String doc;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_karyawan")
    @Expose
    private String statusKaryawan;
    @SerializedName("agama")
    @Expose
    private String agama;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getLogKontrak() {
        return logKontrak;
    }

    public void setLogKontrak(String logKontrak) {
        this.logKontrak = logKontrak;
    }

    public String getLogSalary() {
        return logSalary;
    }

    public void setLogSalary(String logSalary) {
        this.logSalary = logSalary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(String tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public String getTglResign() {
        return tglResign;
    }

    public void setTglResign(String tglResign) {
        this.tglResign = tglResign;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getTempatKelahiran() {
        return tempatKelahiran;
    }

    public void setTempatKelahiran(String tempatKelahiran) {
        this.tempatKelahiran = tempatKelahiran;
    }

    public String getDomisili() {
        return domisili;
    }

    public void setDomisili(String domisili) {
        this.domisili = domisili;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoKtp() {
        return fotoKtp;
    }

    public void setFotoKtp(String fotoKtp) {
        this.fotoKtp = fotoKtp;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusKaryawan() {
        return statusKaryawan;
    }

    public void setStatusKaryawan(String statusKaryawan) {
        this.statusKaryawan = statusKaryawan;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }
}
