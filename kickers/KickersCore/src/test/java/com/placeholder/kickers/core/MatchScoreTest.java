package com.placeholder.kickers.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class MatchScoreTest {


    MatchScore scoreUnderTest;

    @Before
    public void setUp() throws Exception {
        scoreUnderTest = new MatchScore();
    }

    @After
    public void tearDown() throws Exception {
        scoreUnderTest = null;
    }

    @Test
    public void shouldSupportCreatingScoreForGivenMatch() {
        //given
        Match match = createMatchMock();
        //when
        MatchScore score = MatchScore.createFor(match);
        //then
        assertThat(score).isNotNull();
    }

    @Test
    public void shouldReturnNullTeamIDAsDefaultWinner() {
        assertThat(scoreUnderTest.getWinner()).isEqualTo(Team.NULL.ID);
    }

    @Test
    public void shouldReturnWinnerTeamId() {
        //given
        Match matchMock = createMatchMock();
        Team firstTeam = matchMock.lineups.teams.get(0);
        Team secondTeam = matchMock.lineups.teams.get(1);
        scoreUnderTest = MatchScore.createFor(matchMock);

        // FIRST GOAL
        //when
        scoreUnderTest.addPointForTeam(firstTeam.ID);
        //then
        assertThat(scoreUnderTest.getWinner()).isEqualTo(firstTeam.ID);

        // SECOND GOAL
        //when
        scoreUnderTest.addPointForTeam(secondTeam.ID);
        //then
        assertThat(scoreUnderTest.getWinner()).isEqualTo(Team.NULL.ID);

        // THIRD GOAL
        //when
        scoreUnderTest.addPointForTeam(secondTeam.ID);
        //then
        assertThat(scoreUnderTest.getWinner()).isEqualTo(secondTeam.ID);

    }

    @Test
    public void shouldReturnZeroPointsIfTeamDoesntExist() {
        assertThat(scoreUnderTest.getPoints(333)).isEqualTo(0);
    }

    private Match createMatchMock() {
        Lineups lineups = new Lineups();
        lineups.addTeam(new Team(TeamColor.UNDEFINED, 0));
        lineups.addTeam(new Team(TeamColor.UNDEFINED, 1));
        return new Match(lineups, 0);
    }



   /* @Test
    public void shouldReturnUndefinedWinnerAtTheBeginning()
    {
        //given
        //when
        //then
        assertThat(scoreUnderTest.getWinner()).isEqualTo()
    }*/

}