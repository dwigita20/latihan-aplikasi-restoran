import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public MenuItem getItem(int index) {
        return items.get(index);
    }

    public int size() { return items.size(); }

    public void tampilkanMenu() {
        System.out.println("\n--- Daftar Menu ---");
        if (items.isEmpty()) {
            System.out.println("(Menu kosong)");
            return;
        }
        int i = 1;
        for (MenuItem it : items) {
            System.out.print(i + ". ");
            it.tampilMenu();
            i++;
        }
    }

    public void saveToFile(String path) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (MenuItem it : items) {
                bw.write(it.toDataString());
                bw.newLine();
            }
        }
    }

    public void loadFromFile(String path) throws Exception {
        items.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 5) continue;
                String type = parts[0];
                String nama = parts[1];
                double harga = Double.parseDouble(parts[2]);
                String kategori = parts[3];
                String extra = parts[4];
                switch (type) {
                    case "FOOD":
                        items.add(new Makanan(nama, harga, kategori, extra));
                        break;
                    case "DRINK":
                        items.add(new Minuman(nama, harga, kategori, extra));
                        break;
                    case "DISC":
                        double p = Double.parseDouble(extra);
                        items.add(new Diskon(nama, harga, kategori, p));
                        break;
                }
            }
        }
    }
}
