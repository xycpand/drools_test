package entity;

import lombok.Data;

/**
 * Created by lcc on 2018/9/28.
 */
@Data
public class Person {
    private int id;
    private String name ;
    private int count;


    public Person() {
    }

    public Person(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public Person(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }
}
