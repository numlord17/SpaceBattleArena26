import java.awt.Color;
import java.util.Random;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class ExampleShip extends BasicSpaceship {
    private int worldWidth;
    private int worldHeight;
    private Point midpoint;
    private ObjectStatus ship;
    public static void main(String[] args)
    {
        TextClient.run("10.56.98.121", new ExampleShip()); //default is 127.0.0.1
    }

    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight)
    {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.midpoint = new Point(worldWidth / 2, worldHeight / 2);
        return new RegistrationData("testing", new Color(240, 0, 255), 5);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment env)
    {
        
            ship = env.getShipStatus();
            System.out.println("Health: " + (int) ship.getHealth());
            System.out.println("Speed: " + (int) ship.getSpeed());
        
            if (!isFacingCenter(env))
            {
                return new RotateCommand(ship.getPosition().getAngleTo(midpoint) - ship.getOrientation());
            }
            if (ship.getPosition().getDistanceTo(midpoint) >= 200)
            {
                return new ThrustCommand('B', 0.15, 1);
            }
            else if ((ship.getPosition().getDistanceTo(midpoint) < 200) && (isFacingCenter(env)) && (ship.getSpeed() > 10))
            {
                return new BrakeCommand(0.01);
            }
            else
            {
                return new IdleCommand(0.1);
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