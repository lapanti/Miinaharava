package frame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * Pelipaneeli, joka on miinaharavan "pelialue", joka sis‰lt‰‰ myˆs
 * toiminnallisuutta.
 * 
 */
public class Pelipaneeli extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private Ruutunappi[][] taulukko;
	private Peliruudukko pr;
	private Miinapeli mp;
	private boolean loppunut = false;
	private int miinat = 0;
	private int liput = 0;
	private int avatut = 0;

	/**
	 * Pelipaneelin konstruktori.
	 * 
	 * @param ruudukko
	 *            Ruudukko, jonka mukaiseksi n‰kym‰ tehd‰‰n.
	 */
	public Pelipaneeli(Peliruudukko ruudukko, Miinapeli mip) {
		super();
		pr = ruudukko;
		int leveys = pr.annaLeveys();
		int korkeus = pr.annaKorkeus();
		taulukko = new Ruutunappi[leveys][korkeus];

		// Asetetaan ja luodaan oletusarvoinen muotoilu napeille (miinoille)
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0);
		// Lis‰t‰‰n jokaiseen kohtaan nappi;
		for (int x = 0; x < leveys; x++) {
			for (int y = 0; y < korkeus; y++) {
				if (pr.onMiina(x, y)) {
					miinat++;
				}
				c.gridx = x;
				c.gridy = y;
				Ruutunappi nappi = new Ruutunappi(x, y);
				nappi.addMouseListener(this);
				add(nappi, c);
				taulukko[x][y] = nappi;
			}
		}
		mp = mip;
	}

	/**
	 * Aiheuttaa pelin loppumisen h‰viˆˆn.
	 * 
	 */
	public void havisit() {
		peliLoppui();
		mp.asetaAlapalkinTeksti("Game over");
	}

	/**
	 * Lopettaa pelin ja paljastaa kaikki j‰ljell‰ olevat miinat ja "hudin"
	 * tehneet liput.
	 */
	public void peliLoppui() {
		for (int x = 0; x < taulukko.length; x++) {
			for (int y = 0; y < taulukko[0].length; y++) {
				Ruutunappi nappi = taulukko[x][y];
				if (!pr.onLiputettu(x, y) && pr.onMiina(x, y)) {
					nappi.naytaMiina();
				} else if (pr.onLiputettu(x, y) && !pr.onMiina(x, y)) {
					nappi.huti();
				}
			}
		}
		loppunut = true;
	}

	/**
	 * Asettaa pelin loppumaan mik‰li peli on voitettu, muulloin muuttaa
	 * alapalkin teksti‰ sopivaksi.
	 * 
	 * @return Voitettiinko (true) vai h‰vittiinkˆ (false).
	 */
	public boolean peliVoitettu() {
		if (liput < miinat / 2) {
			mp.asetaAlapalkinTeksti("Really flag those mines! " + liput + "/"
					+ miinat);
		} else if (liput > miinat / 2) {
			mp.asetaAlapalkinTeksti("Nice flagging! " + liput + "/" + miinat);
		}
		if (!loppunut
				&& avatut == (taulukko.length * taulukko[0].length - miinat)) {
			mp.asetaAlapalkinTeksti("You won the game! YEY!");
			return true;
		}
		return false;
	}

	/**
	 * Paljastaa avatun ruudun viereiset ruudut.
	 * 
	 * @param x
	 *            Avatun ruudun x-koordinaatti.
	 * @param y
	 *            Avatun ruudun y-koordinaatti.
	 */
	public void paljastaViereiset(int x, int y) {
		// K‰yd‰‰n l‰pi kaikki viereiset nappulat ja yritet‰‰n avata ne.
		for (int i = x - 1; i < x + 2; i++) {
			for (int j = y - 1; j < y + 2; j++) {
				if (i != x || j != y) {
					try {
						int vihje = pr.avaa(i, j);
						if (vihje >= 0) {
							taulukko[i][j].naytaVihje(vihje);
							avatut++;
							/*
							 * Mik‰li lˆytyy lis‰‰ nollia, paljastetaan myˆs
							 * niiden viereiset.
							 */
							if (vihje == 0) {
								paljastaViereiset(i, j);
							}
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Mik‰li peli on loppunut, ei en‰‰ anneta pelaajan tehd‰ mit‰‰n.
		if (loppunut) {
			return;
		}
		// Otetaan talteen painetun napin koordinaatit.
		Ruutunappi nappi = (Ruutunappi) e.getComponent();
		int x = nappi.annaX();
		int y = nappi.annaY();
		/*
		 * Mik‰li klikattiin vasemmalla, avataan ja jos tuli miina, h‰vit‰‰n.
		 * Lipussa tai avatussa ei tehd‰ mit‰‰n.
		 */
		if (e.getButton() == MouseEvent.BUTTON1) {
			int vihje = pr.avaa(x, y);
			if (vihje == Peliruudukko.OLI_MIINA) {
				nappi.naytaMiina();
				havisit();
				return;
			} else if (vihje >= 0) {
				nappi.naytaVihje(vihje);
				avatut++;
				if (vihje == 0) {
					paljastaViereiset(x, y);
				}
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (!pr.onAuki(x, y)) {
				boolean lippu = !pr.onLiputettu(x, y);
				pr.asetaLippu(x, y, lippu);
				nappi.naytaLippu(lippu);
				if (lippu) {
					liput++;
				}
			}
		}
		// Tarkistetaan, voitettiinko peli.
		loppunut = peliVoitettu();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}
