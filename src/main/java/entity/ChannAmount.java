package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by lcc on 2018/10/4.
 */
public  class ChannAmount implements Serializable {
    private String channel;
    private int amount;
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void hbaseSave(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        Date date = new Date();
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("DEAL_CHANNEL",channel);
        map.put("RISK_TYPE","1");
        map.put("RISK_LEVEL","5");
        map.put("WARN_TIME",sdf.format(date));
        map.put("SUGGESTION","Drools规则实现：该渠道最近5分钟交易金额为"+amount+"超过10000元，请密切监控");
//        HbaseUtil.insertRow("operateMonitor","amount_monitor_"+channel,"cf",map);

    }
}

