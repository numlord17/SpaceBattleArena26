import java.awt.Color;
import java.util.Random;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class Ruin2Ship extends BasicSpaceship {
    private int worldWidth;
    private int worldHeight;
    private Point midpoint;
    private ObjectStatus ship;
    public static void main(String[] args)
    {
        TextClient.run("10.56.98.121", new Ruin2Ship()); //default is 127.0.0.1
    }

    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight)
    {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.midpoint = new Point(worldWidth / 2, worldHeight / 2);
        return new RegistrationData(":(", new Color(240, 0, 255), 5);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment env)
    {
        
            ship = env.getShipStatus();
            System.out.println("Health: " + (int) ship.getHealth());
            System.out.println("Speed: " + (int) ship.getSpeed());
            System.out.println("Energy: " + ship.getEnergy());
        
            if (!isFacingCenter(env))
            {
                return new RotateCommand(ship.getPosition().getAngleTo(midpoint) - ship.getOrientation());
            }
            else if (ship.getEnergy() > 3)
            {
                return new FireTorpedoCommand('F');
            }
            else
            {
               return new ThrustCommand('F',1.1,5);
            }
            
    }
    
    public boolean isFacingCenter(BasicEnvironment env)
    {
      if (ship.getPosition().getAngleTo(midpoint) - ship.getOrientation() == 0)
      {
          return true;
      }
      else
      {
         return false;
      }
    }
    
}