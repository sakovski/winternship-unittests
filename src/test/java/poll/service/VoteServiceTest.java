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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

    @Mock
    VoteRepository voteRepository;

    @InjectMocks
    VoteService voteService;


    @Test
    public void should_ReturnVote_when_GivenVoteIdAndVoteFound() {
        //given
        Vote expectedVote = new Vote(3l, "Ivan Zaitsev", LocalDateTime.of(2021, 1, 2, 9, 30), Answer.YES);
        when(voteRepository.findById(3l)).thenReturn(Optional.of(expectedVote));
        //when
        //then
        assertEquals(expectedVote,voteService.getVoteById(3l));
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
        Vote emptyVote = new Vote(id, null, null, null);
        when(voteRepository.findById(id)).thenReturn(Optional.of(emptyVote));
        //when
        //then
        assertSame(emptyVote, voteService.getOrCreateVote(id));
    }

    @Test
    public void should_ReturnListVoteWithYesAnswerByName_when_GivenName() {
        //given
        List<Vote> expectedVoteList = Arrays.asList(
                new Vote(1l, "David Smith", LocalDateTime.of(2021, 1, 1, 14, 30), Answer.YES),
                new Vote(2l, "David Smith", LocalDateTime.of(2021, 1, 1, 15, 30), Answer.YES),
                new Vote(2l, "David Smith", LocalDateTime.of(2021, 1, 1, 15, 30), Answer.YES));
        when(voteRepository.findByName("David Smith")).thenReturn(expectedVoteList);
        //when
        //then
        assertEquals(expectedVoteList, voteService.getAllYesVotesByName("David Smith"));
    }

    @Test
    public void should_returnNumberOfVotesByAnswer_when_GivenAnswer(){
        //given
        int expectedNumber = 4;
        List<Vote> votesListWithYesAnswer = Arrays.asList(
                new Vote(5l, "Uros Kovacevic", LocalDateTime.of(2021, 1, 2, 10, 0), Answer.YES),
                new Vote(6l, "Marko Podrascanin", LocalDateTime.of(2021, 1, 2, 14, 30), Answer.YES),
                new Vote(7l, "Swan Ngapeth", LocalDateTime.of(2021, 1, 3, 8, 0), Answer.YES),
                new Vote(8l, "Kamil Semeniuk", LocalDateTime.of(2021, 1, 3, 14, 0), Answer.YES));
        when(voteRepository.findByAnswer(Answer.YES)).thenReturn(votesListWithYesAnswer);
        //then
        assertEquals(expectedNumber,voteService.countAllVotesOnAnswer(Answer.YES));
    }

    @Test
    public void should_returnTrue_when_GivenAnswerWonTheVote(){
        boolean expectedReturn = true;
        List<Vote> votesListWithYesAnswer = Arrays.asList(
                new Vote(5l, "Uros Kovacevic", LocalDateTime.of(2021, 1, 2, 10, 0), Answer.YES),
                new Vote(6l, "Marko Podrascanin", LocalDateTime.of(2021, 1, 2, 14, 30), Answer.YES),
                new Vote(7l, "Swan Ngapeth", LocalDateTime.of(2021, 1, 3, 8, 0), Answer.NO),
                new Vote(8l, "Kamil Semeniuk", LocalDateTime.of(2021, 1, 3, 14, 0), Answer.YES));
//    when(voteRepository.findAll()).thenReturn()

    }

}
