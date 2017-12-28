import java.awt.Point;

public class Ducks {
	//Position
	public int xCoordinate;
	public int yCoordinate;
	public int xdirection;
	public int ydirection;
	private int xDuckDeathAtCoordinate;
	private int yDuckDeathAtCoordinate;
	int degree;
	private final int FROM_LEFT_WALL = 0;
	private final int FROM_BOTTOM_WALL = 1;
	private final int FROM_RIGHT_WALL = 2;
	private final int SPLASH_BLOOD_TIME = 1000;
	private final int ONE_MOVE_OF_DUCK = 17;

	//Animation
	String[] duckRightAnimation = new String[3];
	String[] duckLeftAnimation = new String[3];
	String[] duckDeadAnimation = new String[3];
	String[] splashBloodAnimation = new String[6];
	String splashBloodPicture;
	int duckAnimationStatus = 0;
	int duckDeadAnimationStatus = 1;
	public boolean isAlive;

	//Color
	private String[] colorAray = { "blue", "green", "red" };
	private String color;
	
	//Time
	private long duckDieAtTime;
	static long timeStayOnAir = 500;
	long time;
	//===============================================================================
	public Ducks() {
		color = colorAray[StdRandom.uniform(0, 3)];
		setDuckRightAnimation();
		setDuckLeftAnimation();
		setDuckDeadAnimation();
		setSplashBloodPicture();
	}

	// < Process Things > ****************
	private void checkHitWall() {
		// if the duck hit the wall 4 times then it can fly away
		if (this.yCoordinate + 20 > GameFrame.getHeight()) {
			// if (!checkHit4Times(this)) {
			this.ydirection *= -1;
			// this.hitWallTimes++;
		} else {
			// this.isGone = true;
		}

		// the duck is turn back if hit the wall
		if (this.xCoordinate + 20 > GameFrame.getWidth() || this.xCoordinate - 20 < 0) {
			this.xdirection *= -1;
			// this.hitWallTimes++;
		}
		if (this.yCoordinate - 20 < 0) {
			if (this.isAlive) {
				this.ydirection *= -1;
				// this.hitWallTimes++;
			} else {
				this.ydirection = 0;
				this.xdirection = 0;
			}
		}
		updateDuckDegree();
	}
	// </ Process Things >
	
	// <Update Things> **********
	private void updateDuckDegree() {
		if (this.xdirection > 0) {
			if (this.ydirection > 0)
				this.degree = 10;
			else
				this.degree = 315;
		} else {
			if (this.ydirection > 0)
				this.degree = 340;
			else
				this.degree = 45;
		}
	}
	void updateDuckPosition() {
		this.xCoordinate += this.xdirection;
		this.yCoordinate += this.ydirection;
		updateDuckAnimationStatus();
		checkHitWall();
	}
	private void updateDuckAnimationStatus() {
		if (this.isAlive) {
			this.duckAnimationStatus++;
			this.duckAnimationStatus %= 3;
		} else {
			this.duckDeadAnimationStatus %= 2;
			this.duckDeadAnimationStatus++;
		}
	}
	// </ Update Thing>
	
	//<Draw Things> *********
	public void drawSplashBlood() {
		if (!this.isAlive) {
			if (System.currentTimeMillis() - duckDieAtTime < SPLASH_BLOOD_TIME) {
				StdDraw.picture(xDuckDeathAtCoordinate, yDuckDeathAtCoordinate, splashBloodPicture);
			}
		}
	}

	//</Draw Things>
	// < Set Things? ***********
	private void setRamdomSplashBlood() {
		splashBloodPicture = splashBloodAnimation[StdRandom.uniform(6)];
	}
	private void setRamdomDirection() {
		if (StdRandom.uniform(0, 2) == 0) {
			this.xdirection = -ONE_MOVE_OF_DUCK;
		} else
			this.xdirection = ONE_MOVE_OF_DUCK;
		if (StdRandom.uniform(0, 2) == 0) {
			this.ydirection = -ONE_MOVE_OF_DUCK;
		} else
			this.ydirection = ONE_MOVE_OF_DUCK;
	}
	void setDuckDeadStayOnAir() {
		time = System.currentTimeMillis();
		this.xdirection = 0;
		this.ydirection = 0;
	}
	void setDuckFallDown() {
		this.xdirection = 0;
		this.ydirection = -50;
	}
	void setDuckDead() {
		setDuckDeadStayOnAir();
		this.isAlive = false;
		this.duckDieAtTime = System.currentTimeMillis();
		this.xDuckDeathAtCoordinate = (int) StdDraw.mouseX();
		this.yDuckDeathAtCoordinate = (int) StdDraw.mouseY();
	}
	// </Set Thing>

	// < Get Things> ***********
	String getDuck_R_or_Left_Animation() {
		if (this.isAlive) {
			if (xdirection > 0)
				return duckRightAnimation[this.duckAnimationStatus];
			else
				return duckLeftAnimation[this.duckAnimationStatus];
		} else {
			time = System.currentTimeMillis();

			return duckDeadAnimation[0];
		}
	}
	private Point getCoordinateOfDuck() {

		int y = 20;
		int x = StdRandom.uniform(20, GameFrame.getWidth() - 20);
		if (StdRandom.uniform(3) == FROM_LEFT_WALL) {
			x = 20;
			y = StdRandom.uniform(20, GameFrame.HEIGHT - 20);
		}
		if (StdRandom.uniform(3) == FROM_BOTTOM_WALL) {
			y = 20;
			x = StdRandom.uniform(20, GameFrame.getWidth() - 20);
		}
		if (StdRandom.uniform(3) == FROM_RIGHT_WALL) {
			x = GameFrame.WIDTH - 20;
			y = StdRandom.uniform(20, GameFrame.HEIGHT - 20);
		}

		return new Point(x, y);

	}
	// </ Get Things >

	// < Init Things > *********************
	void addDuck() {
		this.yCoordinate = getCoordinateOfDuck().y;
		this.xCoordinate = getCoordinateOfDuck().x;
		color = colorAray[StdRandom.uniform(0, 3)];
		setRamdomDirection();
		setRamdomSplashBlood();
		updateDuckDegree();
	}
	private void setDuckRightAnimation() {
		this.duckRightAnimation[0] = this.color + "DuckR0.png";
		this.duckRightAnimation[1] = this.color + "DuckR1.png";
		this.duckRightAnimation[2] = this.color + "DuckR2.png";
	}
	private void setDuckLeftAnimation() {
		this.duckLeftAnimation[0] = this.color + "DuckL0.png";
		this.duckLeftAnimation[1] = this.color + "DuckL1.png";
		this.duckLeftAnimation[2] = this.color + "DuckL2.png";
	}
	private void setDuckDeadAnimation() {
		duckDeadAnimation[0] = this.color + "DuckDead0.png";
		duckDeadAnimation[1] = this.color + "DuckDead1.png";
		duckDeadAnimation[2] = this.color + "DuckDead2.png";
	}
	private void setSplashBloodPicture() {
		splashBloodAnimation[0] = "bloodSplash0.png";
		splashBloodAnimation[1] = "bloodSplash1.png";
		splashBloodAnimation[2] = "bloodSplash2.png";
		splashBloodAnimation[3] = "bloodSplash3.png";
		splashBloodAnimation[4] = "bloodSplash4.png";
		splashBloodAnimation[5] = "bloodSplash5.png";
	}
	// </ Init Things>

}
