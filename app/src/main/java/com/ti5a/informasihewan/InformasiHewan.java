package com.ti5a.informasihewan;

public class InformasiHewan {
    private String key;

    private String nama;
    private String keluarga;
    private String habitat;

    public InformasiHewan(){
    }

    public InformasiHewan(String nama, String keluarga, String habitat) {
        this.nama = nama;
        this.keluarga = keluarga;
        this.habitat = habitat;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeluarga() {
        return keluarga;
    }

    public void setKeluarga(String keluarga) {
        this.keluarga = keluarga;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habibtat) {
        this.habitat = habibtat;
    }


}
