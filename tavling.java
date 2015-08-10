public class Solution {

    //For saving the map
    int[][] map;

    //To keeping track of location and direction
    /** Always sets the starting direction to up.
        dir = 0 -> up
        dir = 1 -> right
        dir = 2 -> down
        dir = 3 -> left
    */
    int x, y, dir, front,back,left,right;

    public Solution() {
        // If you need initialization code, you can write it here!
        map = new int[39][39];
        intitializeMap();
        x = 19;
        y = 19;
        dir = 0;
    }

    public void intitializeMap() {
      for (int i = 0; i < 39; i++) {
        for (int j = 0; j < 39; j++) {
          map[x][y] = 0;
        }
      }
    }

    /**
     * Executes a single step of the tank's programming. The tank can only move,
     * turn, or fire its cannon once per turn. Between each update, the tank's
     * engine remains running and consumes 1 fuel. This function will be called
     * repeatedly until there are no more targets left on the grid, or the tank
     * runs out of fuel.
     */
    public void update() {
        // Todo: Write your code here!
        makeMap();
        printMap();
        if (API.identifyTarget()) {
          front = 0;
          right = 0;
          left = 0;
          back = 0;
          API.fireCannon();
        }
        move();
    }

    public void move() {
      if (front > 1) {
        API.moveForward();
        switch (dir) {
          case 0:
            y--;
          break;
          case 1:
            x++;
          break;
          case 2:
            y++;
          break;
          case 3:
            x--;
          break;
          default:
          break;
        }
      } else if ( left < right && left > 1) {
        API.turnLeft();
        dir += 3;
        if (dir >= 4) {
          dir -=4;
        }
      } else {
        API.turnRight();
        dir -= 3;
        if (dir < 0) {
          dir +=4;
        }
      }
    }

    public void makeMap() {
      front = API.lidarFront();
      back = API.lidarBack();
      left = API.lidarLeft();
      right = API.lidarRight();
      switch (dir) {
        case 0:
          drawOnMap(left,right,front,back);
        break;
        case 1:
        drawOnMap(back,front,left,right);
        break;
        case 2:
        drawOnMap(right,left,back,front);
        break;
        case 3:
        drawOnMap(front,back,right,left);
        break;
        default:
        break;
      }
    }

    public void drawOnMap(int xMinus, int xPlus, int yMinus, int yPlus) {
      for (int i = 0; i < xMinus; i++) {
        map[x-i][y] = 1;
      }
      for (int i = 1; i < xPlus; i++) {
        map[x+i][y] = 1;
      }
      for (int i = 1; i < yMinus; i++) {
        map[x][y-i] = 1;
      }
      for (int i = 1; i < yPlus; i++) {
        map[x][y+i] = 1;
      }
      map[x-xMinus][y] = 2;
      map[x+xPlus][y] = 2;
      map[x][y-yMinus] = 2;
      map[x][y+yPlus] = 2;
    }

    public void printMap() {
      for (int i = 0; i < 39; i++) {
        for (int j = 0; j < 39; j++) {
          System.out.print(map[j][i]);
          if(j == 38) {
            System.out.println();
          }
        }
      }
      System.out.println("\n\n\n\n\n\n");
    }
}
