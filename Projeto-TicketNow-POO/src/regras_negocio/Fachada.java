package regras_negocio;
/**********************************
 * Projeto2 de POO (2022.2)
 * 
 * Grupo de alunos: 
 * Savio,Raqueline
 * 
 **********************************/


import java.util.ArrayList;
import java.util.Random;

import modelo.Ingresso;
import modelo.IngressoGrupo;
import modelo.IngressoIndividual;
import modelo.Jogo;
import modelo.Time;
import repositorio.Repositorio;


public class Fachada {
	private static Repositorio repositorio = new Repositorio();	
	
	private Fachada() {}	
	
	public static ArrayList<Time> listarTimes() {
		return repositorio.getTimes();
	}

	public static ArrayList<Jogo> listarJogos() {
		return repositorio.getJogos();
	}
	
	public static ArrayList<Ingresso> listarIngressos() {
		return repositorio.getIngressos();
	}

	public static ArrayList<Jogo> listarJogos(String data) throws Exception{
		if (data.equals("")) {
			throw new Exception("Nenhum jogo - data não");
		}
		
		ArrayList<Jogo> JogosTemp = repositorio.getJogos();
		ArrayList<Jogo> JogosData = new ArrayList<Jogo>();

		for(Jogo k : JogosTemp) {
			if (k.getData().equals(data)) {
				JogosData.add(k);
			}
		}

		return JogosData;
	}

	public static Ingresso localizarIngresso(int codigo) {
		return repositorio.localizarIngresso(codigo);
	}
	
	
	public static Jogo localizarJogo(int id) {
		return repositorio.localizarJogo(id);
	}
	
	
	public static Time criarTime(String nome, String origem) throws Exception {
		if (repositorio.localizarTime(nome) != null) {
			throw new Exception("Nao foi possivel criar o time - time ja criado:" + nome);
		};

		Time time = new Time(nome, origem);

		repositorio.adicionar(time);
		repositorio.salvar();	

		return time;
	}

	
	public static Jogo 	criarJogo(String data, String local, int estoque, double preco, String nometime1, String nometime2)  throws Exception {
		Time time1 = repositorio.localizarTime(nometime1);
		Time time2 = repositorio.localizarTime(nometime2);

		if (time1 == null) {
			throw new Exception("Nao foi possivel criar o jogo - time inexistente:" + nometime1);
		}

		if (time2 == null) {
			throw new Exception("Nao foi possivel criar o jogo - time inexistente:" + nometime2);
		}

		if (local == "") {
			throw new Exception("Nao foi possivel criar o jogo - local vazio");
		}

		if (data == "") {
			throw new Exception("Nao foi possivel criar o jogo - data vazia");
		}

		if (estoque <= 0) {
			throw new Exception("Nao foi possivel criar o jogo - estoque menor ou igual a zero:" + estoque);
		}

		if (preco <= 0) {
			throw new Exception("Nao foi possivel criar o jogo - preco menor ou igual a zero:" + preco);
		}

		Jogo jogo = new Jogo(repositorio.getTotalJogos()+1, data, local, estoque, preco, time1, time2);
		
		time1.adicionar(jogo);
		time2.adicionar(jogo);
		repositorio.adicionar(jogo);
		repositorio.salvar();

		return jogo;
	}

	
	public static void apagarJogo(int id) throws Exception{
		Jogo jogo = repositorio.localizarJogo(id);

		if(jogo == null) {
			throw new Exception("Nao foi possivel apagar o jogo- codigo do jogo inexistente:" + id);
		}
		
		if(jogo.getIngressos().size() != 0) {
			throw new Exception("Nao foi possivel apagar o jogo - jogo possui ingressos");
		}

		repositorio.remover(jogo);
		repositorio.salvar();
	}
	 

	 
	public static IngressoIndividual criarIngressoIndividual(int id) throws Exception{
		Jogo jogo = repositorio.localizarJogo(id);

		if(jogo == null) {
			throw new Exception("Nao foi possivel criar o ingresso - codigo do jogo inexistente:" + id);
		}

		Random sorteio = new Random();
		int numero;
		
		while(true) {
			numero = sorteio.nextInt(1000000);

			if (repositorio.localizarIngresso(numero) == null) {
				break;
			}
		} 

		IngressoIndividual ingresso = new IngressoIndividual(numero);
		
		ingresso.setJogo(jogo);
		jogo.adicionar(ingresso);
		repositorio.adicionar(ingresso);
		repositorio.salvar();
		
		return ingresso;
	}
	
	
	
	public static IngressoGrupo	criarIngressoGrupo(int[] id) throws Exception{
		for (int i : id) {
			Jogo jogo = repositorio.localizarJogo(i);

			if(jogo == null) {
				throw new Exception("Nao criou ingresso - codigo do jogo inexistente:" + i);
			}
		}

		Random sorteio = new Random();
		int numero;
		
		while(true) {
			numero = sorteio.nextInt(1000000);

			if (repositorio.localizarIngresso(numero) == null) {
				break;
			}
		}

		IngressoGrupo ingresso = new IngressoGrupo(numero);

		for (int i : id) {
			Jogo jogo = repositorio.localizarJogo(i);

			ingresso.adicionar(jogo);
			jogo.adicionar(ingresso);
		}
			
		repositorio.adicionar(ingresso);
		repositorio.salvar();

		return ingresso;
	}
	
	public static void	cancelarIngresso(int codigo) throws Exception {
		Ingresso ingresso = repositorio.localizarIngresso(codigo);
		
		if (ingresso == null) {
			throw new Exception("Nao cancelou ingresso - codigo do ingresso inexistente:" + codigo);
		}
		
		if (ingresso instanceof IngressoIndividual) {	
			IngressoIndividual i = (IngressoIndividual) ingresso;
			
			i.getJogo().remover(i);
			i.remover();	
		}

		if (ingresso instanceof IngressoGrupo) {
			IngressoGrupo i = (IngressoGrupo) ingresso;
			ArrayList<Jogo> jogos = new ArrayList<>();

			for (Jogo j : jogos) {
				j.remover(i);
				i.remover(j);
			}
		}

		repositorio.remover(ingresso);
		repositorio.salvar();
	}
}
