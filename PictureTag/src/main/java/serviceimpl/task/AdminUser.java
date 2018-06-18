package serviceimpl.task;

import serviceimpl.FindProjects;
import serviceimpl.userserviceImpl;
import vo.UserInfo;

import java.util.ArrayList;

public class AdminUser {
          userserviceImpl impl=new userserviceImpl();
          //功能方法
          public ArrayList<UserInfo> getAll() {                              //用户数
              ArrayList<UserInfo> list = impl.getall();
              ArrayList<UserInfo> newList = new ArrayList<UserInfo>();
              for (UserInfo user : list) {
                  if (!user.getUsername().equals("admin")) {
                      newList.add(user);
                  }
              }
              return newList;
          }
          public ArrayList<String> getUserCountByMonth(String month){       //月度注册的用户数
              ArrayList<String> count=new ArrayList<String>();
              ArrayList<UserInfo> all=getAll();
              ArrayList<UserInfo> newlist=new ArrayList<UserInfo>();
              int MonthOfDay=checkMonth(month);
              for(UserInfo user:all){
                  if(user.getMonth().equals(month)){
                      newlist.add(user);
                  }
              }
              int counts[]={0,0,0,0,0,0};
              if(MonthOfDay==28||MonthOfDay==30) {
                  for (UserInfo user : newlist) {
                      int day = convertDay(user.getDay());
                      counts[(day - 1) / 5]++;
                  }
              }

              if(MonthOfDay==31){
                  for (UserInfo user : newlist) {
                      int day = convertDay(user.getDay());
                      if(day==31){
                          counts[5]++;
                      }
                      else {
                          counts[(day - 1) / 5]++;
                      }
                  }
              }
              for(int i=0;i<counts.length;i++){
                  String temp=""+counts[i];
                  count.add(temp);
              }
              return count;
          }

          public ArrayList<String> getUserCountByYear(String year){              //年度注册的用户数
              ArrayList<String> result=new ArrayList<String>();
              ArrayList<UserInfo> all=getAll();
              ArrayList<UserInfo> newlist=new ArrayList<UserInfo>();
              for(UserInfo user:all){
                  if(user.getDate().substring(0,4).equals(year)){
                      newlist.add(user);
                  }
              }
              int counts[]={0,0,0,0,0,0,0,0,0,0,0,0};
              for(UserInfo user:newlist){
                   counts[getMonthIndex(user.getMonth())]++;
              }
              for(int i=0;i<counts.length;i++){
                  String temp=""+counts[i];
                  result.add(temp);
              }
              return result;
          }

          public ArrayList<String> getProjectCountByMonth(String month){
              ArrayList<String> result=new ArrayList<String>();
              ArrayList<UserInfo> all=getAll();
              ArrayList<String> proIds=new ArrayList<String>();         //存放所有的proIds
              int MonthOfDay=checkMonth(month);
              for(UserInfo user:all){
                  if(user!=null) {
                      for (String str : user.getLaunchpro()) {
                          if (str != null) {
                              proIds.add(str);
                          }
                      }
                  }
              }
              FindProjects service=new FindProjects();
              int counts[]={0,0,0,0,0,0};
              if(MonthOfDay==28||MonthOfDay==30) {
                  for (String str : proIds) {
                      int day = convertDay(service.getProject(str).getDate().substring(6,8));
                      counts[(day - 1) / 5]++;
                  }
              }

              if(MonthOfDay==31){
                  for (String str : proIds) {
                      int day = convertDay(service.getProject(str).getDate().substring(6,8));
                      if(day==31){
                          counts[5]++;
                      }
                      else {
                          counts[(day - 1) / 5]++;
                      }
                  }
              }
              for(int i=0;i<counts.length;i++){
                  String temp=""+counts[i];
                  result.add(temp);
              }
              return result;
          }

          public ArrayList<String> getProjectCountByYear(String year){
              FindProjects service=new FindProjects();
              ArrayList<String> result=new ArrayList<String>();
              ArrayList<UserInfo> all=getAll();
              ArrayList<String> proIds=new ArrayList<String>();
              for(UserInfo user:all){
                    for(String str:user.getLaunchpro()){
                        if(service.getProject(str).getDate().substring(0,4).equals(year)) {
                            proIds.add(str);
                        }
                    }
              }
              int counts[]={0,0,0,0,0,0,0,0,0,0,0,0};
              for(String str:proIds){
                  counts[getMonthIndex(service.getProject(str).getDate().substring(4,6))]++;
              }
              for(int i=0;i<counts.length;i++){
                  String temp=""+counts[i];
                  result.add(temp);
              }
              return result;
          }
          public ArrayList<String> getTaskCountByLevel(){
              ArrayList<String> result=new ArrayList<String>();
              ArrayList<UserInfo> all=getAll();
              int counts[]={0,0,0,0,0,0};
              for(UserInfo user:all){
                  if(user!=null){
                      if(user.getLevel()==0){
                           counts[0]=counts[0]+user.getReceivetask().size();
                      }
                      else if(user.getLevel()==1){
                          counts[1]=counts[1]+user.getReceivetask().size();
                      }
                      else if(user.getLevel()==2){
                          counts[2]=counts[2]+user.getReceivetask().size();
                      }
                      else if(user.getLevel()==3){
                          counts[3]=counts[3]+user.getReceivetask().size();
                      }
                      else if(user.getLevel()==4){
                          counts[4]=counts[4]+user.getReceivetask().size();
                      }
                      else{
                          counts[4]=counts[4]+user.getReceivetask().size();
                      }
                  }
              }
              for(int i=0;i<counts.length;i++){
                  String temp=""+counts[i];
                  result.add(temp);
              }
              return result;
          }

