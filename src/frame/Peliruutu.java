package frame;

/**
 * Miinaharavan pelilogiikassa k�ytett�v� luokka, joka kuvaa yksitt�ist� ruutua.
 * 
 * 
 */
public class Peliruutu {
	/**
	 * Peliruudun tila.
	 * 
	 */
	public int tila;
	/**
	 * Onko peliruutu miina (true) vaiko ei (false)?
	 */
	private final boolean miina;

	/**
	 * Peliruudun konstruktori. Peliruutu sis�lt�� tiedon siit�, onko se miina
	 * ja sen tilasta (onko auki, vihjenumero, yms).
	 * 
	 * @param miina
	 *            Onko uusi peliruutu miina, vaiko ei.
	 */
	public Peliruutu(boolean miina) {
		tila = miina ? Peliruudukko.OLI_MIINA : 0;
		this.miina = miina;
	}

	/**
	 * Kertoo, onko kyseinen peliruutu miina vai ei.
	 * 
	 * @return true, jos miina, muuten false.
	 */
	public boolean onkoMiina() {
		return miina;
	}
}
