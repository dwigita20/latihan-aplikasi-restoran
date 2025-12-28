public class Diskon extends MenuItem {
    private double persentase;

    public Diskon(String nama, double harga, String kategori, double persentase) {
        super(nama, harga, kategori);
        this.persentase = persentase;
    }

    public double getPersentase() { return persentase; }
    public void setPersentase(double p) { this.persentase = p; }

    @Override
    public void tampilMenu() {
        System.out.println("Diskon: " + getNama() + " | Persentase: " + persentase + "% | Keterangan: " + getKategori());
    }

    @Override
    public String toDataString() {
        return "DISC|" + getNama() + "|" + getHarga() + "|" + getKategori() + "|" + persentase;
    }
}
