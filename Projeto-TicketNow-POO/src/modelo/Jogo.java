package modelo;


import java.util.ArrayList;


public class Jogo {
    private int id;
    private String data; 
    private String local;
    private int estoque;
    private double preco;
    private Time time1;   
    private Time time2;
    private ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();

    public Jogo(int id, String data, String local, int estoque, double preco, Time time1, Time time2) {
        this.id = id;
        this.data = data;
        this.local = local;
        this.estoque = estoque;
        this.preco = preco;
        this.time1 = time1;
        this.time2 = time2;
    }

    public int getId() {
        return this.id;
    }

    public String getData() {
        return this.data;
    }

    public String getLocal() {
        return this.local;
    }

    public int getEstoque() {
        return this.estoque;
    }

    public double getPreco() {
        return this.preco;
    }

    public Time getTime1() {
        return this.time1;
    }

    public Time getTime2() {
        return this.time2;
    }

    public ArrayList<Ingresso> getIngressos() {
        return this.ingressos;
    }

    public void setEstoque(int novoEstoque) {
        this.estoque = novoEstoque;
    }

    public void setTime1(Time time){
        this.time1 = time;
    }

    public void setTime2(Time time){
        this.time2 = time;
    }

    public double obterValorArrecadado() {
        double soma = 0;

        for (Ingresso i : ingressos) {
            soma = soma + i.calcularValor();
        }

        return soma;
    }

    public void adicionar(Ingresso ing) {
        this.estoque = this.estoque -1;
        ingressos.add(ing);
    }

    public void remover(Ingresso ing) {
        this.estoque = this.estoque +1;
        ingressos.remove(ing);
    }

    public String toString(){
        String cod_ingressos = "";

        for(Ingresso i : ingressos) {
            cod_ingressos = cod_ingressos + i.getCodigo() + ",";
        }

        return "\nid=" + this.id + ", data=" + this.data + ", local=" + this.local + ", estoque=" + this.estoque + ", preco=" + this.preco + ", time1=" + this.time1.getNome() + " x " + "time2=" + this.time2.getNome() + "\ningressos:" + cod_ingressos;
    }   
}
