package entity;

import lombok.Data;

/**
 * Created by lcc on 2018/9/28.
 */
@Data
public class Person {
    private String name ;
    private int count;

    public Person(String name, int count) {
        this.name = name;
        this.count = count;
    }
}
