package org.example.restaurant.dto;

import java.util.List;

public class UserLIstForm {

    private List<UserResponse>userResponses;

    public List<UserResponse> getUserResponses() {
        return userResponses;
    }

    public void setUserResponses(List<UserResponse> userResponses) {
        this.userResponses = userResponses;
    }

}
