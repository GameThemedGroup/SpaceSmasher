from SpaceSmasherOOPSupport import *


@build_Game
def buildGame(Game):
    Game.initializeLifeSet(6)
    Game.initializePaddleSet(2)
    Game.initializeBallSet(4)
    Game.initializeBlockSet() 
    #setLevel(1) # this is a bug

@update_Game
def updateGame(Game):
    if(Game.areAllBlocksRemoved()):
        Game.gameWin()
    else:
        # Handle Paddle movement
        Game.useADForPaddleMovement(0)      # Use AD
        Game.useMouseForPaddleMovement(1)
        
        # Handle ball spawning
        Game.spawnBall(0, 0)    # Spawn ball 0 with paddle 0
        Game.spawnBall(1, 1)    # Spawn ball 1 with paddle 1
        Game.spawnBall(2, 0)    # Spawn ball 2 with paddle 0
        Game.spawnBall(3, 1)    # Spawn ball 3 with paddle 1
        
        # Handle ball and paddle collisions
        # Separate from handleCollisions() to make 
        # it easy for making all balls collide-able with all paddles
        Game.handlePaddleBallCollisions(0, 0) # ball 0 collide with paddle 0
        Game.handlePaddleBallCollisions(0, 1) # ball 0 collide with paddle 1
        Game.handlePaddleBallCollisions(1, 0) # ball 1 collide with paddle 0
        Game.handlePaddleBallCollisions(1, 1) # ball 1 collide with paddle 1
        Game.handlePaddleBallCollisions(2, 0) # ball 0 collide with paddle 0
        Game.handlePaddleBallCollisions(2, 1) # ball 0 collide with paddle 1
        Game.handlePaddleBallCollisions(3, 0) # ball 1 collide with paddle 0
        Game.handlePaddleBallCollisions(3, 1) # ball 1 collide with paddle 1
        
        # Handle Ball, block and wall collisions
        Game.handleBlockBallWallCollision(0)
        Game.handleBlockBallWallCollision(1)
        Game.handleBlockBallWallCollision(2)
        Game.handleBlockBallWallCollision(3)
    
    


"""
  Overload Rob's function to make it easy to understand and 
  to show the use of for-loop. There're 3 steps in this function
"""
@initialize_BlockSet
def initializeBlockSet(Game):
    # STEP1: Set how many blocks per row
    Game.setBlocksPerRow(7)
    
    # STEP2: Add blocks to the row
    # The total number of blocks you add must be >= to the 
    # number of blocks you set per row
    for row in range(0,3):
        Game.addBlock("ice", 1)
        Game.addBlock("Fire", 2)
        Game.addBlock("Normal", 3)
        Game.addBlock("fire", 1)
    
    
    # STEP3: Reveal the block power
    Game.revealBlockPower()

"""
# use specific keys for paddle movement

  Function for paddle movement control. 
  Use the A and D -keys to move the given paddle
  @param whichPaddle do you want to use these keys to control
"""
@use_AD_For_Paddle_Movement
def useADForPaddleMovement(Game, whichPaddle):
    if Game.isKeyboardButtonDown(KeysEnum.A) or Game.isKeyboardButtonDown(KeysEnum.a):
        Game.paddleMoveLeft(whichPaddle)
    if Game.isKeyboardButtonDown(KeysEnum.D) or Game.isKeyboardButtonDown(KeysEnum.d):
        Game.paddleMoveRight(whichPaddle)
    


"""
  Function for paddle movement control
  Use mouse to control the movement of the given paddle
  @param whichPaddle do you want to use the mouse to control
"""
@use_Mouse_For_Paddle_Movement
def useMouseForPaddleMovement(Game, whichPaddle):
    if(Game.isMouseOnScreen()):
        Game.paddleSetCenterX(Game.getMouseXCoordinate(), whichPaddle)   


"""
  To spawn a ball from a paddle by using the SPACE key or the 
  mouse left click
  @param whichBall do you want to spawn
  @param whichPaddle do you want to spawn the ball from
"""
@spawn_Ball
def spawnBall(Game, whichBall, whichPaddle):
    if Game.isKeyboardButtonDown(KeysEnum.SPACE) or Game.isMouseLeftClicked():        
        if not Game.ballIsVisible(whichBall):
            Game.ballSpawnNearPaddle(whichBall, whichPaddle)
 
     


"""
Handle when ball-block and ball-wall collision. This function also
check the game is lost by checking if all the lives are used
@param handle collision for whichBall
"""
@handle_Block_Ball_Wall_Collision
def handleBlockBallWallCollision(Game, whichBall):
    if(Game.ballIsVisible(whichBall)):
        Game.handleBlockBallCollision(whichBall)
        Game.ballAndWallCollisionCheck(whichBall)
        Game.lostGameCheck()



"""
Handle when ball hit the paddle.
@param handle collision for whichBall and whichPaddle
"""
@handle_Paddle_Ball_Collisions
def handlePaddleBallCollisions(Game, whichBall, whichPaddle):
    if Game.ballIsVisible(whichBall) and Game.ballCollidedWithPaddle(whichBall, whichPaddle):
        Game.ballReflectOffPaddle(whichBall, whichPaddle)
        Game.ballPlayBounceSound(whichBall)






startProgram()