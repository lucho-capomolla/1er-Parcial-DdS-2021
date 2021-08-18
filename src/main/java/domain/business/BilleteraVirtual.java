package domain.business;

public class BilleteraVirtual {
    private double saldo;

    // Getters and Setters
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    // Constructor
    public BilleteraVirtual() {}

    public BilleteraVirtual(double saldo) { this.saldo = saldo; }


    // Metodos
    public boolean debitar(double monto) {
        if(this.saldo >= monto) {
            this.saldo -= monto;
            return true;
        }
        else {
            System.out.println("No tiene saldo suficiente.");
            return false;
        }
    }

    public void recargar(double monto) {
        this.saldo += monto;
    }

}
