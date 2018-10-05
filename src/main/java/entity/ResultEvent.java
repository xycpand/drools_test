package entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcc on 2018/10/3.
 */
@Data
public class ResultEvent {
    private List<String> events = new ArrayList<>();
}