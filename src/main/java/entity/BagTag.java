package entity;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by lcc on 2018/10/3.
 */
@Data
public class BagTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private  String id;

    public BagTag() {
        this( UUID.randomUUID().toString() );
    }

    public BagTag(String id) {
        this.id = id;
    }
}