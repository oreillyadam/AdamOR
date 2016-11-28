import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.*;

public class Read extends JFrame {

	static Scanner userInput = new Scanner(System.in);
	public static Terms[] word;
	public static int counter = 0;

	// The following is the code for a gui

	// JComboBox myWords;

	JComboBox myWords2;
	JList myWords;
	DefaultListModel defListModel = new DefaultListModel();
	JScrollPane scroller;

	JTextField inputWord;
	JTextField inputWord2;

	public Read() {
		this.setSize(550, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("My  Frame");

		JPanel thePanel = new JPanel();

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();

		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);

		this.setLocation(xPos, yPos);

		// myWords = new JComboBox();
		myWords = new JList(defListModel);
		myWords.setFixedCellHeight(10);
		myWords.setFixedCellWidth(100);
		myWords.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		scroller = new JScrollPane(myWords,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		inputWord = new JTextField();
		inputWord.setColumns(10);
		// inputWord.setSize(200, 200);
		// inputWord.setMinimumSize(new Dimension(200, 200));
		inputWord.setText("binary");

		myWords2 = new JComboBox();
		inputWord2 = new JTextField();
		inputWord2.setColumns(10);
		// inputWord2.setSize(200, 200);
		inputWord2.setText("bruteforce");

		// Binary Algo
		inputWord.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				// myWords.removeAllItems();
				defListModel.removeAllElements();
				// myWords.removeAll();
				if (inputWord.getText().length() >= 1) {

					System.out.println(inputWord.getText());
					int i = Arrays.binarySearch(word,
							new Terms(0, inputWord.getText()),
							new WordComparator());
					if (i < 0)
						i = -i - 1;// this is in case the word doesnt exist and
									// gives a refrence point
					System.out.println("found  words at" + i);// this prints out
																// to the consol
					if (i < 0)
						return;
					int j = inputWord.getText().length() - 1;
					System.out.println("text lenght at" + j);

					System.out.println(word[i].getWord().charAt(j));
					System.out.println(inputWord.getText().charAt(j));
					do {

						// myWords.addItem(word[i].getWord());

						defListModel.addElement((word[i].getWord()));

						System.out.println(word[i].getWord()
								+ word[i].getWord().length());
						if (j >= word[++i].getWord().length())
							break;
					} while (word[i].getWord().charAt(j) == inputWord.getText()
							.charAt(j));
					System.out.println(word[i].getWord());
				}
			}
		});

		// BruteForce:
		inputWord2.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent ke) {
				myWords2.removeAllItems();
				System.out.println(inputWord2.getText());

				ArrayList<Terms> filtered = new ArrayList<Terms>();

				for (int i = 0; i < word.length; i++) {
					if (word[i].getWord().startsWith(inputWord2.getText()))

					{
						filtered.add(word[i]);

					}
				}

				for (Terms t : filtered) {
					System.out.println(t.getWord());
					myWords2.addItem(t.getWord());
				}

			}
		});

		thePanel.add(inputWord);
		// thePanel.add(myWords);
		// thePanel.add(myWords);
		thePanel.add(scroller);

		thePanel.add(inputWord2);
		thePanel.add(myWords2);

		this.add(thePanel);
		this.setVisible(true);

	}

	public static class WordComparator implements Comparator<Terms> {
		public int compare(Terms o1, Terms o2) {
			return o1.getWord().compareTo(o2.getWord());
		}

	}

	public static void main(String[] args) {

		word = getTerms();

		getFileInfo();

		Read gui = new Read();
		Arrays.sort(word, new WordComparator());
		// System.out.println("what words do you want");

	}

	private static class Terms {

		public long popularityValue;
		public String word;

		public Terms(long popularityValue, String word) {

			this.popularityValue = popularityValue;
			this.word = word;
		}

		public String getWord() {
			return word;
		}

		public String toString() {
			return word;
		}

	}

	// Creates an array of term Objects

	private static Terms[] getTerms() {

		Terms[] word = new Terms[10000];

		return word;

	}

	public String listener() {
		return null;
	}

	private static void getFileInfo() {

		System.out.println("Info Written in File\n");

		// Open a new connection to the file

		File listOfWords = new File(
				"/Users/Adam/workspace/Algo3/src/wikiwords.txt");

		try {

			// FileReader reads character files
			// BufferedReader reads as many characters as possible

			BufferedReader getInfo = new BufferedReader(new FileReader(
					listOfWords));

			// Reads a whole line from the file and saves it in a String
			getInfo.readLine();
			String wordInfo = getInfo.readLine();

			// readLine returns null when the end of the file is reached

			while (wordInfo != null) {

				String[] indivWordData = wordInfo.trim().split("\t");

				long popularityValue = Long.parseLong(indivWordData[0]);

				System.out.print("Word " + indivWordData[1] + " is "
						+ popularityValue + "\n");
				Terms t = new Terms(popularityValue, indivWordData[1]);
				word[counter] = t;
				counter++;
				wordInfo = getInfo.readLine();

			}

		}

		catch (FileNotFoundException e) {

			System.out.println("Couldn't Find the File");
			System.exit(0);
		}

		catch (IOException e) {

			System.out.println("An I/O Error Occurred");
			System.exit(0);

		}

	}

}