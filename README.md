# project
* Day1 CRM项目介绍
  * CRM项目的简介 
  
    企业级应用，传统应用；给销售或者贸易型公司使用，在市场，销售，服务等各个环节中维护客户关系。
  * CRM的项目宗旨是
  
    增加新客户，留住老客户，把已有客户转换成忠诚客户
  * CRM项目的核心业务
    * 系统管理功能：不是直接业务处理数据，为了保证业务管理的功能正常安全运行而设计的功能。
      * 用户登录，安全退出，登录验证等。
    * 业务管理功能：处理业务数据
      * 市场活动：市场部，设计市场活动营销活动
      * 线索：销售部（初级销售）增加线索
      * 客户和联系人：销售部（高级销售），有效地区和跟踪客户和联系人
      * 交易：销售部（高级销售），更好地区分和统计交易的各个阶段
      * 销售回访：客服部，妥善安排售后回访。主动提醒。
      * 统计图表：管理层，统计交易表中的各个阶段数据量
* Day2 物理模型设计和搭建开发环境
  * 1、crm的表结构

    tbl_user   用户表

    tbl_dic_type   数据字典类型表
    tbl_dic_value  数据字典值

    tbl_activity   市场活动表
    tbl_activity_remark  市场活动备注表
  
    tbl_clue       线索表
    tbl_clue_remark   线索备注表
  
    tbl_clue_activity_relation  线索和市场活动的关联关系表
  
    tbl_customer   客户表
    tbl_customer_remark  客户备注表
  
    tbl_contacts   联系人表
    tbl_contacts_remark 联系人备注表
  
    tbl_contacts_activity_relation 联系人和市场活动的关联关系表
  
    tbl_tran       交易表
    tbl_tran_remark  交易备注表
    tbl_tran_history  交易历史表
  
    tbl_task   任务表
  * 2、搭建开发环境
    * 创建项目project并设置jdk为1.8
    * 创建工程crm并补全目录结构
    * 添加jar包，添加依赖pom.xml
    * 添加配置文件
    * 添加静态页面资源

## 10天免登录功能实现

用户勾选复选框，isRemPwd=true,后端接收，若为true，则向外发送cookie，前端接收cookie。



## 用户登录，安全退出

安全退出应该删除cookie,

## 登陆验证和创建市场活动

在进行系统业务的访问时，应该进行登录验证，验证是否存在cookie，即是用拦截器拦截请求

创建类实现HandlerInterceptor

```java
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandler(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Contacts.SESSION_USER);
        if (user == null) {
            // 用户为空，则跳转回登录界面
            response.sendRedirect(request.getContextPath());
           	return false;
        }
        return true;
    }
}
```

## 实现创建市场活动