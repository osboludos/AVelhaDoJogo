package humanPlayer;

import java.util.Scanner;

import jogo.AbstractPlayer;
import jogo.Casa;

/**
 *
 * @author Lucas M. Oliveira
 */
public class Player extends AbstractPlayer {


    public Player(int depth) {
		super(depth);		
	}

	public Casa jogar(int[][] tab) {
        Casa casa = new Casa();
        Scanner leitor = new Scanner(System.in);  
        
        int i,j;  
          
        System.out.println ("Insira a linha");  
        i=leitor.nextInt();
        System.out.println ("Insira a coluna");  
        j=leitor.nextInt();   
        	if (tab[i][j] == 0) {
                    casa.setLinha(i);
                    casa.setColuna(j);
                }
        return casa;
    }

}

