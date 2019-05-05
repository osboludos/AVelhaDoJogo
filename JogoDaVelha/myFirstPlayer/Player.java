package myFirstPlayer;

import java.util.List;

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

		return jogadas.get(0).getCasa();
	}

}
