package vo.Project.Task;

import java.util.ArrayList;

public class Task {          //个体任务

    String id;
    String name;
    double socre;//完成task将获得的分数
    double progress;//0-1的数值，标志完成进度
    boolean flag;//判断task是否已经被接受
    ArrayList<image> images;
    ArrayList<String> requests;


    public Task(){}

    public Task(String id,String name,double socre,double progress,boolean flag){
        images = new ArrayList<image>();
        requests = new ArrayList<String>();
        this.id = id;
        this.name = name;
        this.socre = socre;
        this.progress = progress;
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ArrayList<image> getImages() {
        return images;
    }

    public void setImages(ArrayList<image> images) {
        this.images = images;
    }

    public ArrayList<String> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }
}
