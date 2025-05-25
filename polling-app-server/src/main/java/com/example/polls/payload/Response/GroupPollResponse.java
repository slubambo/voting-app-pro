package com.example.polls.payload.Response;

public class GroupPollResponse {
    private PollResponse poll;

    private GroupSummaryResponse group;

    public GroupPollResponse(PollResponse poll, GroupSummaryResponse group) {
        this.poll = poll;
        this.group = group;
    }

    public GroupSummaryResponse getGroup() {
        return group;
    }

    public void setGroup(GroupSummaryResponse group) {
        this.group = group;
    }

    public PollResponse getPoll() {
        return poll;
    }

    public void setPoll(PollResponse poll) {
        this.poll = poll;
    }
}
