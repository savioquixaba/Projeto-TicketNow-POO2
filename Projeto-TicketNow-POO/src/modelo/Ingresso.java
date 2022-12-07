package modelo;


public abstract class Ingresso {
    private int codigo;
    
    public Ingresso(int codigo) {
        super();
        
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public abstract double calcularValor(); 

    public String toString() {
        return "código=" + this.codigo;
    }
}
