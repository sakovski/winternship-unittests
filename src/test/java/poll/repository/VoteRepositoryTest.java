package poll.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VoteRepositoryTest {

    private VoteRepository voteRepository;

    @BeforeEach
    void init() {
        voteRepository = new VoteRepository();
    }

    @Test
    void should_find_all_from_db() {
        //given
        //when
        List<Vote> result = voteRepository.findAll();
        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(9);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(2).getId()).isEqualTo(3L);
        assertThat(result.get(3).getId()).isEqualTo(4L);
        assertThat(result.get(4).getId()).isEqualTo(5L);
        assertThat(result.get(5).getId()).isEqualTo(6L);
        assertThat(result.get(6).getId()).isEqualTo(7L);
        assertThat(result.get(7).getId()).isEqualTo(8L);
        assertThat(result.get(8).getId()).isEqualTo(9L);
    }

    @Test
    void should_find_vote_by_id() {
        //given
        long id = 9L;
        //when
        final var result = voteRepository.findById(id);
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get()).usingRecursiveComparison()
                .isEqualTo(new Vote(9L, "Seyed Mousavi", LocalDateTime.of(2021, 1, 3, 14, 59), Answer.YES));
    }

    @Test
    void should_find_votes_by_name() {
        //given
        final var name = "Ngapeth";
        //when
        final var result = voteRepository.findByName(name);
        //then
        assertThat(result).isNotEmpty().hasSize(2);
        assertThat(result.get(0).getName()).contains(name);
    }

    @Test
    void should_find_votes_by_answer() {
        //given
        final var answer = Answer.YES;
        //when
        final var result = voteRepository.findByAnswer(answer);
        //then
        assertThat(result).isNotEmpty().hasSize(5);
        assertThat(result.get(0).getAnswer()).isEqualTo(answer);
    }

    @Test
    void should_find_votes_before_date() {
        //given
        final var date = LocalDateTime.of(2021, 1, 3, 14, 59);
        //when
        final var result = voteRepository.findByDateBefore(date);
        //then
        assertThat(result).isNotEmpty().hasSize(8);
        assertThat(result.get(0).getDate()).isBefore(date);
    }

    @Test
    void should_add_new_vote() {
        //given
        Vote newVote = new Vote(1L, "Psikutas bezS", LocalDateTime.of(2021, 1, 3, 14, 59), Answer.YES);
        //when
        //then
        Assertions.assertThrows(UnsupportedOperationException.class, () -> voteRepository.addVote(newVote));
    }
}
