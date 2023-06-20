package com.ashrafunahid.retrofittutorial;

import com.ashrafunahid.retrofittutorial.ResponseModel.DeleteAccountResponse;
import com.ashrafunahid.retrofittutorial.ResponseModel.FetchUsersResponse;
import com.ashrafunahid.retrofittutorial.ResponseModel.LoginResponse;
import com.ashrafunahid.retrofittutorial.ResponseModel.RegisterResponse;
import com.ashrafunahid.retrofittutorial.ResponseModel.UpdateUserPasswordResponse;
import com.ashrafunahid.retrofittutorial.ResponseModel.UpdateUserProfileResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("fetchusers.php")
    Call<FetchUsersResponse> fetchUsers();

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<UpdateUserProfileResponse> updateUserProfile(
            @Field("id") int id,
            @Field("username") String username,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<UpdateUserPasswordResponse> updateUserPassword(
            @Field("email") String email,
            @Field("current") String currentPassword,
            @Field("new") String newPassword
    );

    @FormUrlEncoded
    @POST("deleteaccount.php")
    Call<DeleteAccountResponse> deleteUserAccount(
            @Field("id") int id
    );

}
