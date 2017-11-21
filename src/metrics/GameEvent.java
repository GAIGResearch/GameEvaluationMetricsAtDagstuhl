package metrics;


import tools.Vector2d;

public class GameEvent {

    public String name;
    public Vector2d avatarPosition;
    public int time;

    public GameEvent(String name){
        this.name = name;
    }

    public GameEvent(String name, int time, Vector2d position){
        this.name = name;
        this.time = time;
        this.avatarPosition = position;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
