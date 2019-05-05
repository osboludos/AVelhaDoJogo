/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

/**
 *
 * @author Iuri
 */
public class PlayerVelha extends AbstractPlayer {


    public PlayerVelha(int depth) {
		super(depth);
	}

	@Override
    public Casa jogar(int[][] tab) {
        Casa casa = new Casa();
        loopExterno:
        for (int i = 0; i < getJogo().tam; i++) {
            for (int j = 0; j < getJogo().tam; j++) {
                if (tab[i][j] == 0) {
                    casa.setLinha(i);
                    casa.setColuna(j);
                    break loopExterno;
                }
            }
        }
        return casa;
    }

}
