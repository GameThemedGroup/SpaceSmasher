import SpaceSmasherProceduralAPI.*;

import static SpaceSmasherProceduralAPI.KeysEnum.*;

/**
 * Example demonstrating changing the level.
 * 
 * @author Taran Christensen
 * @version 6/27/16
 * 
 * Most code taken from NinDemo, written by: 
 * Vuochly Ky (Nin)
 */
public class LevelDemo extends SpaceSmasherProceduralAPI
{
	private int MAX_LEVEL = 2;
	
    public void buildGame(){
        initializeLifeSet(6);
        initializePaddleSet(2);
        initializeBallSet(4);
        initializeBlockSet();
        //setLevel(1); // this has a bug
    }
    
    public void updateGame(){
        if(areAllBlocksRemoved())
        {
        	// If the current level is the maximum level, game finished
        	if(getLevel() == MAX_LEVEL)
        	{
        		gameWin();
        	}
        	else
        	{
        	    // Otherwise..
        		nextLevel(); // Increment the level, set the level text
        		clearLevel(); // Clear the block set, remove active balls
        		switch(getLevel()) // Get the level that we've incremented to
        		{
        			case 1: // Level 1
        				initializeNextLevel(3, 2); // Create a level with 3 columns, 2 rows
        				break;
        			case 2: // Level 2
        				initializeNextLevel(7, 3); // 7 columns, 3 rows
        				break;
        			default:
        				initializeNextLevel(2, 1); // 2 columns, 1 row
        				break;
        		}
        	}
        }
        else{
            // Handle Paddle movement
            useADForPaddleMovement(0);      // Use AD
            useMouseForPaddleMovement(1);
            
            // Handle ball spawning
            spawnBall(0, 0);    // Spawn ball 0 with paddle 0
            spawnBall(1, 1);    // Spawn ball 1 with paddle 1
            spawnBall(2, 0);    // Spawn ball 2 with paddle 0
            spawnBall(3, 1);    // Spawn ball 3 with paddle 1
            
            // Handle ball and paddle collisions
            // Separate from handleCollisions() to make 
            // it easy for making all balls collide-able with all paddles
            handlePaddleBallCollisions(0, 0); // ball 0 collide with paddle 0
            handlePaddleBallCollisions(0, 1); // ball 0 collide with paddle 1
            handlePaddleBallCollisions(1, 0); // ball 1 collide with paddle 0
            handlePaddleBallCollisions(1, 1); // ball 1 collide with paddle 1
            handlePaddleBallCollisions(2, 0); // ball 0 collide with paddle 0
            handlePaddleBallCollisions(2, 1); // ball 0 collide with paddle 1
            handlePaddleBallCollisions(3, 0); // ball 1 collide with paddle 0
            handlePaddleBallCollisions(3, 1); // ball 1 collide with paddle 1
            
            // Handle Ball, block and wall collisions
            handleBlockBallWallCollision(0);
            handleBlockBallWallCollision(1);
            handleBlockBallWallCollision(2);
            handleBlockBallWallCollision(3);
        }
        
    }
    
    /**
     * Set all balls in the game to invisible, to remove them from play.
     * Clear all of the blocks in the block set.
     */
    protected void clearLevel()
    {
    	ballSetToInvisible(0);
    	ballSetToInvisible(1);
    	ballSetToInvisible(2);
    	ballSetToInvisible(3);
    	clearBlockSet();
    }
    
    /**
     * Create a very simple level consisting of only two normal blocks.
     */
    public void initializeBlockSet()
    {
        // STEP1: Set how many blocks per row
        setBlocksPerRow(2);
        
        /*
        // STEP2: Add blocks to the row
        // The total number of blocks you add must be >= to the 
        // number of blocks you set per row (Refer to the function comment)
        */
        addBlock("Normal", 2);
        
        // STEP3: Reveal the block power
        revealBlockPower();
    }

    /**
     * Creates a simple level made of normal blocks, with columns columns, and rows rows.
     */
    private void initializeNextLevel(int columns, int rows)
    {
    	System.out.println("~Level: " + getLevel());
    	// STEP1: Set how many blocks per row
        setBlocksPerRow(columns);
        
        // STEP2: Add blocks to the row
        // The total number of blocks you add must be >= to the 
        // number of blocks you set per row (Refer to the function comment)
        for(int column = 0; column < columns; column++)
        {
	        for(int row = 0; row < rows; row++){
	            addBlock("normal", 1);
	        }
        }
        
        // STEP3: Reveal the block power
        revealBlockPower();
    }
    
    // use specific keys for paddle movement
    /**
     * (Override with same implementation as in API)
     * Function for paddle movement control. 
     * Use the A and D -keys to move the given paddle
     * @param whichPaddle do you want to use these keys to control
     */
    public void useADForPaddleMovement(int whichPaddle){
        if(isKeyboardButtonDown(KeysEnum.A) || isKeyboardButtonDown(KeysEnum.a)){
            paddleMoveLeft(whichPaddle);
        }
        if(isKeyboardButtonDown(KeysEnum.D) || isKeyboardButtonDown(KeysEnum.d)){
            paddleMoveRight(whichPaddle);
        }
    }
    
    /**
     * (Override with same implementation as in API)
     * Function for paddle movement control
     * Use mouse to control the movement of the given paddle
     * @param whichPaddle do you want to use the mouse to control
     */
    public void useMouseForPaddleMovement(int whichPaddle){
        if(isMouseOnScreen()){
            paddleSetCenterX(getMouseXCoordinate(), whichPaddle);   
        }
    }
    
    /**
     * (Override with same implementation as in API)
     * To spawn a ball from a paddle by using the SPACE key or the 
     * mouse left click
     * @param whichBall do you want to spawn
     * @param whichPaddle do you want to spawn the ball from
     */
    public void spawnBall(int whichBall, int whichPaddle){
        if (isKeyboardButtonDown(KeysEnum.SPACE) || isMouseLeftClicked()) {       
           if(!ballIsVisible(whichBall)){
               ballSpawnNearPaddle(whichBall, whichPaddle);
           }
         }
    }
    
    /**
     * (Override with same implementation as in API)
     * Handle when ball-block and ball-wall collision. This function also
     * check the game is lost by checking if all the lives are used
     * @param handle collision for whichBall
     */
    public void handleBlockBallWallCollision(int whichBall){
        if(ballIsVisible(whichBall)){
            handleBlockBallCollision(whichBall);
            ballAndWallCollisionCheck(whichBall);
            lostGameCheck();
        }
    }
    
    /**
     * (Override with same implementation as in API)
     * Handle when ball hit the paddle.
     * @param handle collision for whichBall and whichPaddle
     */
    public void handlePaddleBallCollisions(int whichBall, int whichPaddle){
        if(ballIsVisible(whichBall) && ballCollidedWithPaddle(whichBall, whichPaddle)){
            ballReflectOffPaddle(whichBall, whichPaddle);
            ballPlayBounceSound(whichBall);
        }
    }
    
    
}
