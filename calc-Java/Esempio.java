package kalk2;

import kalk2.errLabirinto;
import kalk2.errSize;
import kalk2.errSizeProd;
import kalk2.errSudoku;
import kalk2.errSudokuInit;
import kalk2.matrice;
import kalk2.matriceBinaria;
import kalk2.matriceQuadrata;

class Esempio {
	public static void main(String[] args) throws errSize,errSizeProd,errSudoku,errSudokuInit,errLabirinto {
		matrice m=new matrice(3,3,1);
		matrice n=new matrice(3,3,3);
		m.stampa();
		n.sum(m);
		n.stampa();
		n.mul(n);
		n.stampa();
		matriceQuadrata m1=new matriceQuadrata(9,0);
		m1.sudoku();
		m1.stampa();
		matriceBinaria b1=new matriceBinaria(10,1,0);
		b1.writeAllRandom();
		b1.trasposta();
		b1.stampa();
		b1=new matriceBinaria(6,6,0);
		b1.labirinto(0,0,5,5);
		b1=new matriceBinaria(6,6,1);
		try{
			b1.labirinto(0,0,5,5);
		}
		catch(errLabirinto e) {
			System.out.println("Labirinto impossibile");
		}
    }
}

