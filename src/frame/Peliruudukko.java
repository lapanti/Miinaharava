package frame;

import java.util.Random;

/**
 * Miinaharavan pelilogiikka.
 * 
 * 
 */
public class Peliruudukko {
	/**
	 * Vakioarvo, joka kuvaa sit‰, ett‰ avattava ruutu oli jo auki. Arvon on
	 * oltava negatiivinen, eik‰ se saa olla sama muiden t‰ss‰ luokassa
	 * m‰‰riteltyjen vakioiden kanssa.
	 * 
	 */
	public static final int OLI_JO_AUKI = -2;
	/**
	 * Vakioarvo, joka kuvaa sit‰, ett‰ ruutu, jota yritettiin avata, oli
	 * merkitty lipulla eik‰ avaaminen n‰in ollen onnistunut. Arvon on oltava
	 * negatiivinen, eik‰ se saa olla sama muiden t‰ss‰ luokassa m‰‰riteltyjen
	 * vakioiden kanssa.
	 * 
	 */
	public static final int OLI_LIPUTETTU = -3;
	/**
	 * Vakioarvo, joka kuvaa sit‰, ett‰ avattu ruutu oli miina (ja peli p‰‰ttyi
	 * ik‰v‰sti). Arvon on oltava negatiivinen, eik‰ se saa olla sama muiden
	 * t‰ss‰ luokassa m‰‰riteltyjen vakioiden kanssa.
	 * 
	 */
	public static final int OLI_MIINA = -4;
	private Peliruutu[][] taulukko;

	/**
	 * Luo uuden peliruudukon, jonka leveys ja korkeus on annettu parametreina.
	 * Ruudukon ruuduista valitaan satunnaisesti miinoiksi niin monta kuin
	 * parametri miinoja m‰‰r‰‰, ei kuitenkaan enemp‰‰ kuin puolet kaikista
	 * ruuduista. Jos leveydeksi, korkeudeksi tai miinam‰‰r‰ksi on annettu nolla
	 * tai negatiivinen luku, sen arvoksi asetetaan 1.
	 * 
	 * @param leveys
	 *            Peliruudukon leveys eli ruutujen m‰‰r‰ vaakasuunnassa.
	 * @param korkeus
	 *            Peliruudukon korkeus eli ruutujen m‰‰r‰ pystysuunnassa.
	 * @param miinoja
	 *            Peliruudukkoon sijoitettavien miinojen m‰‰r‰.
	 */
	public Peliruudukko(int leveys, int korkeus, int miinoja) {
		if (leveys < 1) {
			leveys = 1;
		}
		if (korkeus < 1) {
			korkeus = 1;
		}
		if (miinoja < 1) {
			miinoja = 1;
		}
		taulukko = new Peliruutu[leveys][korkeus];
		// T‰ytet‰‰n ensin taulukko tyhjill‰ ruuduilla.
		for (int x = 0; x < leveys; x++) {
			for (int y = 0; y < korkeus; y++) {
				taulukko[x][y] = new Peliruutu(false);
			}
		}
		// Sitten lis‰t‰‰n miinat.
		while (miinoja > 0) {
			int x = new Random().nextInt(leveys);
			int y = new Random().nextInt(korkeus);
			if (!taulukko[x][y].onkoMiina()) {
				taulukko[x][y] = new Peliruutu(true);
				miinoja--;
			}
		}
	}

	/**
	 * Palauttaa ruudukon leveyden.
	 * 
	 * @return Ruudukon leveys.
	 */
	public int annaLeveys() {
		return taulukko.length;
	}

	/**
	 * Palauttaa ruudukon korkeuden.
	 * 
	 * @return Ruudukon korkeus.
	 */
	public int annaKorkeus() {
		return taulukko[0].length;
	}

