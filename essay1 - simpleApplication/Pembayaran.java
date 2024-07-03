public class Pembayaran {
    protected int jumlahUang;
    protected int totalHarga;
    protected int kembalian;

    public Pembayaran(int jumlahUang, int totalHarga) {
        this.jumlahUang = jumlahUang;
        this.totalHarga = totalHarga;
    }

    public void bayar() {
        if (jumlahUang >= totalHarga) {
            kembalian = jumlahUang - totalHarga;
            System.out.println("Total harga : Rp " + totalHarga);
            System.out.println("Pembayaran : Rp " + jumlahUang);
            System.out.println("Pembayaran berhasil. Kembalian: Rp " + kembalian);
        } else {
            System.err.println("Pembayaran gagal. Jumlah uang tidak mencukupi.");
        }
    }

    // Getter dan setter untuk variabel instance
    public int getJumlahUang() {
        return jumlahUang;
    }

    public void setJumlahUang(int jumlahUang) {
        this.jumlahUang = jumlahUang;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getKembalian() {
        return kembalian;
    }
}
