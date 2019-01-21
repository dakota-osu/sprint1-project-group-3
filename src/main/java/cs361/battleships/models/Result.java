package cs361.battleships.models;

public class Result {

	private AtackStatus attackStatus;
	private Ship myShip;
	private Square location;

	public Result(){}

	public AtackStatus getResult() {
		//TODO implement
		return this.attackStatus;
	}

	public void setResult(AtackStatus result) {
		//TODO implement
		this.attackStatus = result;
	}

	public Ship getShip() {
		//TODO implement
		return this.myShip;
	}

	public void setShip(Ship ship) {
		this.myShip = ship;
	}

	public Square getLocation() {
		//TODO implement
		return this.location;
	}

	public void setLocation(Square square) {
		//TODO implement
		this.location = square;
	}
}
