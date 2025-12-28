import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Pesanan {
    private ArrayList<MenuItem> items;

    public Pesanan() {
        items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public double getSubtotal() {
        double sum = 0.0;
        for (MenuItem it : items) {
            if (!(it instanceof Diskon)) {
                sum += it.getHarga();
            }
        }
        return sum;
    }

    public double getTotal() {
        double total = getSubtotal();
        // Apply discounts sequentially
        for (MenuItem it : items) {
            if (it instanceof Diskon) {
                double p = ((Diskon) it).getPersentase();
                double discountAmount = total * (p / 100.0);
                total -= discountAmount;
            }
        }
        return total;
    }

    public void printReceipt() {
        System.out.println("\n===== STRUK PESANAN =====");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Waktu: " + sdf.format(new Date()));
        for (MenuItem it : items) {
            if (it instanceof Diskon) {
                Diskon d = (Diskon) it;
                System.out.println("Diskon: " + d.getNama() + " -> " + d.getPersentase() + "%");
            } else {
                System.out.println(it.getNama() + " - " + it.getHarga());
            }
        }
        System.out.println("-------------------------");
        System.out.println("Subtotal: " + getSubtotal());
        System.out.println("Total setelah diskon: " + getTotal());
        System.out.println("=========================");
    }

    public void saveReceiptToFile(String path) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            bw.write("==== STRUK PESANAN - " + sdf.format(new Date()) + " ===="); bw.newLine();
            for (MenuItem it : items) {
                if (it instanceof Diskon) {
                    Diskon d = (Diskon) it;
                    bw.write("Diskon: " + d.getNama() + " -> " + d.getPersentase() + "%"); bw.newLine();
                } else {
                    bw.write(it.getNama() + " - " + it.getHarga()); bw.newLine();
                }
            }
            bw.write("Subtotal: " + getSubtotal()); bw.newLine();
            bw.write("Total: " + getTotal()); bw.newLine();
            bw.write("\n");
        }
    }
}