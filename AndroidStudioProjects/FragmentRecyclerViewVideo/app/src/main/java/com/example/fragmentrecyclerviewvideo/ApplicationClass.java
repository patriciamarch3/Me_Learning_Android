package com.example.fragmentrecyclerviewvideo;

import android.app.Application;
import android.content.ContentProvider;
import android.os.Build;

import java.util.ArrayList;

// Application runs first, before main_activity; also the last to be destroyed
public class ApplicationClass extends Application {
    public static ArrayList<Person> people;
    public static int currentIndex;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     *
     * <p>Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.</p>
     *
     * <p>If you override this method, be sure to call {@code super.onCreate()}.</p>
     *
     * <p class="note">Be aware that direct boot may also affect callback order on
     * Android {@link Build.VERSION_CODES#N} and later devices.
     * Until the user unlocks the device, only direct boot aware components are
     * allowed to run. You should consider that all direct boot unaware
     * components, including such {@link ContentProvider}, are
     * disabled until user unlock happens, especially when component callback
     * order matters.</p>
     */
    @Override
    public void onCreate() {
        super.onCreate();

        people = new ArrayList<Person>();
        people.add(new Person("Mark Lee", "123"));
        people.add(new Person("Renjun Huang", "234"));
        people.add(new Person("Jeno Lee", "345"));
        people.add(new Person("Haechan Lee", "456"));
        people.add(new Person("Jaemin Na", "567"));
        people.add(new Person("Chenle Zhong", "678"));
        people.add(new Person("Jisung Park", "789"));

        currentIndex = 0;
    }

    public static int getCurrentIndex() {
        return currentIndex;
    }

    public static void setCurrentIndex(int currentIndex) {
        ApplicationClass.currentIndex = currentIndex;
    }
}
