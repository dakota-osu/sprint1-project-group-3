package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ShipTest {

    @Test
    public void ShipsHaveTheCorrectType() {
        Ship s1 = new Ship("BATTLESHIP");
        assertEquals(s1.getShipType(), Ship.ShipType.BATTLESHIP);

        Ship s2 = new Ship("DESTROYER");
        assertEquals(s2.getShipType(), Ship.ShipType.DESTROYER);

        Ship s3 = new Ship("MINESWEEPER");
        assertEquals(s3.getShipType(), Ship.ShipType.MINESWEEPER);
    }

    @Test
    public void ShipsHandleJunkType() {
        Ship s1 = new Ship("asdfasdf");

        assertEquals(s1.getShipType(), Ship.ShipType.INVALID);
    } 


}