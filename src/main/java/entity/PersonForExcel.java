package entity;

/**
 * Created by lcc on 2018/10/9.
 */
public class PersonForExcel {
    private String name;
    private int age;
    private String desc;

    public PersonForExcel() {
       super();
    }

    public PersonForExcel(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toString() {
        return "[name=" + name + ",age=" + age + ",desc=" + desc + "]";
    }
}
