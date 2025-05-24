package com.example.polls.payload.Request;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class GroupPollRequest {
    @NotNull
    @Valid
    private PollRequest poll;

    public PollRequest getPoll() {
        return poll;
    }

    public void setPoll(PollRequest poll) {
        this.poll = poll;
    }
}
