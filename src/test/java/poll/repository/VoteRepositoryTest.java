package poll.repository;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class VoteRepositoryTest {


    private List<Vote> listVote = Arrays.asList(
            new Vote(1l, "Earvin Ngapeth", LocalDateTime.of(2021, 1, 1, 14, 30), Answer.NO),
            new Vote(2l, "David Smith", LocalDateTime.of(2021, 1, 1, 15, 30), Answer.YES),
            new Vote(3l, "Ivan Zaitsev", LocalDateTime.of(2021, 1, 2, 9, 30), Answer.YES),
            new Vote(4l, "Osmany Juantorena", LocalDateTime.of(2021, 1, 2, 9, 45), Answer.NO),
            new Vote(5l, "Osmany Juantorena", LocalDateTime.of(2021, 1, 2, 10, 0), Answer.NO),
            new Vote(6l, "Marko Podrascanin", LocalDateTime.of(2021, 1, 2, 14, 30), Answer.NO),
            new Vote(7l, "Swan Ngapeth", LocalDateTime.of(2021, 1, 3, 8, 0), Answer.YES),
            new Vote(8l, "Kamil Semeniuk", LocalDateTime.of(2021, 1, 3, 14, 0), Answer.YES),
            new Vote(9l, "Seyed Mousavi", LocalDateTime.of(2021, 1, 3, 14, 59), Answer.YES));

    private List<Vote> answersDbTest = new ArrayList<>(listVote);

    VoteRepository voteRepository = new VoteRepository(answersDbTest);

    @Test
    public void should_returnVoteWithId_when_GivenIdAndVoteFound() {
        //given
        long givenId = 3l;
        Vote expectedVote = answersDbTest.stream().filter(vote -> vote.getId() == givenId).findFirst().get();
        //when
        Vote actualVote = voteRepository.findById(givenId).get();
        //then
        assertEquals(expectedVote.getId(), actualVote.getId());
        assertEquals(expectedVote.getName(), actualVote.getName());
        assertEquals(expectedVote.getAnswer(), actualVote.getAnswer());
        assertEquals(expectedVote.getDate(), actualVote.getDate());
    }


    @Test
    public void should_returnEmptyOptional_when_GivenIdAndVoteNotFound() {
        //given
        long givenId = 100l;
        Optional<Vote> expectedVote = Optional.empty();
        //when
        Optional<Vote> actualVote = voteRepository.findById(givenId);
        //then
        assertEquals(expectedVote, actualVote);
    }

    @Test
    public void should_returnListOfVotesWithName_when_GivenName() {
        //given
        String givenName = "Osmany Juantorena";
        List<Vote> expectedVoteList = answersDbTest.stream().filter(vote -> vote.getName().contains(givenName)).collect(Collectors.toList());
        //when
        List<Vote> actualVoteList = voteRepository.findByName(givenName);
        //then
        assertEquals(expectedVoteList, actualVoteList);
    }

    @Test
    public void should_returnListOfVotesWithAnswer_when_GivenAnswer() {
        //given
        Answer givenAnswer = Answer.YES;
        List<Vote> expectedVoteList = answersDbTest.stream().filter(vote -> vote.getAnswer().equals(givenAnswer)).collect(Collectors.toList());
        //when
        List<Vote> actualVoteList = voteRepository.findByAnswer(givenAnswer);
        //then
        assertEquals(expectedVoteList, actualVoteList);
    }

    @Test
    public void should_returnListOfVotesBeforeDate_when_GivenDateBefore() {
        //given
        LocalDateTime givenDateBefore = LocalDateTime.of(2021, 1, 3, 8, 0);
        List<Vote> expectedVoteList = answersDbTest.stream().filter(vote -> vote.getDate().isBefore(givenDateBefore)).collect(Collectors.toList());
        //when
        List<Vote> actualVoteList = voteRepository.findByDateBefore(givenDateBefore);
        //then
        assertEquals(expectedVoteList, actualVoteList);
    }

    @Test
    public void should_returnListOfAllVotes() {
        //given
        //when
        List<Vote> actualVoteList = voteRepository.findAll();
        //then
        assertEquals(answersDbTest, actualVoteList);
    }


    @Test
    public void should_addVoteToList_when_GivenVote() {
        //given
        Vote voteToAdd = new Vote(10l, "Józef Cieplak", LocalDateTime.of(2021, 1, 3, 14, 0), Answer.YES);
        //when
        voteRepository.addVote(voteToAdd);
        List<Vote> listAfterAddVote = voteRepository.findAll();
        Vote addedVote = listAfterAddVote.get(listAfterAddVote.size() - 1);
        //then
        assertEquals(voteToAdd, addedVote);
    }

}
