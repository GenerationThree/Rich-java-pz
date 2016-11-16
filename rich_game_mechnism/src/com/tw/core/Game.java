package com.tw.core;

import com.tw.core.commands.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pzzheng on 11/16/16.
 */
public class Game {
    private List<Player> players;
    private Status status;
    private int currentPlayerIndex;

    public Game(GameMap map) {
        status = Status.START;
        players = new ArrayList<>();
    }

    public void initialPlayers(Player... players) {
        this.players = Arrays.asList(players);
        this.players.get(0).inTurn();
        currentPlayerIndex = 0;
    }

    public Status getStatus() {
        if(players.stream().filter(player -> !player.getStatus().equals(Player.Status.BANKRUPT)).count() == 1) {
            status = Status.END;
        }
        return status;
    }

    public void quit() {
        status = Status.END;
    }

    public Player.Status execute(Command command) {
        return players.get(currentPlayerIndex).execute(command);
    }

    public enum Status {END, START}
}
