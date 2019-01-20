package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;



public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));

        Board board2 = new Board();
        board2.placeShip(new Ship("MINESWEEPER"), 0, 'D', true);
        assertFalse(board2.placeShip(new Ship("DESTROYER"), 1, 'B', false));

        Board board3 = new Board();
        board3.placeShip(new Ship("BATTLESHIP"), 0, 'A', true);
        assertFalse(board3.placeShip(new Ship("BATTLESHIP"), 0, 'B', true));

        Board board4 = new Board();
        assertTrue(board4.placeShip(new Ship("MINESWEEPER"), 9, 'A', false));
    }

    @Test
    public void testInvalidAttack(){
        Board board = new Board();
        board.placeShip(new Ship("MINESWEEPER"), 5, 'C', true);

        Result r = board.attack(11, 'C');   //INVALID

        assertEquals(r.getResult(), AtackStatus.INVALID);

        Result samePlace1 = board.attack(4, 'D');
        Result samePlace2 = board.attack(4,'D');
        assertEquals(samePlace2.getResult(), AtackStatus.INVALID);

    }
}
