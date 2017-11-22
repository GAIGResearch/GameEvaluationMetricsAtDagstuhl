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
        this.name = escape(name);
        this.time = time;
        this.avatarPosition = position;
    }

    private String escape(String name)
    {
        name = name.replace(">","");
        name = name.replace(" ","_");
        name = name.replace("=","__");
        return name;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
