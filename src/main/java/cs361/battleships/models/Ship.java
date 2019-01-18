package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ship {

	public static enum ShipType {
		MINESWEEPER(2),
		BATTLESHIP(3),
		DESTOYER(4),
		INVALID(0);

		private final int value;

		private ShipType(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	@JsonProperty 
	private List<Square> occupiedSquares;

	private ShipType shipType;

	public Ship() {
		this("INVALID");
	}
	
	public Ship(String kind) {
		this.occupiedSquares = new ArrayList<Square>();
		this.shipType = ShipType.valueOf(kind);
	}

	public ShipType getShipType() {
		return this.shipType;
	}

	public List<Square> getOccupiedSquares() {
		return null;
	}
}
