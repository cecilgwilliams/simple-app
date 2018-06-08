package home;

public class Story {

    private long id;
    private long projectId;
    private String name;
    private String info;


    public Story() {
    }

    public Story(long id, long projectId, String name, String info) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.info = info;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
