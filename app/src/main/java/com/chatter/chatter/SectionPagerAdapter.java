package com.chatter.chatter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(final int position) {
        switch (position){
            case 0:
                final RequestsFragment requestsFragment = new RequestsFragment();
                return requestsFragment;
            case 1:
                final ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            case 2:
                final FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;
            default:
                throw new IllegalArgumentException("unsupported fragment position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    public CharSequence getPageTitle(final int position){
        switch (position){
            case 0:
                return "Requests";
            case 1:
                return "Chat";
            case 2:
                return "Friends";
            default:
                throw new IllegalArgumentException("unsupported fragment position: " + position);
        }
    }
}
