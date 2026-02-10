package com.clashroyale.api.model;

public abstract class GameEntity implements Validatable {
    protected int id;
    protected String name;

    //конструктор - метод для того чтобы задать начальные значения полям
    public GameEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract String getType();
    public String getBasicInfo() {
        return "ID: " + id + ", Name: " + name + ", Type: " + getType();
    }

    //getteri - метод для получения инф
    public int getId() { return id; }
    public String getName() { return name; }

    //setteri - метод для задания значений
    public void setId(int id) { this.id = id; }
    public void setName (String name) { this.name = name; }
}
