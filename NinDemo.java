import SpaceSmasherProcedureAPI.*;
import static SpaceSmasherProcedureAPI.KeysEnum.*;

/**
 * Write a description of class NinTodo here.
 * 
 * @author Vuochly Ky (Nin)
 * @version 4/16/16
 */
public class NinDemo extends SpaceSmasherProcedureAPI
{
    public void buildGame(){
        initializeLifeSet(6);
        initializePaddleSet(2);
        initializeBallSet(4);
        initializeBlockSet(); // Calling overloaded function (below)
        //setLevel(1); // this is a bug
    }

    public void updateGame(){
        if(areAllBlocksRemoved()){
            gameWin();
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
     * Overload Rob's function to make it easy to understand and 
     * to show the use of for-loop. There're 3 steps in this function
     */
    public void initializeBlockSet(){
        // STEP1: Set how many blocks per row
        setBlocksPerRow(7);
        
        // STEP2: Add blocks to the row
        // The total number of blocks you add must be >= to the 
        // number of blocks you set per row
        for(int row = 0; row < 3; row++){
            addBlock("ice", 1);
            addBlock("Fire", 2);
            addBlock("Normal", 3);
            addBlock("fire", 1);
        }
        
        // STEP3: Reveal the block power
        revealBlockPower();
    }
    
    // use specific keys for paddle movement
    /**
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
