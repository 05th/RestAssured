public class UserInfo {
    private String name;
    private String about;

    public UserInfo(String name, String about) {
        this.name = name;
        this.about = about;
    }

    public UserInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}