package com.ti5a.informasihewan;

public class InformasiHewan {
    private String key;

    private String kodeMK;
    private String namaMK;
    private String kelas;
    private String dosenPengampuh;

    public InformasiHewan(){
    }

    public InformasiHewan(String kodeMK, String namaMK, String kelas, String dosenPengampuh) {
        this.kodeMK = kodeMK;
        this.namaMK = namaMK;
        this.kelas = kelas;
        this.dosenPengampuh = dosenPengampuh;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKodeMK() {
        return kodeMK;
    }

    public void setKodeMK(String kodeMK) {
        this.kodeMK = kodeMK;
    }

    public String getNamaMK() {
        return namaMK;
    }

    public void setNamaMK(String namaMK) {
        this.namaMK = namaMK;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getDosenPengampuh() {
        return dosenPengampuh;
    }

    public void setDosenPengampuh(String dosenPengampuh) {
        this.dosenPengampuh = dosenPengampuh;
    }
}
