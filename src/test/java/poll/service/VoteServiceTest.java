package poll.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poll.repository.Answer;
import poll.repository.IVoteRepository;
import poll.repository.Vote;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private IVoteRepository voteRepository;
    @InjectMocks
    private VoteService voteService;

    private List<Vote> answersDb;

    @BeforeEach
    void init() {
        answersDb = Arrays.asList(
                new Vote(1L, "Earvin Ngapeth", LocalDateTime.of(2021, 1, 1, 14, 30), Answer.NO),
                new Vote(2L, "David Smith", LocalDateTime.of(2021, 1, 1, 15, 30), Answer.YES),
                new Vote(3L, "Ivan Zaitsev", LocalDateTime.of(2021, 1, 2, 9, 30), Answer.YES),
                new Vote(4L, "Osmany Juantorena", LocalDateTime.of(2021, 1, 2, 9, 45), Answer.NO),
                new Vote(5L, "Uros Kovacevic", LocalDateTime.of(2021, 1, 2, 10, 0), Answer.NO),
                new Vote(6L, "Marko Podrascanin", LocalDateTime.of(2021, 1, 2, 14, 30), Answer.NO),
                new Vote(7L, "Swan Ngapeth", LocalDateTime.of(2021, 1, 3, 8, 0), Answer.YES),
                new Vote(8L, "Kamil Semeniuk", LocalDateTime.of(2021, 1, 3, 14, 0), Answer.YES),
                new Vote(9L, "Seyed Mousavi", LocalDateTime.of(2021, 1, 3, 14, 59), Answer.YES)
        );
    }


    @Test
    void should_find_vote_by_id() {
        //given
        long id = 1L;
        when(voteRepository.findById(id)).thenReturn(Optional.of(answersDb.get(0)));
        //when
        //then
        assertThat(answersDb.get(0)).isEqualTo(voteService.getVoteById(id));
    }

    @Test
    void should_throw_exception_when_find_by_id_not_found_Vote() {
        //given
        long id = 100;
        when(voteRepository.findById(id)).thenReturn(Optional.empty());
        //when
        //then
        final var thrownException = catchThrowable(() -> voteService.getVoteById(id));
        assertThat(thrownException)
                .isInstanceOf(VoteNotFoundException.class)
                .hasMessageContaining("Vote with id " + id + " not found");
    }

    @Test
    void should_create_new_vote_when_not_found_by_id() {
        //given
        long id = 100;
        when(voteRepository.findById(id)).thenReturn(Optional.empty());
        //when
        final var result = voteService.getOrCreateVote(id);
        //then
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isNull();
        assertThat(result.getDate()).isNull();
        assertThat(result.getAnswer()).isNull();
    }


    @Test
    void should_return_all_yes_votes_by_name() {
        //given
        String name = "Ngapeth";
        when(voteRepository.findByName(name)).thenReturn(List.of(answersDb.get(6), answersDb.get(0)));
        //when
        final var result = voteService.getAllYesVotesByName(name);
        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).contains(name);
        assertThat(result.get(0).getAnswer()).isEqualTo(Answer.YES);
        assertThat(List.of(answersDb.get(6))).isEqualTo(voteService.getAllYesVotesByName(name));
    }

    @Test
    void should_return_empty_list_when_name_not_exist() {
        //given
        String name = "Miecio";
        when(voteRepository.findByName(name)).thenReturn(Collections.emptyList());
        //when
        //then
        assertThat(voteService.getAllYesVotesByName(name)).isEmpty();
    }

    @Test
    void should_return_true_when_majority_answers_were_yes() {
        //given
        Answer answer = Answer.YES;
        when(voteRepository.findAll()).thenReturn(answersDb);
        when(voteRepository.findByAnswer(answer)).thenReturn(answersDb
                .stream()
                .filter(vote -> vote.getAnswer().equals(answer))
                .collect(Collectors.toList()));
        //when
        final var result = voteService.hasAnswerWonTheVote(answer);
        //then
        Assertions.assertTrue(result);

    }

    @Test
    void should_count_all_votes_on_answer() {
        //given
        Answer answer = Answer.YES;
        when(voteRepository.findByAnswer(answer)).thenReturn(answersDb
                .stream()
                .filter(vote -> vote.getAnswer().equals(answer))
                .collect(Collectors.toList()));
        //when
        final var result = voteService.countAllVotesOnAnswer(answer);
        //then
        Assertions.assertEquals(5, result);
    }
}
