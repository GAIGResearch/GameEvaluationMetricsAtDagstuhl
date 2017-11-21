/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import java.util.Random;
import tracks.ArcadeMachine;

/**
 *
 * @author vv
 */
public class RunSimpleTest {
    
    
    public static void main(String[] args){
        //Run Game: GVGAI game with random agent
        String gameName = "aliens";
        int levelId = 0;
        String map = "examples/gridphysics/" + gameName+ ".txt";
        String level = "examples/gridphysics/" + gameName+ "_lvl" + levelId + ".txt";
        String playerClassString = "tracks.gameDesign.logger.Agent";
        String action_file = "action_log.txt";
        System.out.println("Map: " + map);
        System.out.println("level: " + level);
        System.out.println("Player Class: " + playerClassString);
        System.out.println("Agent Action file: " + action_file );

        int seed = new Random().nextInt();

        double[] gameScore = ArcadeMachine.runOneGame(map, level, true, playerClassString, action_file, seed, 0);
    }

    
    //Set up the logger, run the game, report results
    
}
