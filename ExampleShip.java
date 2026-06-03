import java.awt.Color;
import java.util.Random;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class ExampleShip extends BasicSpaceship {
    private int worldWidth;
    private int worldHeight;
    private Point midpoint;
    private ObjectStatus ship;
    private Point aimPoint;
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
        this.aimPoint = new Point(1111,1111);
        return new RegistrationData("testing", new Color(240, 0, 255), 5);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment env)
    {
            Point aimPoint = new Point(1111,1111);
            ship = env.getShipStatus();
            System.out.println("Health: " + (int) ship.getHealth());
            System.out.println("Speed: " + (int) ship.getSpeed());
            if (env.getRadar() != null)
            {
            
            for (ObjectStatus status : env.getRadar().getByType("Ship"))
            {
               if (status.getPosition().getDistanceTo(ship.getPosition()) < ship.getPosition().getDistanceTo(aimPoint))
               {
                  aimPoint = status.getPosition();
               }
            }
            }
            if (!isFacingPlayer(env))
            {
                //return new RotateCommand(ship.getPosition().getAngleTo(midpoint) - ship.getOrientation());
                return new RotateCommand(ship.getPosition().getAngleTo(aimPoint) - ship.getOrientation());
            }
            if (isFacingPlayer(env))
            {
               return new FireTorpedoCommand('F');
            }
            /*
            if (ship.getPosition().getDistanceTo(midpoint) >= 200)
            {
                Random random = new Random();
                int randomNum = random.nextInt(3);
                if (randomNum == 0)
                {
                  return new ThrustCommand('B', 1, 1);
                }
                if (randomNum == 1)
                {
                  return new RadarCommand(5);
                }
                else
                {
                  return new FireTorpedoCommand('F');
                }
            }
            */
            else if ((ship.getPosition().getDistanceTo(midpoint) < 200) && (ship.getSpeed() > 10))
            {
                return new BrakeCommand(0.01);
            }
            else
            {
                return new IdleCommand(0.1);
            }
            
    }
    
    public boolean isFacingPlayer(BasicEnvironment env)
    {
      if (ship.getPosition().getAngleTo(aimPoint) - ship.getOrientation() == 0)
      {
          return true;
      }
      else
      {
         return false;
      }
    }
    
}