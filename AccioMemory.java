
/* Name: Umme Tanjuma Haque
 * File: AccioMemory.java
 * Description: Basically, the program is a Harry Potter-themed matching game. There are two levels: Easy and Hard.
 * The Easy level gives the user 240 seconds to match where, the Hard level gives the user 90 seconds. The user has to 
 * click on the two most relevant pictures ( no picture is the same). Once, the score hits 8 before the time lapses,
 * the user will see the win screen otherwise, the lose screen. 
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class AccioMemory extends GraphicsProgram
{
	// fields for introduction state:
	private GImage background;// the background image for state 1
	private GRect instructions; // the instructions button; goes to state 2
	private GLabel instructionsLbl; // the word "Instructions"
	private GRect easyBtn; // starts game on easy; goes to state 3
	private GLabel easyLbl; // the word "Easy"
	private GRect hardBtn; // starts game on hard; goes to state 3
	private GLabel hardLbl; // the word "Hard"
	
	// fields for instructions state:
	private GImage instImage;// the background Image with instructions for state 2
	private GRect back; // returns user to introduction; goes to state 1
	private GLabel backLbl; // the word "Back"
	
	// fields for gameplay state:
	public static final int GRID_SIZE = 4;// sets the size of the two-dimensional
																				// array grid
	private GImage[][] grid;// creates a two dimensional array for storing images
	private ArrayList<GImage> pictures = new ArrayList<GImage>(16);// creates an
																																	// arraylist
																																	// for storing
																																	// images
	private ArrayList<Integer> numbers = new ArrayList<Integer>(16);// creates an
																																	// arraylst
																																	// for storing
																																	// numbers
																																	// 0-15, that
																																	// will act as
																																	// indices for
																																	// the
																																	// pictures
																																	// arraylist
	private ArrayList<GImage> blacksquares = new ArrayList<GImage>(16);// creates
																																			// an
																																			// arraylist
																																			// for
																																			// storing
																																			// 16
																																			// blacksquare
																																			// images
	private ArrayList<Integer> flippedImages = new ArrayList<Integer>(2);// creates
																																				// an
																																				// arraylist
																																				// for
																																				// storing
																																				// the
																																				// two
																																				// indices
																																				// of
																																				// blacksquares
																																				// that
																																				// are
																																				// clicked
																																				// consecutively
	private ArrayList<Integer> donotRepeat = new ArrayList<Integer>(2);// creates
																																			// an
																																			// arraylist
																																			// to
																																			// store
																																			// indices
																																			// of
																																			// blacksquares
																																			// whose
																																			// corresponding
																																			// images
																																			// were
																																			// matched
																																			// in
																																			// order
																																			// to
																																			// avoid
																																			// repeating
																																			// of
																																			// matches
	private GImage blacksquare;// the blacksquare image
	private int timer; // counts down every second
	private GLabel timerLbl; // shows the current time
	private GLabel scoreLbl; // shows the score of the user
	private boolean hard; // true if user selects hard; false otherwise
	private SwingTimer t; // the timer (so we can start and stop it throughout
												// file)
	private int score = 0; // sets the intial value of score to zero
	private GRect recSmall; // creates a rectangle to go under the timer and score
													// labels
	
	// fields for conclusion state:
	private boolean won; // true if the user won, false otherwise
	private GRect playAgain; // button to play again, goes to state 1
	private GLabel playAgainLbl; // says "Play again"
	private GImage youWonpic;// the background for the win screen
	private GImage youLostpic;// the background for the lose screen
	// the current state
	// 1 = introduction
	// 2 = instructions
	// 3 = playing
	// 4 = conclusion
	private int state; // declares an integer for the states
	
	
	@Override
	public void run()
	{
		
		setSize(1920, 1080);
		
		
		// state 1: sets the visuals for the first screen the user sees.
		background = new GImage(MediaTools.loadImage("harry.jpg"), 0, 0);
		background.setSize(getWidth(), getHeight());
		instructions = new GRect(300, 400, 300, 100);
		instructionsLbl = new GLabel("Instructions", 325, 450);
		instructionsLbl.setFont(new Font("SansSerif", Font.BOLD, 50));
		instructionsLbl.setColor(Color.WHITE);
		easyBtn = new GRect(650, 400, 300, 100);
		easyLbl = new GLabel("Easy", 750, 450);
		easyLbl.setFont(new Font("SansSerif", Font.BOLD, 50));
		easyLbl.setColor(Color.WHITE);
		hardBtn = new GRect(1000, 400, 300, 100);
		hardLbl = new GLabel("Hard", 1100, 450);
		hardLbl.setFont(new Font("SansSerif", Font.BOLD, 50));
		hardLbl.setColor(Color.WHITE);
		
		// state 2:sets the instructions page with a back button (rectangle),
		// background image and back GLabel
		
		instImage = new GImage(MediaTools.loadImage("instructions.jpg"), 0, 0);
		instImage.setSize(getWidth(), getHeight());
		back = new GRect(900, 750, 300, 100);
		back.setColor(Color.WHITE);
		backLbl = new GLabel("Back", 980, 800);
		backLbl.setFont(new Font("SansSerif", Font.BOLD, 40));
		backLbl.setColor(Color.WHITE);
		
		// state 3: sets the timer, score(keeper), rectangle that goes behind them,
		// pictures arraylist and numbers arraylist
		
		timerLbl = new GLabel("Time: ", 1, 30);
		timerLbl.setFont(new Font("SansSerif", Font.BOLD, 20));
		timerLbl.setColor(Color.white);
		t = new SwingTimer(1000, this);
		
		recSmall = new GRect(0, 0, 100, 80);
		recSmall.setFillColor(Color.black);
		recSmall.setFilled(true);
		
		scoreLbl = new GLabel("score: ", 1, 60);
		scoreLbl.setFont(new Font("SansSerif", Font.BOLD, 20));
		scoreLbl.setColor(Color.white);
		
		for (int i = 0; i <= 15; i++) // makes a loop that adds numbers from 0-15 to
																	// the arraylist
		{
			
			numbers.add(i);
			
		}
		
		// state 4: sets up the background for each win and lose situation, the
		// playAgain GLabels and rectangles
		
		playAgain = new GRect(1450, 500, 400, 200);
		playAgain.setColor(Color.WHITE);
		playAgainLbl = new GLabel("Play again", 1500, 600);
		playAgainLbl.setFont(new Font("SansSerif", Font.BOLD, 50));
		playAgainLbl.setColor(Color.WHITE);
		
		youWonpic = new GImage(MediaTools.loadImage("youwin.jpg"), 0, 0);
		youWonpic.setSize(getWidth(), getHeight());
		youLostpic = new GImage(MediaTools.loadImage("youlose.jpg"), 0, 0);
		youLostpic.setSize(getWidth(), getHeight());
		
		// start in state 1:
		goToState(1);
		
		// enable the mouse:
		addMouseListeners();
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (state == 1)
		{ // introduction state
			// this is done to make sure the numbers are shuffled and images are
			// placed accordingly when the user opens the game for the first time and
			// presses Play Again
			Collections.shuffle(numbers); // the numbers arraylist's elements
																		// (integers) are shuffled using the
																		// Shuffles method
			
			ResizeImages();// calls the ResizeImages method to resize the different
											// sizes of the images to one uniform required size
			
			grid = new GImage[GRID_SIZE][GRID_SIZE]; // the grid is created with the
																								// length and width of it
																								// specified
			int i = 0;
			for (int x = 0; x < GRID_SIZE; x++)
			{
				for (int y = 0; y < GRID_SIZE && i <= 15; y++) // a double for loop is
																												// being used to fill in
																												// the grid. Also, i is
																												// included here to act
																												// as the index for the
																												// shuffled numbers
																												// arraylist
				{
					int xCoord = x * getWidth() / GRID_SIZE; // to set the x-coordinate
																										// for the current grid to
																										// place, in scale to the
																										// width of the applet
					int yCoord = y * getHeight() / GRID_SIZE;// to set the y-coordinate
																										// for the current grid to
																										// place, in scale to the
																										// length of the applet
					grid[x][y] = pictures.get(numbers.get(i));// the element(integer) at
																										// the i index of the
																										// shuffled numbers
																										// arraylist acts the index
																										// for the pictures
																										// arraylist and sets the
																										// corresponding image to
																										// the current grid. This
																										// allows randomization.
					grid[x][y].setLocation(xCoord, yCoord); // sets the location of the
																									// current filled grid to its
																									// corresponding x and y
																									// coordinates
					GPoint location = grid[x][y].getLocation(); // stores the location of
																											// the current grid
					
					blacksquare =
							new GImage(MediaTools.loadImage("blacksquare.jpg"), 0, 0);
					blacksquare.setLocation(location); // here the location that was
																							// stored sets the location of the
																							// blacksquare on the current grid
																							// ( with the image in it already)
					blacksquare.setSize(getWidth() / 4, getHeight() / 4);// it sets the
																																// size of the
																																// blacksquare
																																// to the 1/4th
																																// of the length
																																// and width of
																																// the applet
					blacksquares.add(blacksquare); // adds the image blacksquare to the
																					// arraylist blacksquares.
					i++;
				}
				
			}
			
			if (instructions.contains(e.getX(), e.getY()))
			{
				goToState(2); // loads instructions screen
			}
			else if (easyBtn.contains(e.getX(), e.getY()))
			{
				hard = false;
				goToState(3); // starts gameplay
			}
			else if (hardBtn.contains(e.getX(), e.getY()))
			{
				hard = true;
				goToState(3); // starts gameplay
			}
			donotRepeat.clear();
			
		}
		else if (state == 2)
		{
			if (back.contains(e.getX(), e.getY()))
			{
				goToState(1); // returns to state 1
			}
		}
		else if (state == 3)
		{
			for (int locN = 0; locN < 16; locN++) // this loop checks each blacksquare
																						// to see which one was clicked
			
			{
				if ((blacksquares.get(locN).contains(e.getX(), e.getY())))
				{
					if (!donotRepeat.contains(locN)) // this becomes important after the
																						// first match is found to ensure
																						// that there are no scores for
																						// clicking on matched images again!
					{
						
						flippedImages.add(locN); // so, if the donotRepeat arraylist does
																			// not contain an index of blacksquare
																			// whose corresponding image was part of a
																			// match, only then will it be added to
																			// flippedImages arrayList
						
					}
					
				}
			}
			
			for (int d = 0; d < flippedImages.size(); d++) // removes blacksquares of
																											// the one/two indices
																											// stored in the
																											// flippedImages
			{
				remove(blacksquares.get(flippedImages.get(d)));
				
			}
			
			if (flippedImages.size() == 2) // sets the condition that the following if
																			// statements will only apply if
																			// flippedImages has 2 elements
			{
				// every condition from now uptill the last 'else if' basically looks
				// for
				// the x and y coordinate of the blacksquare whose indices are stored in
				// the flippedImages and compares them to the current x and y
				// coordinates of the two matching images
				// if the conditions are met then the score(counter) is increased by one
				// and the
				// indices of the matched images blacksquares is added to the
				// donotRepeat arraylist and the flippedImages is cleared because we no
				// longer need to worry about the images that have already
				// matched.Yahoo!
				if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(0).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(0).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(12).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(12).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				// the pictures indices are repeated because there are two orders in
				// which the blacksquares can be clicked.
				// so, this is to make sure the program catches the matched images
				// properly
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(12).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(12).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(0).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(0).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(10).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(10).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(11).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(11).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(11).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(11).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(10).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(10).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(1).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(1).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(3).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(3).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(3).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(3).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(1).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(1).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(2).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(2).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(15).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(15).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(15).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(15).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(2).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(2).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(4).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(4).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(5).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(5).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(5).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(5).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(4).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(4).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(6).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(6).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(7).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(7).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(7).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(7).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(6).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(6).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(8).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(8).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(9).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(9).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(9).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(9).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(8).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(8).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(13).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(13).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(14).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(14).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				else if (blacksquares.get(flippedImages.get(0)).getLocation()
						.getX() == pictures.get(14).getLocation().getX()
						&& blacksquares.get(flippedImages.get(0)).getLocation()
								.getY() == pictures.get(14).getLocation().getY()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getX() == pictures.get(13).getLocation().getX()
						&& blacksquares.get(flippedImages.get(1)).getLocation()
								.getY() == pictures.get(13).getLocation().getY())
				{
					score++;
					
					donotRepeat.add(flippedImages.get(0));
					donotRepeat.add(flippedImages.get(1));
					flippedImages.clear();
					
				}
				// if no match is found then, else happens where if only the user is
				// clicking on another blacksquare and not on an image ( removed
				// blacksquare), the blacksquare will be added back to the first removed
				// blacksquare according to flippedImages
				else
				{
					if (!blacksquares.get(flippedImages.get(0)).contains(e.getX(),
							e.getY()))
					{
						add(blacksquares.get(flippedImages.get(0)));
						// also, to make sure the timer and score(keeper) does not get under
						// the blacksquare, they are added again on top of the added
						// blacksquare at grid[0][0]
						if (blacksquares.get(flippedImages.get(0)).getLocation()
								.getX() == grid[0][0].getX()
								&& blacksquares.get(flippedImages.get(0)).getLocation()
										.getY() == grid[0][0].getY())
						{
							add(recSmall);
							add(timerLbl);
							add(scoreLbl);
						}
					}
					flippedImages.remove(0); // this removes the first flippedImages
																		// element so that a new index is stored
																		// when another blacksquare is clicked and
																		// the entire comparison process starts
																		// again
				}
			}
			
			scoreLbl.setLabel("Score: " + score);
			if (score == 8) // when user reaches winning score, won boolean becomes
											// true and the program moves to state 4
			{
				won = true;
				goToState(4);
			}
		}
		
		else if (state == 4) // here, if the user chooses to play again then the
													// user is taken to state 1, that is the opening
													// screen of the game
		{
			if (playAgain.contains(e.getX(), e.getY()))
			{
				goToState(1);
			}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// the only time the timer matters is in state 3
		if (state == 3)
		{
			timer = timer - 1; // updates the timer
			
			timerLbl.setLabel("Time: " + timer);
			
			if (timer <= 0)
			{
				// the user lost :(
				won = false;
				goToState(4);
			}
		}
	}
	
	public void goToState(int newState)
	{
		// this method handles all state transitions.
		
		// the first step in a state transition is to clean up anything
		// that was happening in the old state. The only state with special
		// instructions is state 3 -- the timer needs to be stopped:
		if (state == 3)
		{
			t.stop();
		}
		
		// all states need to clear the screen:
		removeAll(); // <-- this command clears everything off the screen
		
		// changes the official state:
		state = newState;
		
		// now, adds elements as appropriate:
		if (state == 1)
		{
			add(background);
			add(instructions);
			add(instructionsLbl);
			add(easyBtn);
			add(easyLbl);
			add(hardBtn);
			add(hardLbl);
			
		}
		else if (state == 2)
		{
			add(instImage);
			add(back);
			add(backLbl);
		}
		else if (state == 3)
		{
			
			for (int x = 0; x < 4; x++)
			{
				for (int y = 0; y < 4; y++)
				{
					
					add(grid[x][y]); // adds the grid(images) to the applet
					
				}
			}
			for (int locN = 0; locN < 16; locN++)
			{
				
				add(blacksquares.get(locN)); // adds the blacksquare images from the
																			// arraylist blacksquares to the applet
			}
			
			if (hard == true)
			{
				// starts timer at 90:
				timer = 90;
			}
			else
			{
				// otherwise, the user has 240 seconds:
				timer = 240;
			}
			
			// resets score:
			score = 0;
			
			timerLbl.setLabel("Time: " + timer);
			add(timerLbl);
			scoreLbl.setLabel("Score: " + score);
			
			add(recSmall);
			add(timerLbl);
			add(scoreLbl);
			
			// starts the timer and the game begins!
			t.start();
		}
		else if (state == 4)
		{
			
			if (won == true)
			{
				
				add(youWonpic);
				
			}
			else
			{
				
				add(youLostpic);
				
			}
			
			add(playAgain);
			add(playAgainLbl);
		}
	}
	
	private void ResizeImages() // creates the ResizeImages method to make sure
															// all images are scaled properly to fit in the
															// grids
	{
		
		for (int i = 1; i <= 16; i++) // each of the image are named as numbers
																	// hence, a loop is used to add them to the
																	// arraylist
		{
			GImage image = new GImage(MediaTools.loadImage(i + ".png"), 0, 0);
			pictures.add(image); // adds the current image to the arraylist
			
		}
		double h = getHeight();
		double w = getWidth();
		for (int n = 0; n < 16; n++)
		{
			double a = pictures.get(n).getHeight();// gets the default height of each
																							// image
			double b = pictures.get(n).getWidth();// gets the default width of each
																						// image
			pictures.get(n).scale((w / 4) / b, (h / 4) / a); // divides the current
																												// images's width and
																												// height by 4 and
																												// multiplies the
																												// corresponding default
																												// width and height to
																												// it
			
		}
		
	}
	
}
