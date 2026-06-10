import java.awt.Color;
import java.util.Random;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class CheatingShip extends BasicSpaceship {
    private int worldWidth;
    private int worldHeight;
    private Point midpoint;
    private ObjectStatus ship;
    private Point aimPoint;
    public static void main(String[] args)
    {
        TextClient.run("10.56.98.121", new CheatingShip()); //default is 127.0.0.1
    }

    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight)
    {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.midpoint = new Point(worldWidth / 2, worldHeight / 2);
        this.aimPoint = midpoint;
        return new RegistrationData("15", new Color(1, 100, 255), 5);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment env)
    {
            ship = env.getShipStatus();
            System.out.println("Speed: " + ship.getSpeed());
            if (ship.getSpeed() < 15)
            {
               return new ThrustCommand('B',0.5,1);
            }
            else if (ship.getSpeed() > 18)
            {
               return new BrakeCommand(0.75);
            }
            else
            {
               return new IdleCommand(0.1);
            }
    }
}