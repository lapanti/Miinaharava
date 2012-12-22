package frame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

/**
 * Ruutunappi on miinaharavaan erikoistunut JButtoni.
 * 
 * 
 */
public class Ruutunappi extends JButton {
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	/**
	 * Ruutunapin konstruktori.
	 * 
	 * @param x
	 *            Ruutunapin sijainti x-koordinaatistossa.
	 * @param y
	 *            Ruutunapin sijainti y-koordinaatistossa.
	 */
	public Ruutunappi(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		setBackground(new Color(170, 170, 170));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setPreferredSize(new Dimension(25, 25));
	}

	/**
	 * Palauttaa ruutunapin sijainnin leveyden suhteen.
	 * 
	 * @return Sijainti x-koordinaatistossa.
	 */
	public int annaX() {
		return x;
	}

	/**
	 * Palauttaa ruutunapin sijainnin korkeuden suhteen.
	 * 
	 * @return Sijainti y-koordinaatistossa.
	 */
	public int annaY() {
		return y;
	}

	/**
	 * N‰ytt‰‰ napin alla piilossa olevan miinan.
	 * 
	 */
	public void naytaMiina() {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setIcon(new ImageIcon("pommi.gif"));
	}

	/**
	 * N‰ytt‰‰ napin vihjenumeron.
	 * 
	 * @param vihjenumero
	 *            Nappia ymp‰rˆivien miinojen m‰‰r‰.
	 */
	public void naytaVihje(int vihjenumero) {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setText("" + vihjenumero);
		switch (vihjenumero) {
		case 0:
			setText("");
			break;
		case 1:
			setForeground(new Color(0, 0, 205)); // Vaaleansininen.
			break;
		case 2:
			setForeground(new Color(34, 139, 34)); // Vaaleanvihre‰.
			break;
		case 3:
			setForeground(new Color(255, 0, 0)); // Punainen.
			break;
		case 4:
			setForeground(new Color(25, 25, 112));// Tummansininen.
			break;
		case 5:
			setForeground(new Color(178, 34, 34)); // Tummanpunainen.
			break;
		case 6:
			setForeground(new Color(0, 100, 0)); // Tummanvihre‰.
			break;
		case 7:
			setForeground(new Color(255, 192, 203)); // Pinkki.
			break;
		}
	}

	/**
	 * N‰ytt‰‰ napissa joko lipun, tai poistaa sen.
	 * 
	 * @param lippu
	 *            Ollaanko lis‰‰m‰ss‰ lippua (true) vai poistamassa (false).
	 */
	public void naytaLippu(boolean lippu) {
		if (lippu) {
			setIcon(new ImageIcon("lippu.gif"));
		} else if (!lippu) {
			setBackground(new Color(170, 170, 170));
			setIcon(null);
		}
	}

	/**
	 * N‰ytt‰‰ lipun olleen v‰‰r‰ss‰ paikassa.
	 * 
	 */
	public void huti() {
		setBackground(Color.WHITE);
		setIcon(new ImageIcon("huti.gif"));
	}
}
