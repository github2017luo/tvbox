package cn.a88sv.cn.bean;

public class Channels {

    private String channelsname;
    private String channelsurl;
    private String chanelsImageurl;

    public Channels(String channelsname, String channelsurl, String chanelsImageurl){
        this.channelsname=channelsname;
        this.channelsurl=channelsurl;
        this.chanelsImageurl=chanelsImageurl;
    }
    public String getChannelsname() {
        return channelsname;
    }

    public void setChannelsname(String channelsname) {
        this.channelsname = channelsname;
    }

    public String getChannelsurl() {
        return channelsurl;
    }

    public void setChannelsurl(String channelsurl) {
        this.channelsurl = channelsurl;
    }

    public String getChanelsImageurl() {
        return chanelsImageurl;
    }

    public void setChanelsImageurl(String chanelsImageurl) {
        this.chanelsImageurl = chanelsImageurl;
    }


}
