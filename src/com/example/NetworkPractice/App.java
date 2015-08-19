package com.example.NetworkPractice;

/**
 * Created by cl on 2015/8/19.
 */
public class App {
    public App() {
    }

    @Override
    public String toString() {
        return "App{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", id=" + id +
                '}';
    }

    public App(String name, String age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getage() {
        return age;
    }

    public void setage(String age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String age;
    private int id;


}
