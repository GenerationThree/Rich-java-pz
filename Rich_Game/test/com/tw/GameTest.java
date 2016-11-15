package com.tw;

import com.tw.map.*;
import com.tw.player.Player;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by pzzheng on 11/14/16.
 */
public class GameTest {
    @Test
    public void should_pass_turn_when_in_hospital_or_prison() {
        GameMap map = mock(GameMap.class);
        Game game = new Game(map);
        Player player = Player.createPlayerWith_Fund_Map_WAIT_TURN_STATE(map, 10000);
        Player player1 = Player.createPlayerWith_Fund_Map_WAIT_TURN_STATE(map, 10000);
        game.initialPlayers(player, player1);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        player1.stuckIn(new Prison(), 2);

        game.nextPlayer();
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_TURN));


        game.nextPlayer();
        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));

        game.nextPlayer();
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_end_game_when_player_quit_game() {
        GameMap map = mock(GameMap.class);
        Game game = new Game(map);
        Player player = Player.createPlayerWith_Fund_Map_WAIT_TURN_STATE(map, 10000);
        Player player1 = Player.createPlayerWith_Fund_Map_WAIT_TURN_STATE(map, 10000);
        game.initialPlayers(player, player1);

        assertThat(game.getStatus(), is(Game.Status.GAME_START));

        player1.quit();

        assertThat(game.getStatus(), is(Game.Status.GAME_END));
    }

    @Test
    public void should_shift_to_next_player_when_player_end_turn() {
        GameMap map = mock(GameMap.class);
        Game game = new Game(map);
        Player player = Player.createPlayerWith_Fund_Map_WAIT_TURN_STATE(map, 10000);
        Player player1 = Player.createPlayerWith_Fund_Map_WAIT_TURN_STATE(map, 10000);
        game.initialPlayers(player, player1);

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_TURN));

        player.endTurn();

        assertThat(player.getStatus(), is(Player.Status.WAIT_FOR_TURN));
        assertThat(player1.getStatus(), is(Player.Status.WAIT_FOR_COMMAND));
    }

    @Test
    public void should_end_game_when_only_one_player_not_bankrupt() {
        GameMap map = mock(GameMap.class);
        Game game = new Game(map);
        Player player = Player.createPlayerWith_Fund_Map_WAIT_TURN_STATE(map, 10000);
        Player player1 = Player.createPlayerWith_Fund_Map_WAIT_TURN_STATE(map, 10000);
        Player player2 = Player.createPlayerWith_Fund_Map_WAIT_TURN_STATE(map, 10000);
        game.initialPlayers(player, player1, player2);

        player.bankrupt();
        player1.bankrupt();

        assertThat(game.getStatus(), is(Game.Status.GAME_END));
    }
}