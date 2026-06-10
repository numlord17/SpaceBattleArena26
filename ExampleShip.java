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
        this.aimPoint = midpoint;
        return new RegistrationData("CHUDtesting", new Color(240, 0, 255), 5);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment env)
    {
            //Point aimPoint = new Point(1111,1111);
            boolean shot = false;
            ship = env.getShipStatus();
            System.out.println("Health: " + (int) ship.getHealth());
            System.out.println("Speed: " + (int) ship.getSpeed());
            System.out.println(aimPoint);
            if (env.getRadar() != null)
            {
            System.out.println("radar is NOT null");
            for (ObjectStatus status : env.getRadar().getByType("Asteroid"))
            {
               System.out.println(aimPoint);
               if (status.getPosition().getDistanceTo(ship.getPosition()) < ship.getPosition().getDistanceTo(aimPoint))
               {
                  System.out.println("found a new aimpoint");
                  aimPoint = status.getPosition();
                  System.out.println(aimPoint);
               }
            }
            }
            Random random = new Random();
            int randomNum = random.nextInt(2);
            if (!isFacingPlayer(env) && randomNum == 1)
            {
                //return new RotateCommand(ship.getPosition().getAngleTo(midpoint) - ship.getOrientation());
                return new RotateCommand(ship.getPosition().getAngleTo(aimPoint) - ship.getOrientation());
            }
            else if (!isFacingPlayer(env) && randomNum == 0)
            {
                //return new RotateCommand(ship.getPosition().getAngleTo(midpoint) - ship.getOrientation());
                //return new RotateCommand(ship.getPosition().getAngleTo(aimPoint) - ship.getOrientation());
                //return new ThrustCommand('B',1,1);
                return new RadarCommand(5);
            }
            else if (isFacingPlayer(env) && randomNum == 1)
            {
               return new FireTorpedoCommand('F');
            }
            else if (isFacingPlayer(env) && randomNum == 0)
            {
               return new ThrustCommand('B',1,1);
            }
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
      if (Math.abs(ship.getPosition().getAngleTo(aimPoint) - ship.getOrientation()) <= 20)
      {
          return true;
      }
      else
      {
         return false;
      }
    }
    
}