	/**
	 * Kertoo, onko annetuissa koordinaateissa oleva ruutu merkitty lipulla.
	 * 
	 * @param x
	 *            Ruudun x-koordinaatti.
	 * @param y
	 *            Ruudun y-koordinaatti.
	 * @return true, jos kyseinen ruutu on liputettu, muuten false.
	 * @throws java.lang.ArrayIndexOutOfBoundsException
	 *             Jos koordinaatit olivat ruudukon rajojen ulkopuolella.
	 */
	public boolean onLiputettu(int x, int y)
			throws java.lang.ArrayIndexOutOfBoundsException {
		return (taulukko[x][y].tila == Peliruudukko.OLI_LIPUTETTU);
	}

	/**
	 * Kertoo, onko annetuissa koordinaateissa oleva ruutu kaivettu auki.
	 * 
	 * @param x
	 *            Ruudun x-koordinaatti.
	 * @param y
	 *            Ruudun y-koordinaatti.
	 * @return true, jos kyseinen ruutu on kaivettu auki, muuten false.
	 * @throws java.lang.ArrayIndexOutOfBoundsException
	 *             Jos koordinaatit olivat ruudukon rajojen ulkopuolella.
	 */
	public boolean onAuki(int x, int y)
			throws java.lang.ArrayIndexOutOfBoundsException {
		Peliruutu ruutu = taulukko[x][y];
		return (ruutu.tila == Peliruudukko.OLI_JO_AUKI);
	}

	/**
	 * Kertoo, onko annetuissa koordinaateissa oleva ruutu miina.
	 * 
	 * @param x
	 *            Ruudun x-koordinaatti.
	 * @param y
	 *            Ruudun y-koordinaatti.
	 * @return true, jos kyseinen ruutu on miina, muuten false.
	 * @throws java.lang.ArrayIndexOutOfBoundsException
	 *             Jos koordinaatit olivat ruudukon rajojen ulkopuolella.
	 */
	public boolean onMiina(int x, int y)
			throws java.lang.ArrayIndexOutOfBoundsException {
		return taulukko[x][y].onkoMiina();
	}

	/**
	 * Kaivaa annetuissa koordinaateissa olevan ruudun auki, mik‰li se ei ollut
	 * merkitty lipulla tai auki jo entuudestaan. Metodin palauttama lukuarvo
	 * kertoo tarkemmin siit‰, mit‰ avatessa tapahtui. Jos ruutu oli liputettu,
	 * sit‰ ei avata vaan palautetaan t‰ss‰ luokassa m‰‰ritelty (negatiivinen)
	 * vakioarvo OLI_LIPUTETTU. Samoin jos ruutu oli ennest‰‰n auki, sit‰ ei
	 * avata uudestaan vaan palautetaan (negatiivinen) vakio OLI_JO_AUKI. Jos
	 * ruudun avaaminen onnistui ja siin‰ ei ollut miinaa, palautetaan ruudun
	 * vihjenumero eli jokin luku nollasta kahdeksaan, riippuen siit‰, montako
	 * miinaa ruudun naapureiden joukossa on. Jos avattu ruutu taas oli miina,
	 * palautetaan t‰m‰n merkiksi (niin ik‰‰n negatiivinen) vakioarvo OLI_MIINA.
	 * Metodin kutsuja reagoikoon paluuarvoon parhaaksi katsomallaan tavalla.
	 * 
	 * @param x
	 *            Ruudun x-koordinaatti.
	 * @param y
	 *            Ruudun y-koordinaatti.
	 * @return Nolla tai positiivinen vihjenumero, jos avattu ruutu ei
	 *         sis‰lt‰nyt miinaa, tai jonkin t‰ss‰ luokassa m‰‰ritellyist‰
	 *         negatiivisista vakioarvoista, jos ruudussa oli miina tai sit‰ ei
	 *         voinut syyst‰ tai toisesta avata.
	 * 
	 * @throws java.lang.ArrayIndexOutOfBoundsException
	 *             Jos koordinaatit olivat ruudukon rajojen ulkopuolella.
	 */
	public int avaa(int x, int y)
			throws java.lang.ArrayIndexOutOfBoundsException {
		Peliruutu avattava = taulukko[x][y];
		if (onAuki(x, y) || onLiputettu(x, y) || onMiina(x, y)) {
			return avattava.tila;
		} else {
			avattava.tila = Peliruudukko.OLI_JO_AUKI;
			return annaVihjenumero(x, y);
		}
	}

