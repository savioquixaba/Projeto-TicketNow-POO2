package modelo;


import java.util.ArrayList;


public class IngressoGrupo extends Ingresso {
    private ArrayList<Jogo> Jogos = new ArrayList<Jogo>();

    public IngressoGrupo(int codigo){
        super(codigo);
    }

    public int getCodigo() {
        return super.getCodigo();
    }

    public ArrayList<Jogo> getJogos() {
        return this.Jogos;
    }

    public void adicionar(Jogo jog){
        Jogos.add(jog);
    }

    public void remover(Jogo jog) {
        Jogos.remove(jog);
    }

    @Override
    public double calcularValor() {
        double soma = 0;

        for (Jogo j : Jogos) {
            soma = soma + j.getPreco();
        }

        soma = soma * 0.9;
        
        return soma; 
    }

    @Override
    public String toString() {
        String ids = "";

        for(Jogo j : Jogos) {
            ids = ids + j.getId() +",";
        }

	return super.toString() + ", jogos:" + ids;
    }
}
