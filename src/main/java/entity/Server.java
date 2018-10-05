package entity;

import lombok.Data;

/**
 * Created by lcc on 2018/10/3.
 */
@Data
public class Server {
    // 尝试次数
    private int times;

    public Server(int times) {
        this.times = times;
    }
}