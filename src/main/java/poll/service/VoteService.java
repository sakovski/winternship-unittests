package poll.service;

import poll.repository.Answer;
import poll.repository.IVoteRepository;

public class VoteService {

    private final IVoteRepository voteRepository;

    public VoteService(IVoteRepository voteRepository) {
        this.voteRepository = voteRepository;
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
