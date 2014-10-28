package com.performgroup.innovation.kickers.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;


public class TeamTest {

    Team teamUnderTest;

    @Before
    public void setUp() throws Exception {
        teamUnderTest = new Team(TeamColor.UNDEFINED, -1);
    }

    @After
    public void tearDown() throws Exception {
        teamUnderTest = null;
    }

    @Test
    public void shouldSupportSwitchingColor() {
        //given
        teamUnderTest.color = TeamColor.RED;
        //when
        teamUnderTest.switchColor();
        //then
        assertThat(teamUnderTest.color).isEqualTo(TeamColor.BLUE);
        //when
        teamUnderTest.switchColor();
        //then
        assertThat(teamUnderTest.color).isEqualTo(TeamColor.RED);
    }

    @Test
    public void shouldBeWellInitialized() {
        assertThat(teamUnderTest.players).isNotNull().isEmpty();
        assertThat(teamUnderTest.color).isEqualTo(TeamColor.UNDEFINED);
        assertThat(teamUnderTest.ID).isEqualTo(Team.NULL.ID);
        assertThat(teamUnderTest.scores).isNotNull().isEmpty();
        assertThat(teamUnderTest.wins).isEqualTo(0);
    }

    @Test
    public void shouldSupportAddingPlayers() {
        //when
        teamUnderTest.add(new Player(0, "A", PlayerRole.ATTACER));
        //then
        assertThat(teamUnderTest.players).hasSize(1);
    }


    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenAddingTooManyPlayers() {
        //when
        teamUnderTest.add(new Player(0, "A", PlayerRole.ATTACER));
        teamUnderTest.add(new Player(1, "B", PlayerRole.ATTACER));
        teamUnderTest.add(new Player(2, "C", PlayerRole.ATTACER));
        //then
        fail("Should throw Illegal state exception.");
    }

    @Test
    public void shouldAllowToSwitchPlayerRoles() {
        //given
        teamUnderTest.add(new Player(0, "A", PlayerRole.ATTACER));
        teamUnderTest.add(new Player(1, "B", PlayerRole.DEFFENDER));
        //when
        teamUnderTest.switchRoles();
        //then
        assertThat(teamUnderTest.getPlayer(PlayerRole.DEFFENDER).name).isEqualTo("A");
        assertThat(teamUnderTest.getPlayer(PlayerRole.ATTACER).name).isEqualTo("B");
    }



    @Test
    public void shouldReturnNameBasedOnPlayersNames() {
        //given
        teamUnderTest.add(new Player(0, "A", PlayerRole.ATTACER));
        teamUnderTest.add(new Player(1, "B", PlayerRole.DEFFENDER));
        //when
        String name = teamUnderTest.getName();
        //then
        assertThat(name).isEqualTo("A & B");
    }

}