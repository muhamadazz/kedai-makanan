import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Membuat menu-menu yang tersedia
        Menu[] listMenu = {
            new Menu("kerupuk", 3000),
            new Menu("mie", 5000),
            new Menu("sayur", 2000),
            new Menu("baso", 3000)
        };
        // Menampilkan menu-menu yang tersedia
        System.out.println("Menu yang tersedia:");
        for (int i = 0; i < listMenu.length; i++){
            listMenu[i].getInfo();
        }

        // Menginput jumlah menu yang dipesan pengguna
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan jumlah menu yang ingin dipesan: ");
        int jumlahMenu = scanner.nextInt();
        Pemesanan pemesanan = new Pemesanan(jumlahMenu);

        // Input pesanan pengguna
        for (int i = 0; i < jumlahMenu; i++) {
            scanner.nextLine();
            System.out.print("Masukkan nama menu ke-" + (i + 1) + ": ");
            String namaMenu = scanner.nextLine();
            int hargaMenu = 0;
            if (namaMenu.equalsIgnoreCase(listMenu[0].getNama())) {
                hargaMenu = listMenu[0].getHarga();
            } else if (namaMenu.equalsIgnoreCase(listMenu[1].getNama())) {
                hargaMenu = listMenu[1].getHarga();
            } else if (namaMenu.equalsIgnoreCase(listMenu[2].getNama())) {
                hargaMenu = listMenu[2].getHarga();
            } else if (namaMenu.equalsIgnoreCase(listMenu[3].getNama())) {
                hargaMenu = listMenu[3].getHarga();
            } else {
                System.err.println("pilihan tidak valid");
            }

            Menu menu = new Menu(namaMenu, hargaMenu);
            pemesanan.tambahPesanan(menu);
        }

        // Menampilkan pesanan dan total harga
        pemesanan.tampilkanPesanan();

        // menampilkan opsi pembayaran
        System.out.println("Pilih metode pembayaran:");
        System.out.println("1. Cash");
        System.out.println("2. Transfer (dikenakan biaya admin Rp. 500)");
        System.out.print("Masukkan pilihan: ");
        int metodePembayaran = scanner.nextInt();

        // Lakukan pembayaran
        if (metodePembayaran == 1) {
            System.out.print("Masukkan jumlah uang: ");
            int jumlahUang = scanner.nextInt();
            int totalHarga = pemesanan.hitungTotalHarga();
            Cash cash = new Cash(jumlahUang, totalHarga);
            cash.bayar();
        } else if (metodePembayaran == 2) {
            System.out.print("Masukkan jumlah uang: ");
            int jumlahUang = scanner.nextInt();
            int totalHarga = pemesanan.hitungTotalHarga();
            Transfer transfer = new Transfer(jumlahUang, totalHarga);
            transfer.bayar();
        } else {
            System.out.println("Pilihan tidak valid.");
        }

        // Tutup scanner
        scanner.close();
    }
}
