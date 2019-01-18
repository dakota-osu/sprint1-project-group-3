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

    @Test
    public void ShipsOccupyTheCorrectSquares() {
        Ship s1 = new Ship("BATTLESHIP");
        s1.setOccupiedSquaresByOrientation(0, 'A', false);
        assertTrue(s1.getOccupiedSquares().size() == 4);
        assertTrue(s1.getOccupiedSquares().get(0).getColumn() == 'A');
        assertTrue(s1.getOccupiedSquares().get(1).getColumn() == 'B');
        assertTrue(s1.getOccupiedSquares().get(2).getColumn() == 'C');
        assertTrue(s1.getOccupiedSquares().get(3).getColumn() == 'D');


        Ship s2 = new Ship("DESTROYER");
        s2.setOccupiedSquaresByOrientation(3, 'D', true);
        assertTrue(s2.getOccupiedSquares().size() == 3);
        assertTrue(s2.getOccupiedSquares().get(0).getRow() == 3);
        assertTrue(s2.getOccupiedSquares().get(1).getRow() == 4);
        assertTrue(s2.getOccupiedSquares().get(2).getRow() == 5);

        Ship s3 = new Ship("MINESWEEPER");
        s3.setOccupiedSquaresByOrientation(2, 'C', false);
        assertTrue(s3.getOccupiedSquares().size() == 2);
        assertTrue(s3.getOccupiedSquares().get(0).getColumn() == 'C');
        assertTrue(s3.getOccupiedSquares().get(1).getColumn() == 'D');


    }
}