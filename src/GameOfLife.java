import java.util.Random;

class GameOfLife {

  boolean area[][];
  int size;
  int step = 0;
  int livepercentageGOL;

  // public static void main(String[] args) {
  //
  // // GameOfLife y = new GameOfLife();
  // GameOfLife x = new GameOfLife(20);
  // //x.evolve()
  // x.evolve(10);
  // }


  GameOfLife(int size, int livepercentage) {
    this.livepercentageGOL = livepercentage;
    this.size = size;
    this.area = new boolean[size][size];
    Random random = new Random();

    for (int x = 0; x < size; x++)
      for (int y = 0; y < size; y++) {

        this.area[x][y] = false;
        if (random.nextInt(100) < livepercentageGOL) {
          this.area[x][y] = true;
        }
      }
  }

  GameOfLife() {
    this.size = 6;
    this.area = new boolean[size][size]; // this empty is going to be filled up

    for (int x = 0; x < size; x++) // fill with false
      for (int y = 0; y < size; y++) {
        this.area[x][y] = false;
      }

    this.area[1][1] = true; // setting the certain squares with true, based on the question
    this.area[1][3] = true;
    this.area[2][2] = true;
    this.area[2][3] = true;
    this.area[3][2] = true;
  }

  // returns if a cell is alive or dead
  boolean alive(int x, int y) {
    return this.area[x][y];
  }

  // counts how many cells are alive overally
  private int lifecount() {
    int lifecount = 0;

    for (int x = 0; x < size; x++)
      for (int y = 0; y < size; y++) {
        if (this.area[x][y] == true) {
          lifecount++;
        }
      }
    return lifecount;
  }


  // counts how many neighbours it has
  private int neighbours(int x, int y) { // counter returns int
    int counter = 0;

    for (int i = x - 1; i <= 1 + x; i++)
      for (int j = y - 1; j <= 1 + y; j++) { // i & j are -1, 0, 1

        int truex = (i + this.size) % this.size; // to prevent over/underflow
        int truey = (j + this.size) % this.size;

        if (this.area[truex][truey] == true) {
          counter++;
        }
      }
    if (this.alive(x, y)) {
      counter--; // counts own cell too, so if it is alive we need to substract one
    }
    return counter;
  }

  // modifies and prints the table to the next generation
  public void nextGen() {
    boolean nextGen[][] = new boolean[this.size][this.size];

    for (int x = 0; x < size; x++)
      for (int y = 0; y < size; y++) {

        if (this.neighbours(x, y) == 3) { // 3 neighbours always alive
          nextGen[x][y] = true;
        }

        else if (this.neighbours(x, y) == 2 && this.alive(x, y)) {
          nextGen[x][y] = true; // 2 neighbours and being alive -> surviving
        }

        else {
          nextGen[x][y] = false;
        }
      }
    this.area = nextGen;
    this.step++;
    System.out.println(this);
    System.out.println("Evolution steps: " + this.step);
  }

  // calculates the next gen for # times
  public void evolve(int times) {
    for (int i = 0; i < times; i++) {
      this.nextGen();

      if (this.lifecount() == 0) { // abort if all died
        System.out.println("OH GOD EVERYONE IS DEAD");
        // JOptionPane.showMessageDialog(null, String.format("OH GOD EVERYONE IS DEAD"));
        break;
      }
    }
  }

  public void evolve() { // calculates the next gen forever and ever (until everybody dies)
    this.evolve(1);
    if (this.lifecount() != 0) {
      this.evolve();
    }
  }

  public String toString() {
    StringBuffer game = new StringBuffer();
    for (int x = 0; x < this.size; x++) {
      game.append("\n");
      for (int y = 0; y < this.size; y++) {
        if (this.area[x][y] == true) {
          game.append("@");
        } else {
          game.append(".");
        }
      }
    }
    return game.toString();
  }
}

