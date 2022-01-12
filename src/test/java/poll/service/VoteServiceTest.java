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
    public void should_ReturnVote_when_GivenVoteId() {
        //given
        Vote expectedVote = new Vote(3l, "Ivan Zaitsev", LocalDateTime.of(2021, 1, 2, 9, 30), Answer.YES);
        //when
        when(voteRepository.findById(3l)).thenReturn(Optional.of(expectedVote));
        //then
        assertEquals(voteService.getVoteById(3l), expectedVote);
    }

    @Test
    public void should_ThrowVoteFoundException_when_NotFoundVoteId() {
        //given
        long id = 100l;
        //when
        when(voteRepository.findById(id)).thenReturn(Optional.empty());
        //then
        assertThrows(VoteNotFoundException.class, () -> voteService.getVoteById(id));
    }


    @Test
    public void should_ReturnEmptyVote_when_NotFoundVoteId(){
        //given
        long id = 100l;
        Vote emptyVote = new Vote(id, null,null,null);
        //when
        when(voteRepository.findById(id)).thenReturn(Optional.of(emptyVote));
        //then
        assertSame(emptyVote,voteService.getOrCreateVote(id));
    }

    @Test
    public void should_ReturnListVoteWithYesAnswerByName_when_GivenName(){
        //given
        List<Vote> voteList = Arrays.asList(
                     new Vote(1l, "David Smith", LocalDateTime.of(2021, 1, 1, 14, 30), Answer.YES),
                     new Vote(2l, "David Smith", LocalDateTime.of(2021, 1, 1, 15, 30), Answer.YES));
        //when
        when(voteRepository.findByName("David Smith")).thenReturn(voteList);
        //then
        assertEquals(voteList,voteService.getAllYesVotesByName("David Smith"));
    }




}
