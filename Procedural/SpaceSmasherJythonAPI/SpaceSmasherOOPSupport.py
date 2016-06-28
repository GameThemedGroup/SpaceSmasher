from Engine import GameWindow as _GameWindow
from SpaceSmasher import SpaceSmasher
from SpaceSmasherProcedureAPI import *

class CustomSpaceMaker(SpaceSmasherProcedureAPI):
    # Here we have methods/functions to be called (these are actually just references).
    doBuildGame = None
    doUpdateGame = None
    doInitializeBlockSet = None
    doUseADForPaddleMovement = None
    doUseMouseForPaddleMovement = None
    doSpawnBall = None
    doHandleBlockBallWallCollision = None
    doHandlePaddleBallCollisions = None
    
    def buildGame(self):
        if CustomSpaceMaker.doBuildGame is not None:
            CustomSpaceMaker.doBuildGame(self)
        else:
            super(CustomSpaceMaker, self).buildGame() 
            
    def updateGame(self):
        if CustomSpaceMaker.doUpdateGame is not None:
            CustomSpaceMaker.doUpdateGame(self)
        else:
            super(CustomSpaceMaker, self).updateGame()        
    
    def initializeBlockSet(self):
        if CustomSpaceMaker.doInitializeBlockSet is not None:
            CustomSpaceMaker.doInitializeBlockSet(self)
        else:
            super(CustomSpaceMaker, self).initializeBlockSet() 
            
    def useADForPaddleMovement(self, whichPaddle):
        if CustomSpaceMaker.doUseADForPaddleMovement is not None:
            CustomSpaceMaker.doUseADForPaddleMovement(self, whichPaddle)
        else:
            super(CustomSpaceMaker, self).useADForPaddleMovement(whichPaddle) 

    def useMouseForPaddleMovement(self, whichPaddle):
        if CustomSpaceMaker.doUseMouseForPaddleMovement is not None:
            CustomSpaceMaker.doUseMouseForPaddleMovement(self, whichPaddle)
        else:
            super(CustomSpaceMaker, self).useMouseForPaddleMovement(whichPaddle) 
            
    def spawnBall(self, whichBall, whichPaddle):
        if CustomSpaceMaker.doSpawnBall is not None:
            CustomSpaceMaker.doSpawnBall(self, whichBall, whichPaddle)
        else:
            super(CustomSpaceMaker, self).spawnBall(whichBall, whichPaddle) 
            
    def handleBlockBallWallCollision(self, whichBall):
        if CustomSpaceMaker.doHandleBlockBallWallCollision is not None:
            CustomSpaceMaker.doHandleBlockBallWallCollision(self, whichBall)
        else:
            super(CustomSpaceMaker, self).handleBlockBallWallCollision(whichBall) 
        
    def handlePaddleBallCollisions(self, whichBall, whichPaddle):
        if CustomSpaceMaker.doHandlePaddleBallCollisions is not None:
            CustomSpaceMaker.doHandlePaddleBallCollisions(self, whichBall, whichPaddle)
        else:
            super(CustomSpaceMaker, self).handlePaddleBallCollisions(whichBall, whichPaddle) 



def build_Game(f):
    CustomSpaceMaker.doBuildGame = f
    return f;

def update_Game(f):
    CustomSpaceMaker.doUpdateGame = f
    return f;

def initialize_BlockSet(f):
    CustomSpaceMaker.doInitializeBlockSet = f
    return f

def use_AD_For_Paddle_Movement(f):
    CustomSpaceMaker.doUseADForPaddleMovement = f
    return f

def use_Mouse_For_Paddle_Movement(f):
    CustomSpaceMaker.doUseMouseForPaddleMovement = f
    return f

def spawn_Ball(f):
    CustomSpaceMaker.doSpawnBall = f
    return f
    
def handle_Block_Ball_Wall_Collision(f):
    CustomSpaceMaker.doHandleBlockBallWallCollision = f
    return f
def handle_Paddle_Ball_Collisions(f):
    CustomSpaceMaker.doHandlePaddleBallCollisions = f
    return f


class _Main(_GameWindow):

    def __init__(self):
        super(_GameWindow, self).__init__()
        self.spaceMaker = CustomSpaceMaker()
        self.setRunner(self.spaceMaker)

def startProgram(showFlashScreen = False):
    """Start the program."""
    SpaceSmasher.SetShowFlashScreen(showFlashScreen)
    m = _Main()
    m.startProgram()
