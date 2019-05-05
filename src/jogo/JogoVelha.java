/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iuri
 */
public class JogoVelha extends AbstractJogo {

    public JogoVelha() {
        tam = 3;
    }
 

    @Override
    public int[][] tabuleiro_inicial() {
        return new int[tam][tam];
    }
    
    @Override
    public int[][] efetuar_jogada(int[][] tabOriginal, Casa jogada, AbstractPlayer jogador){
        tabOriginal[jogada.getLinha()][jogada.getColuna()] = jogador.getMinhaMarcaTabuleiro();
        return tabOriginal;
    }
    
    @Override
    public int validar_jogada(int[][] tabOriginal, Casa jogada, AbstractPlayer jogador) {
        
        int retorno = 0;

        if (tabOriginal[jogada.getLinha()][jogada.getColuna()] != 0) {
            retorno = -1;
        }


        return retorno;
    }
    
    @Override
    
    public List<Jogada> getJogadasValidas(int[][] tabOriginal, int marcaTabuleiro) {

    	List<Casa> casas = new ArrayList<>();
        
//    	List<int[][]> tabuleiros = new ArrayList<>();
        
    	List<Jogada> tabuleiros = new ArrayList<Jogada>();
    	
    	PlayerVelha jogador = new PlayerVelha(-1);
        
    	jogador.setMinhaMarcaTabuleiro(marcaTabuleiro);
    	
        int tamanho = tabOriginal.length;
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                Casa casaTeste = new Casa(i, j);
                if (validar_jogada(tabOriginal, casaTeste, null) == 0) {
                    casas.add(casaTeste);
                }
            }
        }
        
        for (Casa casa : casas) {
            int[][] tab = tabuleiro_inicial();
            for (int i = 0; i < tam; i++) {
                for (int j = 0; j < tam; j++) {
                    tab[i][j] = tabOriginal[i][j];
                }
            }
            //tabuleiros.add(efetuar_jogada(tab, casa, jogador));
            tabuleiros.add(new Jogada(efetuar_jogada(tab, casa, jogador),casa));
        }
        
        return tabuleiros;
    }

    //    Verifica se o fim do jogo foi atingido
    @Override
    public int teste_terminal(int[][] tab, int marcaTabuleiro) {
        int retorno = 0;
        if (ganhou(tab, marcaTabuleiro)) {
            retorno = 1;
        } else if (semEspaco(tab)) {
            retorno = 2;
        }
        return retorno;
    }

//    Verifica se o marcaTabuleiro ganhou
    public boolean ganhou(int[][] tab, int marcaTabuleiro) {
        for (int i = 0; i < tam; i++) {
            if (ganhouLinha(tab, i, marcaTabuleiro) || ganhouColuna(tab, i, marcaTabuleiro)) {
                return true;
            }
        }
        if (ganhouDiag1(tab, marcaTabuleiro) || ganhouDiag2(tab, marcaTabuleiro)) {
            return true;
        }
        return false;
    }

//    Verifica se o marcaTabuleiro ganho na linha
    public boolean ganhouLinha(int[][] tab, int linha, int marcaTabuleiro) {
        for (int i = 0; i < tam; i++) {
            if (tab[linha][i] != marcaTabuleiro) {
                return false;
            }
        }
        return true;
    }

//    Verifica se o marcaTabuleiro ganho na coluna
    public boolean ganhouColuna(int[][] tab, int coluna, int marcaTabuleiro) {
        for (int i = 0; i < tam; i++) {
            if (tab[i][coluna] != marcaTabuleiro) {
                return false;
            }
        }
        return true;
    }

//    Verifica se o marcaTabuleiro ganho na diagonal principal
    public boolean ganhouDiag1(int[][] tab, int marcaTabuleiro) {
        for (int i = 0; i < tam; i++) {
            if (tab[i][i] != marcaTabuleiro) {
                return false;
            }
        }
        return true;
    }

//    Verifica se o marcaTabuleiro ganho na diagonal secundaria
    public boolean ganhouDiag2(int[][] tab, int marcaTabuleiro) {
        for (int i = 0; i < tam; i++) {
            if (tab[(tam - 1) - i][i] != marcaTabuleiro) {
                return false;
            }
        }
        return true;
    }

//    Verifica se ainda existe espaco para mais uma jogada
    public boolean semEspaco(int[][] tab) {
        for (int linha = 0; linha < tam; linha++) {
            for (int coluna = 0; coluna < tam; coluna++) {
                if (tab[linha][coluna] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
