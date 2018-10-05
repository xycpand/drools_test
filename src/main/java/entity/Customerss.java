package entity;

import lombok.Data;

/**
 * Created by lcc on 2018/9/29.
 */
@Data
public  class Customerss {

    public String name = "";
    public int money = 0;
    public int emptyBottle = 0;
    public int drinkBottleSum = 0;

    public Customerss(String name, int money, int emptyBottle,
                      int drinkBottleSum) {
        super();
        this.name = name;
        this.money = money;
        this.emptyBottle = emptyBottle;
        this.drinkBottleSum = drinkBottleSum;
    }


}