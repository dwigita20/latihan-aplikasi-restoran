import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        Pesanan pesanan = null;

        // mencoba memuat menu yang ada jika tersedia
        try {
            menu.loadFromFile("menu.txt");
            System.out.println("Menu dimuat dari menu.txt (jika ada).");
        } catch (Exception e) {
            System.out.println("Tidak ditemukan menu yang disimpan atau gagal dimuat: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== Restoran Ceu Idah ===");
            System.out.println("1. Tambah item menu (Makanan)");
            System.out.println("2. Tambah item menu (Minuman)");
            System.out.println("3. Tambah item menu (Diskon)");
            System.out.println("4. Tampilkan menu restoran");
            System.out.println("5. Mulai pesanan baru");
            System.out.println("6. Tambah item ke pesanan");
            System.out.println("7. Tampilkan struk pesanan");
            System.out.println("8. Simpan menu ke file");
            System.out.println("9. Muat menu dari file");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");

            String pilihan = sc.nextLine();
            try {
                switch (pilihan) {
                    case "1":
                        System.out.print("Nama makanan: ");
                        String namaMakanan = sc.nextLine();
                        System.out.print("Harga: ");
                        double hargaMakanan = Double.parseDouble(sc.nextLine());
                        System.out.print("Kategori: ");
                        String kategoriMakanan = sc.nextLine();
                        System.out.print("Jenis makanan: ");
                        String jenisMakanan = sc.nextLine();
                        menu.addItem(new Makanan(namaMakanan, hargaMakanan, kategoriMakanan, jenisMakanan));
                        System.out.println("Makanan ditambahkan.");
                        break;
                    case "2":
                        System.out.print("Nama minuman: ");
                        String namaMinuman = sc.nextLine();
                        System.out.print("Harga: ");
                        double hargaMinuman = Double.parseDouble(sc.nextLine());
                        System.out.print("Kategori: ");
                        String kategoriMinuman = sc.nextLine();
                        System.out.print("Jenis minuman: ");
                        String jenisMinuman = sc.nextLine();
                        menu.addItem(new Minuman(namaMinuman, hargaMinuman, kategoriMinuman, jenisMinuman));
                        System.out.println("Minuman ditambahkan.");
                        break;
                    case "3":
                        System.out.print("Nama diskon (deskripsi): ");
                        String namaDiskon = sc.nextLine();
                        System.out.print("Harga dasar (mis. 0): ");
                        double hargaDiskon = Double.parseDouble(sc.nextLine());
                        System.out.print("Kategori: ");
                        String kategoriDiskon = sc.nextLine();
                        System.out.print("Persentase diskon (0-100): ");
                        double pers = Double.parseDouble(sc.nextLine());
                        menu.addItem(new Diskon(namaDiskon, hargaDiskon, kategoriDiskon, pers));
                        System.out.println("Diskon ditambahkan.");
                        break;
                    case "4":
                        menu.tampilkanMenu();
                        break;
                    case "5":
                        pesanan = new Pesanan();
                        System.out.println("Pesanan baru dibuat.");
                        break;
                    case "6":
                        if (pesanan == null) {
                            System.out.println("Buat pesanan baru terlebih dahulu (pilih 5).");
                            break;
                        }
                        menu.tampilkanMenu();
                        System.out.print("Masukkan nomor item untuk dipesan: ");
                        int idx = Integer.parseInt(sc.nextLine());
                        try {
                            MenuItem item = menu.getItem(idx - 1);
                            pesanan.addItem(item);
                            System.out.println("Item ditambahkan ke pesanan.");
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("Item tidak ditemukan: " + ex.getMessage());
                        }
                        break;
                    case "7":
                        if (pesanan == null) {
                            System.out.println("Tidak ada pesanan. Buat pesanan baru dulu.");
                        } else {
                            pesanan.printReceipt();
                            try {
                                pesanan.saveReceiptToFile("receipts.txt");
                                System.out.println("Struk disimpan ke receipts.txt.");
                            } catch (Exception e) {
                                System.out.println("Gagal menyimpan struk: " + e.getMessage());
                            }
                        }
                        break;
                    case "8":
                        try {
                            menu.saveToFile("menu.txt");
                            System.out.println("Menu disimpan ke menu.txt.");
                        } catch (Exception e) {
                            System.out.println("Gagal menyimpan menu: " + e.getMessage());
                        }
                        break;
                    case "9":
                        try {
                            menu.loadFromFile("menu.txt");
                            System.out.println("Menu dimuat dari menu.txt.");
                        } catch (Exception e) {
                            System.out.println("Gagal memuat menu: " + e.getMessage());
                        }
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (NumberFormatException nf) {
                System.out.println("Input angka tidak valid: " + nf.getMessage());
            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            }
        }

        sc.close();
        System.out.println("Program selesai. Sampai jumpa!");
    }
}