	/**
	 * Asettaa annetuissa koordinaateissa olevalle, viel‰ avaamattomalle
	 * ruudulle lipun tai ottaa sen pois parametrin lippu arvosta riippuen ñ
	 * true tarkoittaa liputusta, false liputuksen poistoa. Ei tee mit‰‰n, jos
	 * ruutu on jo avattu, tai jos liputetulle ruudulle yritet‰‰n asettaa lippua
	 * tai ottaa liputtomalta ruudulta lippua pois.
	 * 
	 * @param x
	 *            Ruudun x-koordinaatti.
	 * @param y
	 *            Ruudun y-koordinaatti.
	 * @param lippu
	 *            true, jos ruutuun halutaan asettaa lippu, false, jos lippu
	 *            halutaan ottaa pois.
	 * @return true, jos liputuksen muuttaminen onnistui (eli ruutu ei viel‰
	 *         ollut avattu ja lis‰ksi liputusta muutettiin oikeaan suuntaan),
	 *         muutoin false.
	 * @throws java.lang.ArrayIndexOutOfBounds
	 *             Jos koordinaatit olivat ruudukon rajojen ulkopuolella.
	 */
	public boolean asetaLippu(int x, int y, boolean lippu)
			throws java.lang.ArrayIndexOutOfBoundsException {
		Peliruutu liputettava = taulukko[x][y];
		if (liputettava.tila == Peliruudukko.OLI_JO_AUKI
				|| lippu == (liputettava.tila == Peliruudukko.OLI_LIPUTETTU)) {
			return false;
		} else {
			/*
			 * Mik‰li liputetaan, laitetaan tilaksi lippu, muuten laitetaan
			 * asianmukaisesti joko OLI_MIINA tai 0
			 */
			liputettava.tila = lippu ? Peliruudukko.OLI_LIPUTETTU : liputettava
					.onkoMiina() ? Peliruudukko.OLI_MIINA : 0;
			return true;
		}
	}

	/**
	 * Palauttaa annettuja koordinaatteja vastaavan ruudun vihjenumeron eli
	 * ruutua ymp‰rˆivien miinaruutujen m‰‰r‰n. Myˆs miinaruuduilla on
	 * vihjenumero, vaikka k‰yt‰nnˆss‰ sit‰ ei koskaan tarvita mihink‰‰n.
	 * 
	 * @param x
	 *            Ruudun x-koordinaatti.
	 * @param y
	 *            Ruudun y-koordinaatti.
	 * @return Ruudun ymp‰rill‰ olevien miinojen m‰‰r‰ - siis jokin luku
	 *         nollasta kahdeksaan.
	 * @throws java.lang.ArrayIndexOutOfBoundsException
	 *             Jos koordinaatit olivat ruudukon rajojen ulkopuolella.
	 */
	public int annaVihjenumero(int x, int y)
			throws java.lang.ArrayIndexOutOfBoundsException {
		int lkm = 0;
		// K‰yd‰‰n l‰pi kaikki ymp‰rˆiv‰t ruudut ja lasketaan miinojen m‰‰r‰.
		for (int i = x - 1; i < x + 2; i++) {
			for (int j = y - 1; j < y + 2; j++) {
				if (i >= 0 && j >= 0 && i < annaLeveys() && j < annaKorkeus()) {
					if (taulukko[i][j].onkoMiina()) {
						lkm++;
					}
				}
			}
		}
		return lkm;
	}
}
