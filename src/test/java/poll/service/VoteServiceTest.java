package poll.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poll.repository.Answer;
import poll.repository.Vote;
import poll.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @Mock
    VoteRepository voteRepository;


    @InjectMocks
    VoteService voteService;


    private List<Vote> answersDbTest = Arrays.asList(
            new Vote(1l, "Earvin Ngapeth", LocalDateTime.of(2021, 1, 1, 14, 30), Answer.NO),
            new Vote(2l, "David Smith", LocalDateTime.of(2021, 1, 1, 15, 30), Answer.YES),
            new Vote(3l, "Ivan Zaitsev", LocalDateTime.of(2021, 1, 2, 9, 30), Answer.YES),
            new Vote(4l, "Osmany Juantorena", LocalDateTime.of(2021, 1, 2, 9, 45), Answer.NO),
            new Vote(5l, "Uros Kovacevic", LocalDateTime.of(2021, 1, 2, 10, 0), Answer.NO),
            new Vote(6l, "Marko Podrascanin", LocalDateTime.of(2021, 1, 2, 14, 30), Answer.NO),
            new Vote(7l, "Swan Ngapeth", LocalDateTime.of(2021, 1, 3, 8, 0), Answer.YES),
            new Vote(8l, "Kamil Semeniuk", LocalDateTime.of(2021, 1, 3, 14, 0), Answer.YES),
            new Vote(9l, "Seyed Mousavi", LocalDateTime.of(2021, 1, 3, 14, 59), Answer.YES));


    @Test
    public void should_ReturnVote_when_GivenVoteIdAndVoteFound() {
        //given
        long givenVoteId = 3l;
        Vote expectedVote = answersDbTest
                .stream()
                .filter(vote -> vote.getId() == givenVoteId)
                .findFirst()
                .orElseThrow();
        when(voteRepository.findById(givenVoteId)).thenReturn(Optional.of(expectedVote));
        //when
        //then
        assertEquals(expectedVote, voteService.getVoteById(givenVoteId));
    }

    @Test
    public void should_ThrowVoteFoundException_when_NotFoundVoteId() {
        //given
        long id = 100l;
        when(voteRepository.findById(id)).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(VoteNotFoundException.class, () -> voteService.getVoteById(id));
    }


    @Test
    public void should_ReturnEmptyVote_when_NotFoundVoteId() {
        //given
        long id = 100l;
        Vote emptyVote = Vote.empty(id);
        when(voteRepository.findById(id)).thenReturn(Optional.of(emptyVote));
        //when
        //then
        assertEquals(emptyVote, voteService.getOrCreateVote(id));
    }

    @Test
    public void should_ReturnListVoteWithYesAnswerByName_when_GivenName() {
        //given
        String givenName = "David Smith";
        List<Vote> expectedVoteList = answersDbTest
                .stream()
                .filter(vote -> vote.getName().equals(givenName))
                .filter(vote -> vote.getAnswer().equals(Answer.YES))
                .collect(Collectors.toList());
        when(voteRepository.findByName(givenName)).thenReturn(expectedVoteList);
        //then
        assertEquals(expectedVoteList, voteService.getAllYesVotesByName(givenName));
    }

    @Test
    public void should_returnNumberOfVotesByAnswer_when_GivenAnswer() {
        //given
        int expectedNumber = 5;
        Answer givenAnswer = Answer.YES;
        List<Vote> votesListWithYesAnswer = answersDbTest.stream().filter(vote -> vote.getAnswer().equals(givenAnswer)).collect(Collectors.toList());
        when(voteRepository.findByAnswer(Answer.YES)).thenReturn(votesListWithYesAnswer);
        //then
        assertEquals(expectedNumber, voteService.countAllVotesOnAnswer(Answer.YES));
    }

    @Test
    public void should_returnTrue_when_GivenAnswerWonTheVote() {
        //given
        boolean expectedReturn = true;
        Answer givenAnswer = Answer.YES;
        List<Vote> votesListWithYesAnswer = answersDbTest.stream().filter(vote -> vote.getAnswer().equals(givenAnswer)).collect(Collectors.toList());
        when(voteRepository.findByAnswer(givenAnswer)).thenReturn(votesListWithYesAnswer);
        when(voteRepository.findAll()).thenReturn(answersDbTest);
        //then
        assertEquals(expectedReturn, voteService.hasAnswerWonTheVote(givenAnswer));
    }

}
