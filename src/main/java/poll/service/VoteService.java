package poll.service;

import poll.repository.Answer;
import poll.repository.IVoteRepository;
import poll.repository.Vote;

import java.util.List;
import java.util.stream.Collectors;

public class VoteService {

    private final IVoteRepository voteRepository;

    public VoteService(IVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote getVoteById(long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new VoteNotFoundException("Vote with id " + id + " not found"));
    }

    public Vote getOrCreateVote(long id) {
        return voteRepository.findById(id)
                .orElseGet(() -> Vote.empty(id));
    }

    public List<Vote> getAllYesVotesByName(String name) {
        return voteRepository.findByName(name)
                .stream()
                .filter(vote -> vote.getAnswer().equals(Answer.YES))
                .collect(Collectors.toList());
    }

    public boolean hasAnswerWonTheVote(Answer answer) {
        int allVotesForAnswerCount = countAllVotesOnAnswer(answer);
        int allVotes = voteRepository.findAll().size();

        return (float)((allVotesForAnswerCount * 100) / allVotes) > 50;
    }

    public int countAllVotesOnAnswer(Answer answer) {
        return voteRepository.findByAnswer(answer).size();
    }
}
