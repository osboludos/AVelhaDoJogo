package myFirstPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import jogo.AbstractPlayer;
import jogo.Casa;
import jogo.Jogada;
import jogo.JogoVelha;

public class Player extends AbstractPlayer {

	public Player(int depth) {
		super(depth);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Casa jogar(int[][] tab) {
		JogoVelha jogo = new JogoVelha();

		List<Jogada> jogadas = jogo.getJogadasValidas(tab, getMinhaMarcaTabuleiro());

		Map<Jogada, Integer> scores = new HashMap<>();
		for (Jogada j : jogadas){

			int m = NumeroDeLinhasVenciveis(j.getJogada(), getMinhaMarcaTabuleiro());
			int o = NumeroDeLinhasVenciveis(j.getJogada(), getMarcaTabuleiroOponente());
			int h = m - o;
			scores.put(j, h);
		}

		return JogadaComMelhorScore(scores).getCasa();
	}

	private Jogada JogadaComMelhorScore(Map<Jogada, Integer> scores){

		AtomicInteger maxScore = new AtomicInteger(Integer.MIN_VALUE);
		AtomicReference<Jogada> melhorJogada = new AtomicReference<>();
		scores.forEach((k, v)->{
			if (v > maxScore.get()){
				maxScore.set(v);
				melhorJogada.set(k);
			}
		});
		return melhorJogada.get();
	}



	private int NumeroDeLinhasVenciveis(int[][] tab, int marca){

		int count = 0;
		for (int i = 0; i < 3; i++){
			if (PodeGanharLinha(tab, i, marca)){
				count++;
			}
			if (PodeGanharColuna(tab, i, marca)){
				count++;
			}
		}
		if (PodeGanharDiag1(tab, marca)){
			count++;
		}
		if (PodeGanharDiag2(tab, marca)){
			count++;
		}
		return count;
	}

	private int[][] SimularJogada(int[][] tab, Jogada jogada, int marca){
		tab[jogada.getCasa().getLinha()][jogada.getCasa().getColuna()] = marca;
		return tab;
	}


	private boolean PodeGanharLinha(int[][] tab, int linha, int marcaTabuleiro) {
		for (int i = 0; i < 3; i++) {
			if (tab[linha][i] == -marcaTabuleiro) {
				return false;
			}
		}
		return true;
	}

	//    Verifica se o marcaTabuleiro ganho na coluna
	private boolean PodeGanharColuna(int[][] tab, int coluna, int marcaTabuleiro) {
		for (int i = 0; i < 3; i++) {
			if (tab[i][coluna] == -marcaTabuleiro) {
				return false;
			}
		}
		return true;
	}

	//    Verifica se o marcaTabuleiro ganho na diagonal principal
	private boolean PodeGanharDiag1(int[][] tab, int marcaTabuleiro) {
		for (int i = 0; i < 3; i++) {
			if (tab[i][i] == -marcaTabuleiro) {
				return false;
			}
		}
		return true;
	}

	//    Verifica se o marcaTabuleiro ganho na diagonal secundaria
	private boolean PodeGanharDiag2(int[][] tab, int marcaTabuleiro) {
		for (int i = 0; i < 3; i++) {
			if (tab[(3 - 1) - i][i] == -marcaTabuleiro) {
				return false;
			}
		}
		return true;
	}
}
