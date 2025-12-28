public class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String kategori, String jenisMakanan) {
        super(nama, harga, kategori);
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() { return jenisMakanan; }
    public void setJenisMakanan(String jenis) { this.jenisMakanan = jenis; }

    @Override
    public void tampilMenu() {
        System.out.println("Makanan: " + getNama() + " | Harga: " + getHarga() + " | Kategori: " + getKategori() + " | Jenis: " + jenisMakanan);
    }

    @Override
    public String toDataString() {
        return "FOOD|" + getNama() + "|" + getHarga() + "|" + getKategori() + "|" + jenisMakanan;
    }
}
