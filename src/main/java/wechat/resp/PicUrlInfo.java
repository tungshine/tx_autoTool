package wechat.resp;

/**
 * @author 不言
 * @create 2018-10-29 19:48
 * @description:
 */
public class PicUrlInfo {

    private String title;

    private String pic;
    private String Url;
    private String Info;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}