package it.polito.tdp.Regine.model;
import java.util.*;

public class Regine {
	
	private int N; // numero di righe/colonne scacchiera
	private List<Integer> soluzione;
	
	public List<Integer> risolvi(int N) {
		this.N=N;
		List<Integer> parziale = new ArrayList<Integer>(); // Non LinkedList, perchè nel metodoposValida() uso
		                                                   // il metodo get, che nelle LinkedList implica ritardo
		
		cerca(parziale, 0);
		
		return this.soluzione;
	}
	
	// N = numero righe e colonne della scacchiera (righe e colonne numerate da 0 a N-1)
	// Ad ogni livello posizioniamo una regina in una nuova riga
	
	// soluzione parziale = lista colonne in cui mettere le regine per le prime righe
	// livello = numero di righe già piene (se livello = i, devo mettere la regina nella riga i)
	
	/*[0] // Cioè prima regina nella riga 0 colonna 1
	 *   [0,2] // Cioè prima regina nella riga 0 colonna 1 e seconda regina nella riga 1 colonna 2
	 *        [0,2,1]  // Cioè prima regina nella riga 0 colonna 1, seconda regina nella riga 1 colonna 2, terza regina nella riga 2 colonna 1
	 */
	
	/**
	 * Restituisce LA PRIMA SOLUZIONE valida
	 * Se cerca = true --> trovato; Se cerca = false --> cerca ancora
	 * 
	 * @param parziale
	 * @param livello
	 * @return
	 */
	private boolean cerca(List<Integer> parziale, int livello) {
		if(livello==N) { // Caso terminale
			this.soluzione = new ArrayList<>(parziale);
			return true;
		}
		else {
			for(int colonna=0;colonna<N;colonna++) {
				// controllo se la posizione nella casella [livello][colonna] è valida
				// se sì aggiungi a parziale e fai ricorsione
				
				if(posValida(parziale,colonna)) { // Provo la prima colonna valida e vedo gli effetti sui livelli successivi
					parziale.add(colonna);
					boolean trovato = cerca(parziale,livello+1);
					if(trovato == true)
						return true; // Se trovassi un true (Soluzione trovata) --> Restituisco true "a cascata" per uscire dal metodo cerca
					parziale.remove(parziale.size()-1); // Se la ricorsione non è andata bene tolgo l'ultimo elemento
					                                    // e ne provo un altro (BACKTRACKING)
				}
				
			}
		}
		return false; // Nè io nè le chiamate ricorsive che ho fatto abbiamo trovato la soluzione --> continuo a fare prove
	}

	private boolean posValida(List<Integer> parziale, int colonna) {
		// Controlla se la posizione che sto provando è valida
		// Non ci deve essere un'altra regina sulla stessa colonna
		
		int livello = parziale.size(); // La riga in cui sto inserendo la regina
		
		if(parziale.contains(colonna))
			return false;
		
		// Per il controllo diagonale osserviamo che i punti sulla stessa diagonale, se sommo (o sottraggo) riga e colonna sono uguali
		// Controllo a posizione RC della regina che voglio inserire con quella delle regine già esistenti
		
		for(int r = 0; r<livello; r++) {
			int c = parziale.get(r);
			
			if(r+c == livello+colonna || r-c == livello-colonna)
				return false;
		}
		
		return true;
	}

}
