/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import static core.competition.CompetitionParameters.IMG_PATH;

import java.util.Random;

import metrics.plot.MetricVisualiser;
import tracks.ArcadeMachine;
import static core.competition.CompetitionParameters.IMG_PATH;
import static core.competition.CompetitionParameters.MAX_TIMESTEPS;

/**
 *
 * @author vv
 */
public class RunSimpleTest {
    
    
    public static void main(String[] args){
        //Run Game: GVGAI game with random agent
        String gameName = "sheriff";
        int levelId = 0;
        String gamesDir = "";
        if (args.length==1) {
            gamesDir = args[0];
            IMG_PATH = gamesDir +  "sprites/";

        }
        String game = gamesDir + "examples/gridphysics/" + gameName+ ".txt";
        String level = gamesDir + "examples/gridphysics/" + gameName+ "_lvl" + levelId + ".txt";
        String playerClassString = "tracks.gameDesign.logger.Agent";

        String action_file = "action_log.txt";
        System.out.println("Map: " + game);
        System.out.println("level: " + level);
        System.out.println("Player Class: " + playerClassString);
        System.out.println("Agent Action file: " + action_file );

        int seed = new Random().nextInt();

        //double[] gameScore = ArcadeMachine.runOneGame(map, level, true, playerClassString, action_file, seed, 0);

        GameLogger logger = new SampleLogger();
        int NUM_GAMES = 2;
        boolean visuals = true;
        ArcadeMachine.runGames(game, new String[]{level}, NUM_GAMES, playerClassString, null, logger, visuals);
    }

    
    //Set up the logger, run the game, report results
    
}
