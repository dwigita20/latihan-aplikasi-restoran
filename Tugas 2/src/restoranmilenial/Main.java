package restoranmilenial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Kelas utama untuk aplikasi restoran sederhana.
 * Mengelola daftar menu, pesanan, dan interaksi pengguna.
 */
public class Main {

    // ArrayList untuk menyimpan daftar menu.
    // Menggunakan ArrayList agar mudah menambah dan menghapus menu.
    private static ArrayList<Menu> daftarMenu = new ArrayList<>();

    // ArrayList untuk menyimpan item yang dipesan oleh pelanggan.
    private static ArrayList<Menu> pesanan = new ArrayList<>();

    // Scanner untuk input pengguna.
    private static Scanner scanner = new Scanner(System.in);

    // Konstanta untuk biaya
    private static final double PAJAK_RATE = 0.10; // 10%
    private static final double BIAYA_LAYANAN = 20000.0;

    /**
     * Method main, titik awal program.
     */
    public static void main(String[] args) {
        // Inisialisasi data menu awal
        initializeMenu();

        // Loop menu utama aplikasi
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n--- Selamat Datang di Restoran Milenial ---");
            System.out.println("1. Pesan Makanan & Minuman (Pelanggan)");
            System.out.println("2. Kelola Menu (Pemilik Restoran)");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilih menu: ");

            int pilihan = getInputAngka();

            switch (pilihan) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    menuAdmin();
                    break;
                case 0:
                    berjalan = false;
                    System.out.println("Terima kasih telah menggunakan aplikasi.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
        scanner.close();
    }

    /**
     * Inisialisasi daftar menu awal (minimal 4 per kategori).
     */
    private static void initializeMenu() {
        // Makanan
        daftarMenu.add(new Menu("Cireng", 15000, "Makanan"));
        daftarMenu.add(new Menu("Cimol Bojot", 15000, "Makanan"));
        daftarMenu.add(new Menu("Seblak", 10000, "Makanan"));
        daftarMenu.add(new Menu("Cirambay", 20000, "Makanan"));

        // Minuman
        daftarMenu.add(new Menu("Es Kepal Milo", 10000, "Minuman"));
        daftarMenu.add(new Menu("Jus Alpukat", 15000, "Minuman"));
        daftarMenu.add(new Menu("Boba Milk Tea", 20000, "Minuman"));
        daftarMenu.add(new Menu("Air Mineral", 5000, "Minuman"));
    }

    // =================================================================
    // ||                  LOGIKA UNTUK PELANGGAN                     ||
    // =================================================================

    /**
     * Menangani alur pemesanan untuk pelanggan.
     */
    private static void menuPelanggan() {
        System.out.println("\n--- Menu Pemesanan Pelanggan ---");
        // 1. Tampilkan menu
        tampilkanMenu();
        // 2. Terima pesanan
        terimaPesanan();
        // 3. Hitung dan cetak struk
        if (!pesanan.isEmpty()) {
            hitungDanCetakStruk();
        } else {
            System.out.println("Anda tidak memesan apa pun.");
        }
        // Bersihkan pesanan untuk pelanggan berikutnya
        pesanan.clear();
    }

    /**
     * Menampilkan daftar menu yang dikelompokkan berdasarkan kategori.
     * Nomor menu sesuai dengan indeks di ArrayList (ditambah 1) untuk memudahkan
     * pemilihan, penambahan, dan penghapusan.
     */
    private static void tampilkanMenu() {
        System.out.println("\n--- Daftar Menu ---");

        System.out.println("--- Makanan ---");
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu m = daftarMenu.get(i);
            if (m.getKategori().equals("Makanan")) {
                // Format: Nomor. Nama (rata kiri) - Harga (rata kanan)
                System.out.printf("%d. %-25s - Rp. %.0f\n", (i + 1), m.getNama(), m.getHarga());
            }
        }

        System.out.println("\n--- Minuman ---");
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu m = daftarMenu.get(i);
            if (m.getKategori().equals("Minuman")) {
                System.out.printf("%d. %-25s - Rp. %.0f\n", (i + 1), m.getNama(), m.getHarga());
            }
        }
        System.out.println("--------------------");
    }

    /**
     * Menerima input pesanan dari pelanggan.
     * Pelanggan memasukkan nomor menu.
     * Berhenti ketika pelanggan mengetik 'selesai'.
     */
    private static void terimaPesanan() {
        System.out.println("\nSilakan masukkan nomor menu yang ingin dipesan.");
        System.out.println("(Ketik 'selesai' untuk mengakhiri pemesanan)");

        String input;
        while (true) {
            System.out.print("Masukkan pesanan: ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                break;
            }

            try {
                int nomorMenu = Integer.parseInt(input);
                // Cek apakah nomor valid (antara 1 s/d ukuran daftarMenu)
                if (nomorMenu > 0 && nomorMenu <= daftarMenu.size()) {
                    Menu menuDipesan = daftarMenu.get(nomorMenu - 1); // Indeks array dimulai dari 0
                    pesanan.add(menuDipesan);
                    System.out.println(">> " + menuDipesan.getNama() + " telah ditambahkan ke pesanan.");
                } else {
                    // Input angka, tapi di luar jangkauan
                    System.out.println("Nomor menu tidak valid. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                // Input bukan angka dan bukan 'selesai'
                System.out.println("Input tidak dikenal. Masukkan nomor menu atau 'selesai'.");
            }
        }
        System.out.println("--- Pemesanan Selesai ---");
    }

    /**
     * Menghitung total biaya dan mencetak struk.
     * Termasuk perhitungan diskon, BOGO, pajak, dan biaya layanan.
     */
    private static void hitungDanCetakStruk() {
        // Gunakan HashMap untuk mengelompokkan pesanan yang sama
        // Key: Menu, Value: Jumlah
        Map<Menu, Integer> ringkasanPesanan = new HashMap<>();
        double totalBiaya = 0;

        for (Menu item : pesanan) {
            ringkasanPesanan.put(item, ringkasanPesanan.getOrDefault(item, 0) + 1);
        }

        System.out.println("\n==============================================");
        System.out.println("             STRUK PEMBAYARAN");
        System.out.println("==============================================");

        // Cetak item yang dipesan
        for (Map.Entry<Menu, Integer> entry : ringkasanPesanan.entrySet()) {
            Menu item = entry.getKey();
            int jumlah = entry.getValue();
            double subtotalItem = item.getHarga() * jumlah;
            totalBiaya += subtotalItem;

            System.out.printf("%-20s %dx @%.0f \t Rp. %.0f\n",
                    item.getNama(), jumlah, item.getHarga(), subtotalItem);
        }

        System.out.println("----------------------------------------------");
        System.out.printf("Subtotal Biaya \t\t\t\t Rp. %.0f\n", totalBiaya);

        // Inisialisasi variabel untuk diskon
        double diskonPersen = 0;
        double diskonBOGO = 0;
        boolean dapatBOGO = false;

        // Cek Penawaran Beli 1 Gratis 1 Minuman (jika total > 50.000)
        // Diimplementasikan sebagai: gratis 1 minuman termurah yang dipesan
        if (totalBiaya > 50000) {
            Menu minumanTermurah = null;
            for (Menu item : pesanan) {
                if (item.getKategori().equals("Minuman")) {
                    if (minumanTermurah == null || item.getHarga() < minumanTermurah.getHarga()) {
                        minumanTermurah = item;
                    }
                }
            }
            if (minumanTermurah != null) {
                diskonBOGO = minumanTermurah.getHarga();
                dapatBOGO = true;
            }
        }

        // Hitung total setelah diskon BOGO (jika ada)
        double totalSetelahBOGO = totalBiaya - diskonBOGO;

        // Cek Diskon 10% (jika total *keseluruhan* pesanan > 100.000)
        // Diskon dihitung dari total *sebelum* BOGO
        if (totalBiaya > 100000) {
            diskonPersen = totalBiaya * 0.10;
        }

        // Tampilkan info promo
        if (dapatBOGO) {
            System.out.printf("Promo BOGO (Gratis 1 Minuman) \t\t -Rp. %.0f\n", diskonBOGO);
        }
        if (diskonPersen > 0) {
            System.out.printf("Diskon 10%% (> 100rb) \t\t\t -Rp. %.0f\n", diskonPersen);
        }

        // Hitung total setelah semua diskon
        double totalSetelahDiskon = totalBiaya - diskonBOGO - diskonPersen;

        // Hitung Pajak dan Biaya Layanan
        // Pajak 10% dihitung dari total SETELAH diskon
        double pajak = totalSetelahDiskon * PAJAK_RATE;
        double totalAkhir = totalSetelahDiskon + pajak + BIAYA_LAYANAN;

        System.out.println("----------------------------------------------");
        System.out.printf("Total (setelah diskon) \t\t\t Rp. %.0f\n", totalSetelahDiskon);
        System.out.printf("Pajak 10%% \t\t\t\t Rp. %.0f\n", pajak);
        System.out.printf("Biaya Pelayanan \t\t\t Rp. %.0f\n", BIAYA_LAYANAN);
        System.out.println("==============================================");
        System.out.printf("Total Pembayaran \t\t\t Rp. %.0f\n", totalAkhir);
        System.out.println("==============================================");
    }

    // =================================================================
    // ||                   LOGIKA UNTUK ADMIN                        ||
    // =================================================================

    /**
     * Menangani menu untuk admin (pemilik restoran).
     * Memungkinkan navigasi kembali ke menu utama.
     */
    private static void menuAdmin() {
        boolean kembali = false;
        while (!kembali) {
            System.out.println("\n--- Menu Pengelolaan Restoran ---");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Tampilkan Daftar Menu");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");

            int pilihan = getInputAngka();

            switch (pilihan) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahMenu();
                    break;
                case 3:
                    hapusMenu();
                    break;
                case 4:
                    tampilkanMenu();
                    break;
                case 0:
                    kembali = true; // Keluar dari loop admin, kembali ke loop utama
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    /**
     * Logika untuk menambahkan menu baru.
     * Bisa menambahkan beberapa sekaligus.
     */
    private static void tambahMenu() {
        System.out.println("\n--- Tambah Menu Baru ---");
        boolean tambahLagi = true;

        while (tambahLagi) {
            try {
                System.out.print("Masukkan Nama Menu: ");
                String nama = scanner.nextLine();
                if (nama.isEmpty()) {
                    System.out.println("Nama tidak boleh kosong.");
                    continue;
                }

                System.out.print("Masukkan Harga Menu: Rp. ");
                double harga = scanner.nextDouble();
                scanner.nextLine(); // Konsumsi newline

                System.out.print("Masukkan Kategori (1: Makanan, 2: Minuman): ");
                int katPilihan = scanner.nextInt();
                scanner.nextLine(); // Konsumsi newline

                String kategori;
                if (katPilihan == 1) {
                    kategori = "Makanan";
                } else if (katPilihan == 2) {
                    kategori = "Minuman";
                } else {
                    System.out.println("Pilihan kategori tidak valid.");
                    continue;
                }

                // Tambahkan menu baru ke ArrayList
                daftarMenu.add(new Menu(nama, harga, kategori));
                System.out.println(">> Menu '" + nama + "' berhasil ditambahkan.");

            } catch (InputMismatchException e) {
                System.out.println("Input harga atau kategori tidak valid. Silakan coba lagi.");
                scanner.nextLine(); // Bersihkan buffer scanner
                continue; // Ulangi loop
            }

            // Konfirmasi tambah lagi
            System.out.print("Tambah menu lagi? (ya/tidak): ");
            String konfirmasi = scanner.nextLine();
            if (!konfirmasi.equalsIgnoreCase("ya")) {
                tambahLagi = false;
            }
        }
        System.out.println("Kembali ke menu pengelolaan...");
    }

    /**
     * Logika untuk mengubah harga menu yang ada.
     */
    private static void ubahMenu() {
        System.out.println("\n--- Ubah Harga Menu ---");
        tampilkanMenu();
        System.out.print("Masukkan nomor menu yang akan diubah harganya (0 untuk batal): ");

        int nomorMenu = getInputAngka();
        if (nomorMenu == 0) {
            System.out.println("Perubahan dibatalkan.");
            return;
        }

        // Validasi nomor menu
        if (nomorMenu > 0 && nomorMenu <= daftarMenu.size()) {
            try {
                Menu menu = daftarMenu.get(nomorMenu - 1);
                System.out.println("Anda akan mengubah: " + menu.getNama() + " (Harga lama: Rp. " + menu.getHarga() + ")");
                System.out.print("Masukkan harga baru: Rp. ");
                double hargaBaru = scanner.nextDouble();
                scanner.nextLine(); // Konsumsi newline

                System.out.print("Anda yakin ingin mengubah harga? (Ya/Tidak): ");
                String konfirmasi = scanner.nextLine();

                if (konfirmasi.equalsIgnoreCase("Ya")) {
                    menu.setHarga(hargaBaru);
                    System.out.println(">> Harga menu berhasil diubah.");
                } else {
                    System.out.println("Perubahan dibatalkan.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input harga tidak valid.");
                scanner.nextLine(); // Bersihkan buffer
            }
        } else {
            System.out.println("Nomor menu tidak valid.");
        }
        System.out.println("Kembali ke menu pengelolaan...");
    }

    /**
     * Logika untuk menghapus menu dari daftar.
     */
    private static void hapusMenu() {
        System.out.println("\n--- Hapus Menu ---");
        tampilkanMenu();
        System.out.print("Masukkan nomor menu yang akan dihapus (0 untuk batal): ");

        int nomorMenu = getInputAngka();
        if (nomorMenu == 0) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        // Validasi nomor menu
        if (nomorMenu > 0 && nomorMenu <= daftarMenu.size()) {
            Menu menu = daftarMenu.get(nomorMenu - 1);

            System.out.print("Anda yakin ingin menghapus '" + menu.getNama() + "'? (Ya/Tidak): ");
            String konfirmasi = scanner.nextLine();

            if (konfirmasi.equalsIgnoreCase("Ya")) {
                daftarMenu.remove(nomorMenu - 1); // Hapus dari ArrayList
                System.out.println(">> Menu berhasil dihapus.");
            } else {
                System.out.println("Penghapusan dibatalkan.");
            }
        } else {
            System.out.println("Nomor menu tidak valid.");
        }
        System.out.println("Kembali ke menu pengelolaan...");
    }

    /**
     * Helper method untuk memastikan input adalah angka (integer).
     * Mencegah crash jika pengguna input teks saat diminta angka.
     */
    private static int getInputAngka() {
        int input = -1;
        while (true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // Konsumsi newline setelah nextInt()
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                scanner.nextLine(); // Bersihkan buffer scanner
                System.out.print("Pilih menu: ");
            }
        }
    }
}
