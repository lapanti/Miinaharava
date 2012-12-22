package frame;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Miinaharava-pelin ulkoasun rakentava luokka. Käsittelee myös valikon
 * tapahtumat.
 * 
 * 
 */
public class Miinapeli extends JFrame {
	private static final long serialVersionUID = 1L;
	/**
	 * Helpon pelin "asetukset" eli 9 x 9 ruudukko 10 miinalla. Oletusarvo
	 * uudessa pelissä.
	 * 
	 */
	public static final int[] HELPPO = { 9, 9, 10 };
	/**
	 * Normaalin pelin "asetukset" eli 16 x 16 ruudukko 40 miinalla.
	 * 
	 */
	public static final int[] NORMAALI = { 16, 16, 40 };
	/**
	 * Vaikean pelin "asetukset" eli 30 x 16 ruudukko 99 miinalla.
	 * 
	 */
	public static final int[] VAIKEA = { 30, 16, 99 };
	/**
	 * Vaikeustaso, määrittelee pelin koon ja miinojen määrän.
	 * 
	 */
	private int[] vt = Miinapeli.HELPPO;
	private JPanel alapalkki;
	private Pelipaneeli paneeli;
	private JLabel alapalkinsisalto;

	/**
	 * Konstruktori, joka rakentaa oletusnäkymäisen pelinäkymän.
	 * 
	 */
	public Miinapeli() {
		super("Minesweeper");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		// Tehdään valikko.
		setJMenuBar(teeValikko());
		alapalkinsisalto = new JLabel();
		// Tehdään itse peliruutu.
		paneeli = new Pelipaneeli(new Peliruudukko(vt[0], vt[1], vt[2]), this);
		add(paneeli, BorderLayout.CENTER);
		// Tehdään alapalkki ja asetetaan sen teksti.
		alapalkki = new JPanel();
		alapalkinsisalto
				.setText("Started a new game of awesome Minesweeper...");
		alapalkki.add(alapalkinsisalto);
		add(alapalkki, BorderLayout.SOUTH);
		// Pakataan yms.
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	/**
	 * Muuttaa alapalkissa olevaa tekstiä.
	 * 
	 * @param teksti
	 *            Alapalkkiin asetettava uusi teksti.
	 */
	public void asetaAlapalkinTeksti(String teksti) {
		alapalkinsisalto.setText(teksti);
		repaint();
	}

	/**
	 * Luokka, joka rakentaa Miinaharava-pelin valikon.
	 * 
	 * @return Valikko peliä varten.
	 */
	private JMenuBar teeValikko() {
		// Tehdään valikkopalkkipalkkipalkki.
		JMenuBar valikkopalkki = new JMenuBar();

		// Tehdään valikkopalkkipalkki.
		JMenu pelivalikko = new JMenu("Game");
		pelivalikko.setMnemonic(KeyEvent.VK_G);
		pelivalikko.getAccessibleContext().setAccessibleDescription(
				"Game settings etc.");
		valikkopalkki.add(pelivalikko);

		// Tehdään pelin uudelleen aloittava nappi.
		JMenuItem pelinaloitus = new JMenuItem("Restart", KeyEvent.VK_R);
		pelinaloitus.addActionListener(new PelinAlustaja());
		pelivalikko.add(pelinaloitus);

		// Tehdään pelin asetuksen valintamahdollisuus.
		JMenuItem pelinasetukset = new JMenuItem("Settings", KeyEvent.VK_S);
		pelinasetukset.addActionListener(new PelinAsetukset());
		pelivalikko.add(pelinasetukset);

		pelivalikko.addSeparator();

		// Tehdään pelin lopettava nappi.
		JMenuItem pelinlopetus = new JMenuItem("Quit", KeyEvent.VK_Q);
		pelinlopetus.addActionListener(new PelinLopettaja());
		pelivalikko.add(pelinlopetus);

		// Tehdään help-valikko.
		JMenu helpvalikko = new JMenu("Help");
		helpvalikko.setMnemonic(KeyEvent.VK_H);
		pelivalikko.getAccessibleContext().setAccessibleDescription(
				"Help and info");
		valikkopalkki.add(helpvalikko);

		// Tehdään help-nappi.
		JMenuItem help = new JMenuItem("Like you need help with this game.");
		helpvalikko.add(help);

		return valikkopalkki;
	}

	/**
	 * Asettaa pelin "vaikeustason" eli peliruudun koon ja miinojen määrän.
	 * 
	 * @param taulukko
	 *            Uudet asetukset pelille.
	 */
	private void asetavt(int[] taulukko) {
		if (taulukko.length == 3) {
			vt = taulukko;
		}
	}

	/**
	 * Sisäinen luokka pelin lopettamista varten.
	 * 
	 * 
	 */
	private class PelinLopettaja implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			/*
			 * Tehdään dialogi lopetuksesta, mikäli lopetetaan, suljetaan
			 * ohjelma.
			 */
			int n = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to quit?", "HALT!",
					JOptionPane.YES_NO_OPTION);
			if (n == 0) {
				System.exit(0);
			}
		}

	}

	/**
	 * Sisäinen luokka pelin uudelleen aloittamista varten.
	 * 
	 * 
	 */
	private class PelinAlustaja implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Poistetaan vanha peliruutu ja lisätään uusi.
			getContentPane().remove(paneeli);
			paneeli = new Pelipaneeli(new Peliruudukko(vt[0], vt[1], vt[2]),
					Miinapeli.this);
			getContentPane().add(paneeli, BorderLayout.CENTER);
			pack();
			repaint();
		}
	}

	private class PelinAsetukset implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Tallennetaan alkuperäiset asetukset.
			int[] orig = vt.clone();
			// Tehdään valikko, josta voi valita vaikeustason.
			JPanel paneeli = new JPanel(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			paneeli.add(new JLabel("Set the difficulty:"), c);
			c.gridy = 1;
			RadioKuuntelija rk = new RadioKuuntelija();
			JRadioButton helppo = new JRadioButton("Easy");
			helppo.addActionListener(rk);
			paneeli.add(helppo, c);
			c.gridx = 1;
			JRadioButton normaali = new JRadioButton("Normal");
			normaali.addActionListener(rk);
			paneeli.add(normaali, c);
			c.gridx = 2;
			JRadioButton vaikea = new JRadioButton("Hard");
			vaikea.addActionListener(rk);
			paneeli.add(vaikea, c);

			// Tarkistetaan minkä asetuksen käyttäjä haluaa.
			int n = JOptionPane.showOptionDialog(null, paneeli, "Settings",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, null, null);
			if (n == 0) {
				new PelinAlustaja().actionPerformed(null);
			} else {
				asetavt(orig);
			}
		}

		/**
		 * Sisäinen luokka asetuksien radionappien kuuntelemiseen.
		 * 
		 * 
		 * 
		 */
		private class RadioKuuntelija implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Tarkistetaan minkä asetuksen käyttäjä valitsi.
				String teksti = ((JRadioButton) arg0.getSource()).getText();
				if (teksti.equals("Easy")) {
					asetavt(Miinapeli.HELPPO);
				} else if (teksti.equals("Normal")) {
					asetavt(Miinapeli.NORMAALI);
				} else if (teksti.equals("Hard")) {
					asetavt(Miinapeli.VAIKEA);
				}
			}

		}
	}

	public static void main(String[] args) {
		// Aloitetaan uusi peli.
		Miinapeli testipeli = new Miinapeli();
		testipeli.setVisible(true);
	}
}
