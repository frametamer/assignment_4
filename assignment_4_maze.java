import java.util.HashMap;
import java.util.Map;

public enum Direction {
    NORTH, EAST, SOUTH, WEST
}

abstract class Wall implements Cloneable {
    public abstract Wall clone();
}

class SimpleWall extends Wall {
    @Override
    public Wall clone() {
        return new SimpleWall();
    }
}

class DoorWall extends Wall {
    private Room room1, room2;

    public DoorWall(Room r1, Room r2) {
        room1 = r1;
        room2 = r2;
    }

    @Override
    public Wall clone() {
        return new DoorWall(room1, room2);
    }
}

class Room implements Cloneable {
    private int roomNo;
    private Map<Direction, Wall> sides = new HashMap<>();

    public Room(int no) {
        roomNo = no;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setSide(Direction dir, Wall wall) {
        sides.put(dir, wall);
    }

    public Wall getSide(Direction dir) {
        return sides.get(dir);
    }

    @Override
    public Room clone() {
        Room room = new Room(roomNo);
        for (Direction dir : sides.keySet()) {
            room.sides.put(dir, sides.get(dir).clone());
        }
        return room;
    }
}

abstract class MazePrototype implements Cloneable {
    private Map<Integer, Room> rooms = new HashMap<>();

    public void addRoom(Room r) {
        rooms.put(r.getRoomNo(), r);
    }

    public Room getRoom(int no) {
        return rooms.get(no);
    }

    public abstract MazePrototype clone();
}

class SimpleMazePrototype extends MazePrototype {
    @Override
    public MazePrototype clone() {
        SimpleMazePrototype maze = new SimpleMazePrototype();
        for (Room room : rooms.values()) {
            maze.addRoom(room.clone());
        }
        return maze;
    }
}

public class MazeGame {
    public static void main(String[] args) {
        SimpleMazePrototype prototype = createMaze();
        MazePrototype maze1 = prototype.clone();
        MazePrototype maze2 = prototype.clone();
    }

    private static SimpleMazePrototype createMaze() {
        SimpleMazePrototype maze = new SimpleMazePrototype();
        Room r1 = new Room(1);
        Room r2 = new Room(2);
        DoorWall d = new DoorWall(r1, r2);

        maze.addRoom(r1);
        maze.addRoom(r2);

        r1.setSide(Direction.NORTH, d);
        r1.setSide(Direction.EAST, new SimpleWall());
        r1.setSide(Direction.SOUTH, new SimpleWall());
        r1.setSide(Direction.WEST, new SimpleWall());
        r2.setSide(Direction.NORTH, new SimpleWall());
        r2.setSide(Direction.EAST, new SimpleWall());
        r2.setSide(Direction.SOUTH, d);
        r2.setSide(Direction.WEST, new SimpleWall());

        return maze;
    }
}