package poll.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IVoteRepository {
    List<Vote> findAll();
    Optional<Vote> findById(long id);
    List<Vote> findByName(String name);
    List<Vote> findByAnswer(Answer answer);
    List<Vote> findByDateBefore(LocalDateTime date);
    void addVote(Vote vote);
}
