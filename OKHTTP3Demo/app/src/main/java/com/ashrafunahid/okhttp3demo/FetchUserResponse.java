package com.ashrafunahid.okhttp3demo;

import kotlin.jvm.internal.SerializedIr;

public class FetchUserResponse {

    Users users;
    String error;

    public FetchUserResponse(Users users, String error) {
        this.users = users;
        this.error = error;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