          public ArrayList<String> getTaskCount(){
              ArrayList<String> result=new ArrayList<String>();
              ArrayList<UserInfo> list=getAll();
              int finish=0;
              int unfinish=0;
              int giveup=0;
              for(UserInfo user:list){
                  if(user!=null){
                      for(String str:user.getFinish().keySet()){
                          if(user.getFinish().get(str)){
                                finish++;
                          }
                          if(!user.getFinish().get(str)){
                                giveup++;
                          }
                      }
                      unfinish=unfinish+(user.getReceivetask().size()-user.getFinish().size());
                  }
              }
              result.add(""+finish);
              result.add(""+unfinish);
              result.add(""+giveup);
              return result;
          }
          //不同等级的满意度
          public ArrayList<String> getScoreByLevel(){
              ArrayList<UserInfo> user=getAll();
              ArrayList<String> result=new ArrayList<String>();
              double score0=0.0;
              int size0=0;
              double score1=0.0;
              int size1=0;
              double score2=0.0;
              int size2=0;
              double score3=0.0;
              int size3=0;
              double score4=0.0;
              int size4=0;
              for(UserInfo u:user){
                   if(u.getLevel()==0){
                       for(String str:u.getReceiveEvalu().keySet()){
                           score0=score0+u.getReceiveEvalu().get(str);
                           size0++;
                       }
                   }
                   else if(u.getLevel()==1){
                       for(String str:u.getReceiveEvalu().keySet()){
                           score1=score1+u.getReceiveEvalu().get(str);
                           size1++;
                       }
                   }
                   else if(u.getLevel()==2){
                       for(String str:u.getReceiveEvalu().keySet()){
                           score2=score2+u.getReceiveEvalu().get(str);
                           size2++;
                       }
                   }
                   else if(u.getLevel()==3){
                       for(String str:u.getReceiveEvalu().keySet()){
                           score3=score3+u.getReceiveEvalu().get(str);
                           size3++;
                       }
                   }
                   else{
                       for(String str:u.getReceiveEvalu().keySet()){
                           score4=score4+u.getReceiveEvalu().get(str);
                           size4++;
                       }
                   }
              }
              if(size0==0){
                  result.add("0");
              }
              else{
                  result.add(""+score0*1.0/size0);
              }
              if(size1==0){
                  result.add("0");
              }
              else{
                  result.add(""+score1*1.0/size1);
              }
              if(size2==0){
                  result.add("0");
              }
              else{
                  result.add(""+score2*1.0/size2);
              }
              if(size3==0){
                  result.add("0");
              }
              else{
                  result.add(""+score3*1.0/size3);
              }
              if(size4==0){
                  result.add("0");
              }
              else{
                  result.add(""+score4*1.0/size4);
              }
              return result;
          }

          public ArrayList<String> getAllNorm(){
               ArrayList<String> result=new ArrayList<String>();
               return result;
          }

          //辅助方法
          public int checkMonth(String month){            //返回该月的天数
              int day=0;
              if(month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||month.equals("08")||month.equals("10")||month.equals("12")){
                  day=31;
              }
              if(month.equals("04")||month.equals("06")||month.equals("09")||month.equals("11")){
                  day=30;
              }
              if(month.equals("02")){
                  day=28;
              }
              return day;
          }

          public int convertDay(String day){                         //将day转为整数
              int result=0;
              if(day.substring(0,1).equals("0")){
                  result=Integer.parseInt(day.substring(1,2));
              }
              else{
                  result=Integer.parseInt(day);
              }
              return result;
          }

          public int getMonthIndex(String month){
              int index=0;
              if(month.equals("01")){
                  index=0;
              }
              else if(month.equals("02")){
                  index=1;
              }
              else if(month.equals("03")){
                  index=2;
              }
              else if(month.equals("04")){
                  index=3;
              }
              else if(month.equals("05")){
                  index=4;
              }
              else if(month.equals("06")){
                  index=5;
              }
              else if(month.equals("07")){
                  index=6;
              }
              else if(month.equals("08")){
                  index=7;
              }
              else if(month.equals("09")){
                  index=8;
              }
              else if(month.equals("10")){
                  index=9;
              }
              else if(month.equals("11")){
                  index=10;
              }
              else{
                  index=11;
              }
              return index;
          }
}
