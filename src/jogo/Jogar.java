package jogo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author Iuri, silla
 */
public class Jogar {

	static int X = 1, O = -1, validar;
	static Boolean persistencia = false;
	private static Connection conexao = null;

	public static void main(String[] args) {
		if (persistencia) {
			conectar();
		}
		Casa casa = new Casa();
		AbstractJogo jogo;
		AbstractPlayer player;
		AbstractPlayer player2;

		jogo = new JogoVelha();

		//TODO: Define a classe utilizado pelo Player 1:
		player = new myFirstPlayer.Player(-1);
		player.setMinhaMarcaTabuleiro(X);
		player.setJogo(jogo);
		player.setMarcaTabuleiroOponente(O);

		//TODO: Defnine a classe utilizado pelo Player 2:
		player2 = new myFirstPlayer.Player(-1);
		player2.setMinhaMarcaTabuleiro(O);
		player2.setMarcaTabuleiroOponente(X);
		player2.setJogo(jogo);

		Tabuleiro t = new Tabuleiro(jogo);
		t.imprimir();

		while (true) {
			int resultado = 0;

			// jogada player 1
			casa = player.jogar(t.getTabuleiro());
			validar = jogo.validar_jogada(t.getTabuleiro(), casa, player);
			if (validar == 0) {
				t.setTabuleiro(jogo.efetuar_jogada(t.getTabuleiro(), casa,
						player));
			} else if (validar == -1 || validar == -2) {
				System.out.println("player1 WO: Jogada em casa invalida");
				break;
			}
			System.out.println("Jogada do player1");
			t.imprimir();
			if (persistencia) {
				gravarJogada(casa.getLinha(), casa.getColuna(),
						player.getIdUsuario());
			}

			// verifica se o player 1 venceu
			resultado = jogo.teste_terminal(t.getTabuleiro(),
					player.getMinhaMarcaTabuleiro());
			if (resultado != 0) {
				if (resultado == 1) {
					if (persistencia) {
						gravarVitoria(1);
					}
					System.out.println("Player 1 Ganhou");
				} else {
					if (persistencia) {
						gravarVitoria(0);
					}
					System.out.println("Empate");
				}
				break;
			}
			// jogada player 2
			casa = player2.jogar(t.getTabuleiro());
			validar = jogo.validar_jogada(t.getTabuleiro(), casa, player2);
			if (validar == 0) {
				t.setTabuleiro(jogo.efetuar_jogada(t.getTabuleiro(), casa,
						player2));
				;
			} else if (validar == -1 || validar == -2) {
				System.out.println("player2 WO: Jogada em casa invalida");
				break;
			}
			System.out.println("Jogada do player2");
			t.imprimir();
			if (persistencia) {
				gravarJogada(casa.getLinha(), casa.getColuna(),
						player2.getIdUsuario());
			}
			// verifica se o player 2 venceu
			resultado = jogo.teste_terminal(t.getTabuleiro(),
					player2.getMinhaMarcaTabuleiro());
			if (resultado != 0) {
				if (resultado == 1) {

					if (persistencia) {
						gravarVitoria(2);
					}
					System.out.println("Player 2 ganhou");
				} else {
					if (persistencia) {
						gravarVitoria(0);
					}
					System.out.println("Empate");
				}
				break;
			}

		}

		if (persistencia) {
			desconectar();
		}

	}

	private static void conectar() {
		try {
			Class.forName("org.postgresql.Driver");
			String jdbcUrl = "jdbc:postgresql://localhost:5432/tcc";
			conexao = DriverManager.getConnection(jdbcUrl, "postgres",
					"mardegan");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void gravarJogada(Integer x, Integer y, Integer idUsuario) {
		try {
			PreparedStatement pstmt = null;
			pstmt = conexao
					.prepareStatement("insert into movimento (_confronto, _usuario, eixo_x, eixo_y) values (?, ?, ?, ?)");
			pstmt.setInt(1, Torneio.CONFRONTO);
			pstmt.setInt(2, idUsuario);
			pstmt.setInt(3, x);
			pstmt.setInt(4, y);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private static void gravarVitoria(Integer player) {
		try {
			PreparedStatement pstmt = null;
			pstmt = conexao
					.prepareStatement("update confronto set finalizacao = ? where id = ?");
			pstmt.setInt(1, player);
			pstmt.setInt(2, Torneio.CONFRONTO);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private static void desconectar() {
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
