package com.ashrafunahid.retrofittutorial.BottomNavFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ashrafunahid.retrofittutorial.R;
import com.ashrafunahid.retrofittutorial.ResponseModel.FetchUsersResponse;
import com.ashrafunahid.retrofittutorial.ResponseModel.User;
import com.ashrafunahid.retrofittutorial.RetrofitClient;
import com.ashrafunahid.retrofittutorial.UserAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {

    List<User> userList;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.users_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Call<FetchUsersResponse> call = RetrofitClient.getInstance().getApi().fetchUsers();

        call.enqueue(new Callback<FetchUsersResponse>() {
            @Override
            public void onResponse(Call<FetchUsersResponse> call, Response<FetchUsersResponse> response) {

                if(response.isSuccessful()) {

                    userList = response.body().getUserList();
                    recyclerView.setAdapter(new UserAdapter(getActivity(), userList));

                }
                else {
                    Toast.makeText(getActivity(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FetchUsersResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}