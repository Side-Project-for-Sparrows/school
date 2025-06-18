package com.sparrows.school.kafka.payload.user;

import java.util.List;

public class UserUpdatedPayload{
    boolean isAdded;
    private List<Integer> boardIds;
    public int schoolId;
}
