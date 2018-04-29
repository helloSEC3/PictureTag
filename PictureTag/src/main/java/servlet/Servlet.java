package servlet;

import com.google.gson.Gson;
import com.sun.deploy.net.HttpResponse;
import serviceimpl.dataAnalyze;
import serviceimpl.tagIO;
import serviceimpl.userserviceImpl;
import stub.userstub;
import vo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Servlet extends javax.servlet.http.HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request,response);//跳转到dopost执行
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//这个方法只对getPost()方法有用，详情见http://blog.csdn.net/joywy/article/details/8006645
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");//获取参数，当且仅当request中只有一个参数的时候有效
        if("newUser".equals(action)){
            String userData = request.getParameter("gData");
            this.newUser(request,response,userData);
        }
        else if("login".equals(action)){
            String userData = request.getParameter("gData");
            this.login(request,response,userData);
        }
        else if("deleteUser".equals(action)){
            String userId = request.getParameter("gData");
            this.deleteUser(request,response,userId);
        }
        else if("modifyUser".equals(action)){
            String userData = request.getParameter("gData");
            this.modifyUser(request,response,userData);
        }
        else if("receiveUserInfo".equals(action)){
            String userId = request.getParameter("gData");
            this.receiveUserInfo(request,response,userId);
        }
        else if("receiveUserDegree".equals(action)){
            String userId = request.getParameter("gData");
            this.receiveUserDegree(request,response,userId);
        }
        else if("writeTag".equals(action)){
            String picId = request.getParameter("gData");
            this.writeTag(request,response,picId);
        }
        else if("receiveTag".equals(action)){
            String picId = request.getParameter("gData");
            this.receiveTag(request,response,picId);
        }
        else if("modifyTag".equals(action)){
            String imgData = request.getParameter("gData");
            this.modifyTag(request,response,imgData);
        }
 /*       else if("receiveProjectInfo".equals(action)){
            String projectId = request.getParameter("gData");
            this.receiveProjectInfo(request,response,projectId);
        }
        else if("newProject".equals(action)){
            String projectData = request.getParameter("gData");
            this.newProject(request,response,projectData);
        }
        else if("modifyProject".equals(action)){
            String projectData = request.getParameter("gData");
            this.modifyProject(request,response,projectData);
        }
        else if("receiveProjects".equals(action)){
            String userId = request.getParameter("gData");
            this.receiveProjects(request,response,userId);
        }*/
        else if("newTask".equals(action)){
            String taskData = request.getParameter("gData");
            this.newTask(request,response,taskData);
        }
        else if("modifyTask".equals(action)){
            String taskData = request.getParameter("gData");
            this.modifyTask(request,response,taskData);
        }
        else if("receiveTaskContent".equals(action)){
            String taskId = request.getParameter("gData");
            this.receiveTaskContent(request,response,taskId);
        }
        else if("deleteTask".equals(action)){
            String taskId = request.getParameter("gData");
            this.deleteTask(request,response,taskId);
        }
        else if("commitTask".equals(action)){
            String taskId = request.getParameter("gData");
            this.commitTask(request,response,taskId);
        }
        else{
            System.out.println("no function like this");
        }
    }

    /**
     * 注册用户
     * @param request
     * @param response
     * @param userData
     */
    private void newUser(HttpServletRequest request,HttpServletResponse response,String userData){
        boolean result=false;
        Gson gson=new Gson();
        UserInfo user=gson.fromJson(userData,UserInfo.class);
        userserviceImpl impl=new userserviceImpl();
        result=impl.register(user.getUsername(),user.getPassword());                 //userdata到底是什么
        try {
            PrintWriter out = response.getWriter();       //写入字符,不知道界面的键值是什么
            String data = "false";
            if (result) {
                data = "true";
            }
            out.write(data);
            out.close();
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    private void login(HttpServletRequest request,HttpServletResponse response,String userData){     //这里会不会有问题
        boolean result=false;
        Gson gson=new Gson();
        UserInfo user=gson.fromJson(userData,UserInfo.class);
        userserviceImpl impl=new userserviceImpl();
        result=impl.login(user.getUsername(),user.getPassword());
        try {
            PrintWriter out = response.getWriter();       //写入字符,不知道界面的键值是什么
            String data = "false";
            if (result) {
                data = "true";
            }
            out.write(data);
            out.close();
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    /**
     * 修改用户数据
     * @param request
     * @param response
     * @param userData
     */
    private void modifyUser(HttpServletRequest request,HttpServletResponse response,String userData){

    }

    /**
     * 通过id获取用户信息
     * @param request
     * @param response
     * @param userId
     */
    private void receiveUserInfo(HttpServletRequest request,HttpServletResponse response,String userId){
        UserInfo user=new UserInfo();
        userserviceImpl impl=new userserviceImpl();
        user=impl.getUser(userId);
        try {
            PrintWriter out = response.getWriter();       //写入字符,不知道界面的键值是什么
            Gson gson=new Gson();
            String result=gson.toJson(user);
            out.write(result);
            out.close();
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    /**优先已完成
     * 根据用户id获得用户的：积分奖励，群体排名，等级
     * @param request
     * @param response
     * @param userId
     */
    private void receiveUserDegree(HttpServletRequest request,HttpServletResponse response,String userId){
        dataAnalyze d = new dataAnalyze();
        String out = d.receiveUserDegree(userId);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(out);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据用户id获得所有任务(任务id+基本信息+参与者+完成进度)，已完成任的任务(任务id+基本信息+参与者+完成进度)，未完成任务(任务id+基本信息+参与者+完成进度)
     * @param request
     * @param response
     * @param userId
     */
    private  void receiveUserTask(HttpServletRequest request,HttpServletResponse response,String userId){

    }

    /**
     * 通过id删除用户
     * @param request
     * @param response
     * @param userId
     */
    private void deleteUser(HttpServletRequest request,HttpServletResponse response,String userId){
        boolean result=false;
        userserviceImpl impl=new userserviceImpl();
        result=impl.delete(userId);
        try {
            PrintWriter out = response.getWriter();         //写入字符,不知道界面的键值是什么
            String data = "false";
            if (result) {
                data = "true";
            }
            out.write(data);
            out.close();
        }catch(IOException io){
            io.printStackTrace();
        }
    }

    /**
     *  提供对单一图片的查询功能
     * @param request
     * @param response
     * @param s
     * @throws IOException
     */
    private void receiveTag(HttpServletRequest request,HttpServletResponse response,String s) throws IOException {
        String reqStr = s;
        tagIO t = new tagIO();
        String image = t.receiveTag(reqStr);
        try {
            PrintWriter out = response.getWriter();
            out.write(image);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeTag(HttpServletRequest request,HttpServletResponse response,String s){
        tagIO t = new tagIO();
        String reqStr = s;
        boolean flag = t.writeTag(reqStr);
    }

    /**
     * 修改对图片的标注内容
     * @param request
     * @param response
     */
    private void modifyTag(HttpServletRequest request,HttpServletResponse response,String imgData){
        tagIO t = new tagIO();
        t.modifyTag(imgData);
    }

    /**
     *暂无
     * @param request
     * @param response
     */
    private void receiveProjectInfo(HttpServletRequest request,HttpServletResponse response,String projectId){

    }

    /**
     * 暂无
     * 新增project，输入为project对象的完整信息
     * @param request
     * @param response
     */
    private  void newProject(HttpServletRequest request,HttpServletResponse response,String projectData){

    }

    /**
     * 暂无
     * 修改project，输入为project对象的完整信息
     * @param request
     * @param response
     * @param projectData
     */
    private void modifyProject(HttpServletRequest request,HttpServletResponse response,String projectData){

    }

    /**
     * 暂无
     * 王灿灿
     * 获得user下所有projects的info，包含projects的id列表和该project所有的taskId
     * @param request
     * @param response
     * @param userId
     */
    private  void receiveProjects(HttpServletRequest request,HttpServletResponse response,String userId){

    }

    /**
     * 暂无
     * @param request
     * @param response
     * @param projectId
     */
    private  void deleteProject(HttpServletRequest request,HttpServletResponse response,String projectId){

    }

    private void newTask(HttpServletRequest request,HttpServletResponse response,String taskData){
        dataAnalyze d = new dataAnalyze();
        d.newTask(taskData);
    }

    private void modifyTask(HttpServletRequest request,HttpServletResponse response,String taskData){
        dataAnalyze d = new dataAnalyze();
        d.modifyTask(taskData);
    }

    /**优先
     * 王灿灿
     * 根据任务(Task)id获得：标注人，图片列表(图片的ids)，图片的url，每张图片的标注内容
     * @param request
     * @param response
     * @param taskId
     */
    private void receiveTaskContent(HttpServletRequest request,HttpServletResponse response,String taskId){
        dataAnalyze d = new dataAnalyze();
        String taskInfo = d.receiveTaskInfo(taskId);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(taskInfo);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**王灿灿
     * 传递任务(Task)的id给后台，可以删除该任务
     * @param request
     * @param response
     * @param taskId
     */
    private void deleteTask(HttpServletRequest request,HttpServletResponse response,String taskId){
        dataAnalyze d = new dataAnalyze();
        d.deleteTask(taskId);
    }

    private void commitTask(HttpServletRequest request, HttpServletResponse response,String taskId){
        dataAnalyze d = new dataAnalyze();
        d.commitTask(taskId);
    }

}
