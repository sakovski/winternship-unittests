package poll.controller;

import poll.repository.Answer;
import poll.repository.VoteRepository;
import poll.service.VoteService;

public class VoteController {
    public static void main(String... args) {
        VoteService voteService = new VoteService(new VoteRepository());
        System.out.println(voteService.hasAnswerWonTheVote(Answer.NO));
        System.out.println(voteService.hasAnswerWonTheVote(Answer.YES));
        System.out.println(voteService.countAllVotesOnAnswer(Answer.NO));
        System.out.println(voteService.countAllVotesOnAnswer(Answer.YES));
        System.out.println(voteService.getAllYesVotesByName("Ngapeth"));
        System.out.println(voteService.getAllYesVotesByName("Earvin Ngapeth"));
        System.out.println(voteService.getVoteById(1l));
        System.out.println(voteService.getOrCreateVote(10l));
    }
}
