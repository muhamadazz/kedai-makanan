class Pemesanan {
    private Menu[] pesanan;
    private int jumlahPesanan;

    public Pemesanan(int kapasitas) {
        pesanan = new Menu[kapasitas];
        jumlahPesanan = 0;
    }

    public void tambahPesanan(Menu menu) {
        pesanan[jumlahPesanan] = menu;
        jumlahPesanan++;
    }

    public int hitungTotalHarga() {
        int total = 0;
        for (int i = 0; i < jumlahPesanan; i++) {
            total += pesanan[i].getHarga();
        }
        return total;
    }

    public void tampilkanPesanan() {
        System.out.println("Pesanan:");
        for (int i = 0; i < jumlahPesanan; i++) {
            pesanan[i].getInfo();
        }
        System.out.println("Total: Rp " + hitungTotalHarga());
    }
}