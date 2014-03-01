import java.util.ArrayList;

/**
 * A generic 2D array scanner
 *
 * @author moorejm
 *
 * @param <T>
 */
public class AbstractScanner<T extends Comparable<? super T>> extends Thread implements IScanner {
	/**
	 * The direction the scanner scans from left to right, top to bottom
	 *
	 * @author moorejm
	 *
	 */
	public enum Dir {
		HORIZ("--"), VERT("|"), LDIAG("\\"), RDIAG("/");

		private String info;

		private Dir(String info) {
			this.info = info;
		}

		public String getInfo() {
			return this.info;
		}
	}

	private final T[][] arr2D;
	private Dir dir;
	protected int size;
	private final Position pos;
	private Position mover;
	protected final ArrayList<T> data;

	/**
	 * Default Scanner that scans 1 element at a time horizontally
	 *
	 * @param arr
	 */
	public AbstractScanner(T[][] arr) {
		this(arr, 1, Dir.HORIZ);
	}

	/**
	 * Scanner constructor
	 *
	 * @param arr
	 *            The 2D array to scan
	 * @param size
	 *            The maximum size of the data to hold during a scan
	 * @param direction
	 *            The direction of the scanner
	 */
	public AbstractScanner(T[][] arr, int size, Dir direction) {
		if (size < 1)
			throw new IllegalArgumentException(
					"You must scan at least one element");
		if (arr.length != arr[0].length)
			throw new IllegalArgumentException("The array must be a square");
		if (arr.length < size)
			throw new IllegalArgumentException(
					"The size of the scanner cannot be longer than the array");
		this.arr2D = arr;
		this.size = size;

		this.dir = direction;
		pos = new Position(dir);
		data = new ArrayList<T>(size);
	}

	@Override
	public void run() {
		while (pos.canMove()) { // canMove() calls reset()
			pos.move();
			mover = pos.clone(); // Start the mover at pos
			T e = null;

			if (pos.canScan()) {
				// Scan
				for (int i = 0; i < size; i++) {
					e = mover.getData();
					if (!mover.canMove()) {
						add(e);
						break; // End Whole Scan
					} else {
						add(e);
						mover.move();
					}
				}
				// End Size Scan
				empty();
			}
		}
	}

	private void add(T e) {
		if (data.size() == size || e == null)
			throw new IllegalArgumentException();
		data.add(e);
	}

	protected void empty() {
		data.clear();
	}

	public ArrayList<T> getData() {
		return data;
	}

	/**
	 * Get the current position and the direction of this scanner
	 *
	 * @return
	 */
	public String getInfo() {
		StringBuilder sb = new StringBuilder(pos.toString() + "\t  ");
		sb.append(this.dir.getInfo() + " \t ");
		return sb.toString();
	}

	/**
	 * A marker for the position of data being read from the array
	 *
	 * @author Jordan
	 *
	 */
	class Position {
		private int xPos, yPos;
		private int row, col;

		public Position(Dir direction) {
			dir = direction;
			init();
		}

		@Override
		public String toString() {
			return String.format("[%02d, %02d]   %s ", yPos + 1, xPos + 1,
					getData()); // [row, col]
		}

		public int getX() {
			return xPos;
		}

		public int getY() {
			return yPos;
		}

		private T getData() {
			return arr2D[yPos][xPos];
		}

		@Override
		public Position clone() {
			Position p = new Position(dir);
			p.xPos = xPos;
			p.yPos = yPos;
			p.row = row;
			p.col = col;
			return p;
		}

		/**
		 * Initializes this position at the start of the array
		 */
		private void init() {
			switch (dir) {
			case HORIZ:
				xPos = -1;
				yPos = 0;
				break;
			case VERT:
				xPos = 0;
				yPos = -1;
				break;
			case LDIAG: // bottom left downright -> up
				xPos = col = -1;
				yPos = row = arr2D.length - size - 1;
				break;
			case RDIAG: // top left upright -> down
				xPos = col = -1;
				yPos = row = size;
				break;
			default:
				break;
			}
		}

		/**
		 * Moves this position one space in its direction
		 */
		public void move() {
			switch (dir) {
			case HORIZ:
				xPos++;
				break;
			case VERT:
				yPos++;
				break;
			case LDIAG:
				xPos++;
				yPos++;
				break;
			case RDIAG:
				xPos++;
				yPos--;
				break;
			default:
				break;
			}
		}

		/**
		 * Returns true if the position can move one space ahead
		 *
		 * @return
		 */
		public boolean canMove() {
			int end = arr2D.length - 1;
			boolean reset = false;

			if (dir == Dir.HORIZ || dir == Dir.VERT) {
				if (xPos == yPos && xPos == end)
					return false;
				reset = ((dir == Dir.HORIZ && xPos >= end) || (dir == Dir.VERT && yPos >= end));
			} else if (dir == Dir.LDIAG) {
				if (xPos == end && yPos == size - 1)
					return false;
				reset = (xPos == end || yPos == end);
			} else if (dir == Dir.RDIAG) {
				if (xPos == end && yPos == end - size + 1)
					return false;
				reset = (xPos == end || yPos == 0);
			}

			if (reset) {
				wrapAround();
			}
			return true;
		}

		/**
		 * Returns true if the scanner can move size spaces ahead
		 *
		 * @return
		 */
		public boolean canScan() {
			int end = arr2D.length;
			boolean willGoOut;

			if (dir == Dir.HORIZ || dir == Dir.VERT) {
				willGoOut = ((dir == Dir.HORIZ && pos.getX() + size > end) || (dir == Dir.VERT && pos
						.getY() + size > end));
				if (willGoOut || mover.getX() == mover.getY()
						&& mover.getX() >= end)
					return false;
			} else if (dir == Dir.LDIAG) {
				willGoOut = (pos.getX() + size > end || pos.getY() + size > end);
				if (willGoOut || mover.getX() == end
						&& mover.getY() == size - 1)
					return false;
			} else if (dir == Dir.RDIAG) {
				willGoOut = (pos.getX() + size > end || pos.getY() - size < -1);
				if (willGoOut || mover.getX() == end
						&& mover.getY() == end - size + 1)
					return false;
			}

			return true;
		}

		/**
		 * Causes the position to wrap-around the array
		 */
		private void wrapAround() {
			int end = arr2D.length - 1;
			switch (dir) {
			case HORIZ:
				xPos = -1;
				yPos++;
				break;
			case VERT:
				yPos = -1;
				xPos++;
				break;
			case LDIAG:
				if (xPos == end) {
					xPos = ++col;
					yPos = -1;
				} else if (yPos == end) {
					xPos = -1;
					yPos = row == 0 ? -1 : --row;
				}
				break;
			case RDIAG:
				if (xPos == end) {
					xPos = ++col;
					yPos = end + 1;
				} else if (yPos == 0) {
					xPos = -1;
					yPos = row == 0 ? -1 : ++row;
				}
				break;
			default:
				break;
			}
		}
	}

}