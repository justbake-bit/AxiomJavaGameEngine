package com.axiom.gameengine.Renderer;

public class Layout {
	
	public static final int
    BYTE           = 0x1400,
    UNSIGNED_BYTE  = 0x1401,
    SHORT          = 0x1402,
    UNSIGNED_SHORT = 0x1403,
    INT            = 0x1404,
    UNSIGNED_INT   = 0x1405,
    FLOAT          = 0x1406,
    TWO_BYTES      = 0x1407,
    THREE_BYTES    = 0x1408,
    FOUR_BYTES     = 0x1409,
    DOUBLE         = 0x140A;
	
	private final int type, amount;
	
	public Layout(int type, int amount) {
		this.type = type;
		this.amount = amount;
	}
	
	public int getSizeInBytes() {
		return typeSizeInBytes() * amount;
	}
	
	public int getSize() {
		return amount;
	}
	
	public int typeSizeInBytes() {
		switch (type) {
		case BYTE: {
			return 1;
		}
		case UNSIGNED_BYTE: {
			return 1;
		}
		case SHORT: {
			return 2;
		}
		case UNSIGNED_SHORT: {
			return 2;
		}
		case INT: {
			return 4;
		}
		case UNSIGNED_INT: {
			return 4;
		}
		case FLOAT: {
			return 4;
		}
		case TWO_BYTES: {
			return 2;
		}
		case THREE_BYTES: {
			return 3;
		}
		case FOUR_BYTES: {
			return 4;
		}
		case DOUBLE: {
			return 8;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}

	public int type() {
		return type;
	}
	
	public int amount() {
		return amount;
	}
}
