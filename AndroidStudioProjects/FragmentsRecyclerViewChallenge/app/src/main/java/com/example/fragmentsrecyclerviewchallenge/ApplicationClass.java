package com.example.fragmentsrecyclerviewchallenge;

import android.app.Application;

import java.util.ArrayList;

public class ApplicationClass extends Application {
    public static ArrayList<Car> carList;
    public static int currentIndex;
    public static boolean showCar;

    @Override
    public void onCreate() {
        super.onCreate();

        carList = new ArrayList<>();
        carList.add(new Car("mercedes", "E200", "Renjun Huang", "123"));
        carList.add(new Car("mercedes", "E180", "Jeno Lee", "234"));
        carList.add(new Car("volkswagen", "Polo", "Haechan Lee", "345"));
        carList.add(new Car("nissan", "Almera", "Jaemin Na", "456"));
        carList.add(new Car("mercedes", "E200", "Chenle Zhong", "567"));
        carList.add(new Car("mercedes", "E180", "Jisung Park", "678"));

        currentIndex = 0;
        showCar = true;
    }

    public static int getCurrentIndex() {
        return currentIndex;
    }

    public static void setCurrentIndex(int currentIndex) {
        ApplicationClass.currentIndex = currentIndex;
    }

    public static boolean isShowCar() {
        return showCar;
    }

    public static void setShowCar(boolean showCar) {
        ApplicationClass.showCar = showCar;
    }
}
