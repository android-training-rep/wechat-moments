package com.example.wechatmoments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wechatmoments.model.Tweet;
import com.example.wechatmoments.model.User;
import com.example.wechatmoments.myadapter.TweetsAdapter;
import com.example.wechatmoments.repository.TweetRepository;
import com.example.wechatmoments.repository.UserRepository;
import com.example.wechatmoments.utils.HttpUtil;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getName();
    private HttpUtil httpUtil = new HttpUtil();
    private UserViewModel userViewModel;
    private TweetsViewModel tweetsViewModel;
    ImageView profileImage, avatar;
    TextView nick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImage = findViewById(R.id.profile_image);
        avatar = findViewById(R.id.avatar);
        nick = findViewById(R.id.nick);

        userViewModel = obtainUserViewModel();
        userViewModel.observeUser(this,new Observer<User>(){
            @Override
            public void onChanged(User user) {
                if (Objects.nonNull(user.getProfileImage())) {
                    Glide.with(profileImage).load(user.getProfileImage()).into(profileImage);
                }
                if (Objects.nonNull(user.getAvatar())) {
                    Glide.with(avatar).load(user.getAvatar()).into(avatar);
                }
                nick.setText(user.getNick());
            }
        });
        userViewModel.loadUserInfo();

        RecyclerView recyclerView = findViewById(R.id.tweets_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TweetsAdapter tweetsAdapter = new TweetsAdapter();
        recyclerView.setAdapter(tweetsAdapter);


        tweetsViewModel = obtainTweetsViewModel();
        tweetsViewModel.observeTweets(this, new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                System.out.println("------tweets size:------" + tweets.size());
                tweetsAdapter.setTweets(tweets);
            }
        });
        tweetsViewModel.loadTweets();

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.tweets_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里获取更多数据的逻辑
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private UserViewModel obtainUserViewModel() {
        UserRepository userRepository = new UserRepository(httpUtil);
        UserViewModel userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        userViewModel.setUserRepository(userRepository);
        return userViewModel;
    }

    private TweetsViewModel obtainTweetsViewModel() {
        TweetRepository tweetRepository = new TweetRepository(httpUtil);
        TweetsViewModel tweetsViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TweetsViewModel.class);
        tweetsViewModel.setTweetRepository(tweetRepository);
        return tweetsViewModel;
    }
}