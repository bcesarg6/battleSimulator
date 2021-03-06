package BS.game;

import BS.game.actions.skills.Skill;
import BS.game.agents.Agent;
import BS.game.buffs.Buff;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Game {
    private List<Agent> agents;
    private Queue<Skill> actions;

    private Comparator<Agent> comparator = (o1, o2) -> (int) (o1.getMax_hp() - o2.getMax_hp());


    public Game(List<Agent> agents) {
        this.agents = agents;
        actions = new LinkedBlockingQueue<>();
        agents.sort(comparator);
    }

    public void gameLoop(){
        for(Agent agent : agents){
            actions.add(agent.getActionListener().getAction());
        }

        for(Skill skill : actions){
            skill.execute();
        }

        actions.clear();
        agents.sort(comparator);
        refreshBuffs();

        System.out.println("\n--- END OF TURN ---\n");
    }

    /**
     * Decrease turns of every buff of every agent in battle
     */
    public void refreshBuffs(){
        for (Agent a : agents) {
            for (Buff b: a.getBuffs()) {
                b.decreaseDuration();
            }

        }

    }
}
