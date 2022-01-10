package poll.repository;

import java.time.LocalDateTime;

public class Vote {

    private final long id;
    private final String name;
    private final LocalDateTime date;
    private final Answer answer;

    public Vote(long id, String name, LocalDateTime date, Answer answer) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.answer = answer;
    }

    public static Vote empty(long id) {
        return new Vote(id, null, null, null);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Answer getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", answer=" + answer +
                '}';
    }
}
