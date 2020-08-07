package com.example.wechatmoments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wechatmoments.model.User;
import com.example.wechatmoments.repository.UserRepository;
import com.example.wechatmoments.utils.HttpUtil;

public class MainActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getName();
    private HttpUtil httpUtil = new HttpUtil();
    private UserViewModel userViewModel;
    ImageView profileImage, avatar;
    TextView nick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImage = findViewById(R.id.profile_image);
        avatar = findViewById(R.id.avatar);
        nick = findViewById(R.id.nick);

        userViewModel = obtainViewModel();
        Observer userObserver = new Observer<User>(){
            @Override
            public void onChanged(User user) {
                System.out.println("-------------user:" + user.getNick());
                // todo profile-image 转换
                Glide.with(profileImage).load(user.getProfileImage()).into(profileImage);
                Glide.with(avatar).load(user.getAvatar()).into(avatar);
                nick.setText(user.getNick());
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