package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {


	private List<Ship> ships;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.ships = new ArrayList<Ship>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		Ship toAdd = new Ship(ship);
		toAdd.setOccupiedSquaresByOrientation(x, y, isVertical);	

		// check that each occupied square is valid
		for(Square s : toAdd.getOccupiedSquares()) {
			if(0 > s.getRow() || s.getRow() > 9)
				return false;
			if('A' > s.getColumn() || s.getColumn() > 'J')
				return false;
		}

		// check that there are no shipwise collisions
		// also check that the same board cannot have two of 
		// the same piece
		for(Ship incident : this.ships) {
			if(toAdd.collidesWith(incident))
				return false;
			if(toAdd.getShipType().equals(incident.getShipType()))
				return false;
		}

		this.ships.add(toAdd);

		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {

		Result attackResult = new Result();
		if(x < 1 || x >10 || y < 'A' || y > 'J'){
			attackResult.setResult(AtackStatus.INVALID);
		}


		//TODO Implement
		return attackResult;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public List<Result> getAttacks() {
		//TODO implement
		return null;
	}

	public void setAttacks(List<Result> attacks) {
		//TODO implement
	}
}

