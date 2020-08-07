package com.example.wechatmoments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import com.example.wechatmoments.model.User;
import com.example.wechatmoments.repository.UserRepository;
import com.example.wechatmoments.utils.HttpUtil;

public class MainActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getName();
    private HttpUtil httpUtil = new HttpUtil();
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = obtainViewModel();
        Observer userObserver = new Observer<User>(){
            @Override
            public void onChanged(User user) {
                // todo 把user绑定到界面
                System.out.println("-------------user:" + user.getUsername());
            }
        };
        userViewModel.getUser().observe(this, userObserver);

        userViewModel.loadUserInfo();
    }

    private UserViewModel obtainViewModel() {
        UserRepository userRepository = new UserRepository(httpUtil);
        UserViewModel userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.setUserRepository(userRepository);
        return userViewModel;
    }
}