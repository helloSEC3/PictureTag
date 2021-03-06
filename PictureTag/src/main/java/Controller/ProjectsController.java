package Controller;
import serviceimpl.task.FindProjects;
import vo.Project.Project;
import vo.Project.Task.Task;

import java.util.ArrayList;

public class ProjectsController implements service.FindProjects {
    FindProjects impl=new FindProjects();
    public Project lauchPro(Project pro){

        return impl.lauchPro(pro);
    }
    public ArrayList<Project> getProjects(String username){

        return impl.getProjects(username);
    }
    public Project getProject(String proid){

        return impl.getProject(proid);
    }
    public ArrayList<Task> getTasks(String ProId){

        return impl.getTasks(ProId);
    }
    public ArrayList<Project> chooseProjectByDate(String Date1, String Date2, String username){

        return impl.chooseProjectByDate(Date1,Date2,username);
    }
    public void update(Project pro){

        impl.update(pro);
    }
    public void updateTaskId(String proId,String taskId){

        impl.updateTaskId(proId,taskId);
    }
    public void updateProgress(String proid){

        impl.updateProgress(proid);
    }
    public void updateList(String proid,String username,String taskId){

        impl.updateList(proid,username,taskId);
    }
}
