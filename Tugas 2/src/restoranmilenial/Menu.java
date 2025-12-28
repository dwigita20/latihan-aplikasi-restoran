package restoranmilenial;

/**
 * Kelas untuk merepresentasikan satu item menu di restoran.
 */
public class Menu {
    private String nama;
    private double harga;
    private String kategori; // "Makanan" atau "Minuman"

    public Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Getter
    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    // Setter (hanya untuk harga, sesuai kebutuhan di 'Ubah Menu"
    public void setHarga(double harga) {
        this.harga = harga;
    }

    // Override equals dan hashCode agar berfungsi benar di HashMap (untuk Struk)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Double.compare(menu.harga, harga) == 0 &&
                nama.equals(menu.nama) &&
                kategori.equals(menu.kategori);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(nama, harga, kategori);
    }
}
