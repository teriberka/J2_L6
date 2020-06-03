package ru.geekbrains.J2.L5.hometask;

public class MyThread extends Thread {

    float[] arr;

    public MyThread(String name,float[] arr)
    {
        super(name);
        this.arr = arr;
    }

    @Override
    public void run() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;

        }

        for (int i = 0; i < arr.length ; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
