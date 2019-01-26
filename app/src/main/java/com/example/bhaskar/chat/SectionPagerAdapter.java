package com.example.bhaskar.chat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Requests r;
                r = new Requests();
                Log.d("req: ","executed");
                return r;
            case 1:
                Fds fds = new Fds();
                Log.d("fds: ","executed");
                return fds;

            case 2:
                Chat chat = new Chat();
                Log.d("cf: ","executed");
                return chat;

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int i) {
        switch (i) {
            case 0 :
                return "REQUESTS";

            case 1:
                return "FRIENDS";

            case 2:
                return "CHATS";

                default:
                    return null;
        }
    }
}
