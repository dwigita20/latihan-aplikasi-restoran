public class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String kategori, String jenisMinuman) {
        super(nama, harga, kategori);
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() { return jenisMinuman; }
    public void setJenisMinuman(String jenis) { this.jenisMinuman = jenis; }

    @Override
    public void tampilMenu() {
        System.out.println("Minuman: " + getNama() + " | Harga: " + getHarga() + " | Kategori: " + getKategori() + " | Jenis: " + jenisMinuman);
    }

    @Override
    public String toDataString() {
        return "DRINK|" + getNama() + "|" + getHarga() + "|" + getKategori() + "|" + jenisMinuman;
    }
}

