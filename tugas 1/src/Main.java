import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ArrayList;

class Menu {
    String nama;
    double harga;
    String kategori;

    Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
}

class Pesanan {
    String nama;
    int jumlah;
    double totalHarga;
    boolean diskonBeliSatuGratisSatu; // menandakan apakah diskon diterapkan

    Pesanan(String nama, int jumlah, double totalHarga, boolean diskon) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.diskonBeliSatuGratisSatu = diskon;
    }
}

public class Main {
    static Menu[] menuMakanan = {
            new Menu("nasi", 5000, "Makanan"),
            new Menu("ayam geprek", 10000, "Makanan"),
            new Menu("ayam gepuk", 10000, "Makanan"),
            new Menu("mie ayam", 8000, "Makanan"),
    };

    static Menu[] menuMinuman = {
            new Menu("air mineral", 3000, "Minuman"),
            new Menu("es teh", 5000, "Minuman"),
            new Menu("es buah", 10000, "Minuman"),
            new Menu("jus buah", 10000, "Minuman"),
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Pesanan> daftarPesanan = new ArrayList<>();
        double totalBiaya = 0;
        double pajak, biayaPelayanan = 20000; // biaya pelayanan tetap

        System.out.println("Menu Makanan:");
        for (Menu menu : menuMakanan) {
            System.out.println(menu.nama + " - Rp" + menu.harga);
        }

        System.out.println("Menu Minuman:");
        for (Menu menu : menuMinuman) {
            System.out.println(menu.nama + " - Rp" + menu.harga);
        }

        System.out.println("\nSilakan Masukkan Pesanan (format: Nama Menu = Jumlah, maksimal 4 menu):");
        for (int i = 0; i < 4; i++) {
            System.out.print("Pesanan" + (i + 1) + ":");
            String pesanan = scanner.nextLine();
            if (pesanan.isEmpty()) {
                break; // jika input kosong, keluar dari loop
            }

            String[] parts = pesanan.split("=");
            String namaMenu = parts[0].trim();
            int qty = Integer.parseInt(parts[1].trim());

            double harga = 0;
            boolean ditemukan = false;

            // cek di menu makanan
            for (Menu menu : menuMakanan) {
                if (menu.nama.equalsIgnoreCase(namaMenu)) {
                    harga = menu.harga;
                    totalBiaya += harga * qty; // tambah total biaya
                    daftarPesanan.add(new Pesanan(menu.nama, qty, harga * qty, false)); // tambah ke daftar pesanan
                    ditemukan = true;
                    break;
                }
            }

            // cek di menu minuman
            if (!ditemukan) {
                for (Menu menu : menuMinuman) {
                    if (menu.nama.equalsIgnoreCase(namaMenu)) {
                        harga = menu.harga;
                        totalBiaya += harga * qty; // tambah total biaya
                        daftarPesanan.add(new Pesanan(menu.nama, qty, harga * qty, false)); // tambah ke daftar pesnanan
                        ditemukan = true;
                        break;
                    }
                }
            }

            if (!ditemukan) {
                System.out.println("Menu" + namaMenu + "tidak ditemukan.");
            }
        }

        // menghitung pajak
        pajak = totalBiaya * 0.1; // 10% pajak

        // cek apakah total biaya lebih dari Rp 100.000
        double diskon = 0;
        if (totalBiaya > 100000) {
            diskon = totalBiaya * 0.1; // diskon 10%
            totalBiaya -= diskon; // kurangi diskon dari total biaya
        }

        // tanyakan tentang penawaran "beli 1 gratis 1" utk minuman jika total lebih dari Rp 50.000
        boolean tawaranBeliSatuGratisSatu = false;
        if (totalBiaya > 50000) {
            System.out.print("Total biaya Anda melebihi Rp 50.000. Apakah Anda ingin membeli minuman dengan penawaran beli 1 gratis 1? (ya/tidak):");
            String jawab = scanner.nextLine().trim().toLowerCase();
            if (jawab.equals("ya")) {
                tawaranBeliSatuGratisSatu = true;
            }
        }

        // jika tawaran diterima, minta input utk minuman
        if (tawaranBeliSatuGratisSatu) {
            System.out.println("\nSilakan Pilih Minuman Dengan Penawaran Beli 1 Gratis 1 (format: Nama Minuman = Jumlah):");
            String minumanPesanan = scanner.nextLine();
            String[] minumanParts = minumanPesanan.split("=");
            String namaMinuman = minumanParts[0].trim();
            int qtyMinuman = Integer.parseInt(minumanParts[1].trim());

            double hargaMinuman = 0;
            boolean minumanDitemukan = false;

            for (Menu menu : menuMinuman) {
                if (menu.nama.equalsIgnoreCase(namaMinuman)) {
                    hargaMinuman = menu.harga;
                    double totalHargaMinuman = hargaMinuman * (qtyMinuman /2 + qtyMinuman % 2); // hanya bayar setengah dari jumlah
                    totalBiaya += totalHargaMinuman; // tambah ke total biaya
                    daftarPesanan.add(new Pesanan(menu.nama, qtyMinuman, totalHargaMinuman, true)); // tambah ke daftar pesanan
                    minumanDitemukan = true;
                    break;
                }
            }

            if (!minumanDitemukan) {
                System.out.println("Minuman" + namaMinuman + "tidak ditemukan.");
            }
        }

        // menghitung total akhir
        double totalAkhir = totalBiaya + pajak + biayaPelayanan;

        // menampilkan struk pesanan
        System.out.println("\n--------Struk Pesanan------------");
        for (Pesanan pesanan : daftarPesanan) {
            if (pesanan.diskonBeliSatuGratisSatu) {
                System.out.printf("%s x %d = %s (Diskon Beli 1 Gratis 1 diterapkan%n", pesanan.nama, pesanan.jumlah, NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(pesanan.totalHarga));
            } else {
                System.out.printf("%s + %d = %s%n", pesanan.nama, pesanan.jumlah, NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(pesanan.totalHarga));
            }
        }

        System.out.printf("\nTotal Biaya Pesanan: %s%n", NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(totalBiaya));
        System.out.printf("Biaya Pajak (10%%): %s%n", NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(pajak));
        System.out.printf("Biaya Pelayanan: %s%n", NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(biayaPelayanan));

        // jika diskon diterapkan, tampilkan info diskon
        if (diskon > 0) {
            System.out.printf("Diskon 10%%: -%s%n", NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(diskon));
        }

        System.out.printf("Total Setelah Diskon dan Biaya: %s%n", NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(totalAkhir));
        System.out.println("Terima Kasih");
        System.out.println("Makanan dan Minuman yang sudah dipesan tidak dapat dibatalkan");
        System.out.println("--------------------------------");

        scanner.close();
    }
}
