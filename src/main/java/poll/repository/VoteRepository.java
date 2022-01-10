package poll.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VoteRepository implements IVoteRepository {

    private List<Vote> answersDb = Arrays.asList(
            new Vote(1l, "Earvin Ngapeth", LocalDateTime.of(2021, 1, 1, 14, 30), Answer.NO),
            new Vote(2l, "David Smith", LocalDateTime.of(2021, 1, 1, 15, 30), Answer.YES),
            new Vote(3l, "Ivan Zaitsev", LocalDateTime.of(2021, 1, 2, 9, 30), Answer.YES),
            new Vote(4l, "Osmany Juantorena", LocalDateTime.of(2021, 1, 2, 9, 45), Answer.NO),
            new Vote(5l, "Uros Kovacevic", LocalDateTime.of(2021, 1, 2, 10, 0), Answer.NO),
            new Vote(6l, "Marko Podrascanin", LocalDateTime.of(2021, 1, 2, 14, 30), Answer.NO),
            new Vote(7l, "Swan Ngapeth", LocalDateTime.of(2021, 1, 3, 8, 0), Answer.YES),
            new Vote(8l, "Kamil Semeniuk", LocalDateTime.of(2021, 1, 3, 14, 0), Answer.YES),
            new Vote(9l, "Seyed Mousavi", LocalDateTime.of(2021, 1, 3, 14, 59), Answer.YES)
    );

    public VoteRepository() {}

    public VoteRepository(List<Vote> answersDb) {
        this.answersDb = answersDb;
    }

    @Override
    public List<Vote> findAll() {
        return new ArrayList<>(answersDb);
    }

    @Override
    public Optional<Vote> findById(long id) {
        return answersDb
                .stream()
                .filter(vote -> vote.getId() == id)
                .findFirst();
    }

    @Override
    public List<Vote> findByName(String name) {
        return answersDb
                .stream()
                .filter(vote -> vote.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vote> findByAnswer(Answer answer) {
        return answersDb
                .stream()
                .filter(vote -> vote.getAnswer().equals(answer))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vote> findByDateBefore(LocalDateTime date) {
        return answersDb
                .stream()
                .filter(vote -> vote.getDate().isBefore(date))
                .collect(Collectors.toList());
    }

    @Override
    public void addVote(Vote vote) {
        answersDb.add(vote);
    }
}
