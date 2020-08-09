package com.example.wechatmoments;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.wechatmoments.model.User;
import com.example.wechatmoments.repository.UserRepository;
import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void should_show_nick_name_correct() {
        UserRepository userRepository = mock(UserRepository.class);
        User mockUser= new User();
        mockUser.setProfileImage("http://lorempixel.com/480/280/technics/6/");
        mockUser.setAvatar("https://s3.amazonaws.com/uifaces/faces/twitter/mahmoudmetwally/128.jpg");
        mockUser.setNick("John Smith");
        mockUser.setUsername("jsmith");

        Gson gson = new Gson();
        when(userRepository.loadUserInfo()).thenReturn(Observable.create(new ObservableOnSubscribe<String>(){

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(gson.toJson(mockUser));
            }
        })
        );

        // todo id nick multi, to choose correct view
        onView(withId(R.id.nick))
                .check(matches(withText(mockUser.getNick())));
    }
}