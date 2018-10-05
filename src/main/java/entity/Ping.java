package entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by lcc on 2018/9/30.
 */
@Data
public class Ping {
    private int id;
    private int count;
    private long time;
    private Timestamp datetime;

    public Ping(int count, long time) {
        this.count = count;
        this.time = time;
    }

    public Ping(int count, Timestamp time) {
        this.count = count;
        this.datetime = time;
    }

    public Ping(int id, int count,Timestamp datetime) {
        this.id = id;
        this.count = count;
        this.datetime = datetime;
    }
}
