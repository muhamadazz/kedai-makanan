public class Transfer extends Pembayaran {
    private static final int BIAYA_PENANGANAN = 500;

    public Transfer(int jumlahUang, int totalHarga) {
        super(jumlahUang, totalHarga);
    }

    // Implementasi metode bayar() untuk pembayaran dengan transfer
    @Override
    public void bayar() {
        if (getJumlahUang() >= (getTotalHarga() + BIAYA_PENANGANAN)) {
            int kembalian = getJumlahUang() - (getTotalHarga() + BIAYA_PENANGANAN);
            System.out.println("Total harga : Rp " + getTotalHarga());
            System.out.println("Biaya penanganan: Rp " + BIAYA_PENANGANAN);
            System.out.println("Total yang harus dibayar: Rp " + (getTotalHarga() + BIAYA_PENANGANAN));
            System.out.println("Pembayaran : Rp " + getJumlahUang());
            System.out.println("Pembayaran berhasil dengan transfer. Kembalian: Rp " + kembalian);
        } else {
            System.err.println("Pembayaran gagal. Jumlah uang tidak mencukupi.");
        }
    }
}