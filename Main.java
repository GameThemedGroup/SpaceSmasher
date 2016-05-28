import Engine.GameWindow;
import SpaceSmasher.SpaceSmasher;


/*
 * Main.java
 * 
 * This is Space Smasher's main driver.
 * 
 * This file defines the starting point for our Space Smasher game, and picks
 * from the set of TODOs to execute.  You'll put no real code in this file, but 
 * rather comment and uncomment lines below as you work on each TODO.  
 */

@SuppressWarnings("serial")
public class Main extends GameWindow
{
  public Main()
  {
      /*
       * This is the only code that will be modified in this file; 
       * comment and uncomment TODOs as you work on them, leaving only one active at a time
       */
	  
      setRunner(new NinDemo());
	  //setRunner(new TODO0());
	  //setRunner(new TODO1());
	  //setRunner(new TODO2());
	  //setRunner(new TODO3());
	  //setRunner(new TODO4());
	  //setRunner(new TODO5());
	  //setRunner(new TODO6());
	  //setRunner(new TODO7());
	  //setRunner(new TODO8());
	  //setRunner(new TODO9());
  }

  public static void main(String[] args)
  {
	  SpaceSmasher.SetShowFlashScreen(false);   // switch off the initial showing of Flash Screen for quicker debugging  
	  (new Main()).startProgram();
  }
}
