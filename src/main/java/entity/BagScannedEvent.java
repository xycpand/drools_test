package entity;

import lombok.Data;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lcc on 2018/10/3.
 */
@Data
@Role(Role.Type.EVENT)
@Timestamp("timestamp")
@Expires("1d")
public class BagScannedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;

    private Date timestamp;

    private final BagTag bagTag;

    private final Location location;

    private final double weight;

    public BagScannedEvent(BagTag bagTag, Location location, double weight) {
        this(bagTag, location, weight, new Date());
    }

    public BagScannedEvent(BagTag bagTag, Location location, double weight, Date eventTimestamp) {
        this(UUID.randomUUID().toString(), bagTag, location, weight, eventTimestamp);
    }

    public BagScannedEvent(String id, BagTag bagTag, Location location, double weight, Date eventTimestamp) {
        this.id = id;
        this.bagTag = bagTag;
        this.location = location;
        this.weight = weight;
        this.timestamp = eventTimestamp;
    }

    public Location getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public BagTag getBagTag() {
        return bagTag;
    }

    public double getWeight() {
        return weight;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date eventTimestamp) {
        this.timestamp = eventTimestamp;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Event:{").append("id: " + id).append("， bagTag: " + bagTag).append(", timestamp: " + timestamp).append(", location: " + location).append("}").toString();
    }

}
