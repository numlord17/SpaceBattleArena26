import java.awt.Color;
import java.util.Random;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class ExampleShip extends BasicSpaceship {
    private int worldWidth;
    private int worldHeight;
    private Point midpoint;
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
        ObjectStatus ship = env.getShipStatus();
        
        if (ship.getPosition().getAngleTo(midpoint) - ship.getOrientation() != 0)
        {
            return new RotateCommand(ship.getPosition().getAngleTo(midpoint) - ship.getOrientation());
        }
        else if (ship.getPosition().getDistanceTo(midpoint) >= 10)
        {
            return new ThrustCommand('B', 1, 1);
        }
        else if ((ship.getPosition().getDistanceTo(midpoint) < 10) && (ship.getSpeed() != 0))
        {
            return new AllStopCommand();
        }
        else
        {
            return new IdleCommand(0.1);
        }
    }
}