/**
 * @author Dawid Adamczyk #adamsdd
 */

package pl.adamsdd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private ImageIcon crossIco;
	private ImageIcon ovalIco;
	private JPanel gamePanel;
	private JPanel rightMenuPanel;
	private JLabel[][] board = new JLabel[3][3];
	private final int KOLKO = 1;
	private final int KRZYZYK = 2;
	private final int EMPTY = 0;
	private final String FIELD = "field";	// nazwa klucza ktory przechowuje dane dotyczace jakie pole jest na etykiecie
	private int currentPlayer = KRZYZYK;	//  pierwsza gre zaczyna krzyzyk
	private JRadioButton kolkoRadio = new JRadioButton("kolko");
	private JRadioButton krzyzykRadio = new JRadioButton("krzyzyk");
	private int clickCount = 0;
	
	public Game()
	{
		gamePanel = new JPanel();
		rightMenuPanel = makeRightMenuPanel();
		gamePanel.setLayout(new GridLayout(3, 3));
		crossIco = new ImageIcon(".\\img\\cross.png");
		ovalIco = new ImageIcon(".\\img\\oval.png");
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				JLabel lab = new JLabel();
				lab.setHorizontalAlignment(SwingConstants.CENTER);
				lab.putClientProperty(FIELD, EMPTY);
				lab.setBorder(BorderFactory.createEtchedBorder());
				lab.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mousePressed(MouseEvent e) {
						if((int)lab.getClientProperty(FIELD) != EMPTY) {
							return;
						}
						
						if(currentPlayer == KOLKO) {
							clickCount++;
							lab.putClientProperty(FIELD, KOLKO);
							lab.setIcon(ovalIco);
//							lab.setText("KOLKO");
							checkWin(KOLKO);
							currentPlayer = KRZYZYK;
							krzyzykRadio.setSelected(true);
							
						}else if(currentPlayer == KRZYZYK) {
							clickCount++;
							lab.putClientProperty(FIELD, KRZYZYK);
							lab.setIcon(crossIco);
//							lab.setText("KRZYZYK");
							checkWin(KRZYZYK);
							currentPlayer = KOLKO;
							kolkoRadio.setSelected(true);
						}
					}
				});
				board[i][j] = lab;
				gamePanel.add(lab);
			}
		}
		
		add(gamePanel, BorderLayout.CENTER);
		add(rightMenuPanel, BorderLayout.EAST);
		setTitle("Kolko i Krzyzyk");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public JPanel makeRightMenuPanel()
	{
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		ButtonGroup buttGroup = new ButtonGroup();
		buttGroup.add(kolkoRadio);
		buttGroup.add(krzyzykRadio);
		
		kolkoRadio.setEnabled(false);
		krzyzykRadio.setEnabled(false);
		krzyzykRadio.setSelected(true);
		
		menuPanel.add(kolkoRadio);
		menuPanel.add(krzyzykRadio);
		
		return menuPanel;
	}
	
	/**
	 * 
	 * @param player = gracz dla ktorego sprawdzana jest wygrana
	 * @method checkWin = naiwna implementacja algorytmu sprawdzajacego wygrana gracza
	 */
	public void checkWin(int player)
	{
		JLabel testLab;
		int count = 0;
		String winner;
		
		if(player == KOLKO)
			winner = "Kolko";
		else
			winner = "Krzyzyk";
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				testLab = board[i][j];
				if((int)testLab.getClientProperty(FIELD) == player)
				{
					count++;
					continue;
				}
				else
					break;
			}
			
			
			if(count == 3)
			{
				JOptionPane.showMessageDialog(null, "Wygrywa gracz: " + winner);
				startNewGame();
			}
			
			count = 0;
		}
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				testLab = board[j][i];
				if((int)testLab.getClientProperty(FIELD) == player)
				{
					count++;
					continue;
				}
				else
					break;
			}
			
			if(count == 3)
			{
				JOptionPane.showMessageDialog(null, "Wygrywa gracz: " + winner);
				startNewGame();
			}
			
			count = 0;
		}
		
		for(int i = 0; i < 3; i++)
		{
			testLab = board[i][i];
			if((int)testLab.getClientProperty(FIELD) == player)
				count++;
			
			if(count == 3)
			{
				JOptionPane.showMessageDialog(null, "Wygrywa gracz: " + winner);
				startNewGame();
			}
		}
		count = 0;
		
		int j = 2;
		for(int i = 0; i < 3; i ++)
		{
			testLab = board[i][j];
			if((int)testLab.getClientProperty(FIELD) == player)
			{
				count++;
				j--;
			}
				
			if(count == 3)
			{
				JOptionPane.showMessageDialog(null, "Wygrywa gracz: " + winner);
				startNewGame();
			}
			
		}
		
		if(clickCount == 9)
		{
			JOptionPane.showMessageDialog(null, "Remis!");
			startNewGame();
		}
	}
	
	private void startNewGame()
	{
		int n = JOptionPane.showOptionDialog(this,
						"Chcesz zagrac ponownie?",
						"Nowa Gra",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						new Object[] { "Tak!", "Nie"},
						null);
		
		if(n == JOptionPane.YES_OPTION)
		{
			for(int i = 0; i < 3; i++)
				for(int j = 0; j < 3; j++)
				{
					JLabel lab = board[i][j];
					lab.putClientProperty(FIELD, EMPTY);
					lab.setIcon(null);
					clickCount = 0;
				}
		}
		else
			System.exit(1);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Game());
	}
}
