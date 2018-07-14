package com.jyss.ziwei.action;

import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.filter.MySessionContext;
import com.jyss.ziwei.service.AccountUserService;
import com.jyss.ziwei.service.MobileLoginService;
import com.jyss.ziwei.service.UserService;
import com.jyss.ziwei.utils.CommTool;
import com.jyss.ziwei.utils.HttpClientUtil;
import com.jyss.ziwei.utils.PasswordUtil;
import com.jyss.ziwei.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserAction {

    @Autowired
    private UserService userService;
    @Autowired
    private MobileLoginService mobileLoginService;
    @Autowired
    private AccountUserService accountUserService;


    /**
     * 发送验证码
     */
    @RequestMapping("/sendCode")
    @ResponseBody
    public ResponseResult sendCode(@RequestParam("telephone") String telephone, HttpServletRequest request) {
        if(!StringUtils.isEmpty(telephone)){
            //发送验证码
            Map<String, String> m = new HashMap<String, String>();
            String code = CommTool.getYzm();
            //添加到session
            HttpSession session = request.getSession();
            String sessionId = "";
            sessionId = session.getId();
            session.removeAttribute("code");
            session.setAttribute("code",code);
            session.removeAttribute("tel");
            session.setAttribute("tel",telephone);
            //设置过期时间
            session.setMaxInactiveInterval(10 * 60);
            //发送短信
            String msgDo = HttpClientUtil.MsgDo(telephone,
                    "您此次操作的验证码为：" + code + "，请尽快在10分钟内完成验证。");
            m.put("sessionId", sessionId);

            if (msgDo.equals("1")) {
                return ResponseResult.ok(m);
            }
            return ResponseResult.error("-1","操作失败！");

        }
        return ResponseResult.error("-2","手机号不能为空！");
    }


    /**
     * 短信验证码校验
     */
    private ResponseResult validationCode(@RequestParam("tel") String tel,@RequestParam("code") String code,
                                          @RequestParam("sessionId") String sessionId){
        if(StringUtils.isEmpty(sessionId)){
            return ResponseResult.error("-1","请重新获取验证码！");
        }
        HttpSession session = MySessionContext.getSession(sessionId);
        if (session == null){
            return ResponseResult.error("-1","请重新获取验证码！");
        }
        String checkTel = (String) session.getAttribute("tel");
        String checkCode = (String) session.getAttribute("code");

        if(tel.equals(checkTel) && code.equals(checkCode)){
            return ResponseResult.ok(new HashMap<>());
        }
        return ResponseResult.error("-2","验证码不正确！");
    }


    /**
     * 注册，校验验证码
     */
    @RequestMapping("/regist")
    @ResponseBody
    public ResponseResult insertUser(@RequestParam("telephone") String telephone,@RequestParam("code") String code,
                                     @RequestParam("password") String password,@RequestParam("sessionId") String sessionId){
        if(password == null || password.length() < 6){
            return ResponseResult.error("-3","密码不能低于6位！");
        }
        ResponseResult result = validationCode(telephone, code, sessionId);
        if(result.getStatus() == 1){
            List<User> users = userService.selectUserByTel(null, telephone);
            if(users != null && users.size() > 0 ){
                return ResponseResult.error("-4","该手机号已经注册！");
            }
            result = userService.insertUser(telephone, password);
            return result;
        }
        return result;
    }


    /**
     * 验证码登陆
     */
    @RequestMapping("/yzlogin")
    @ResponseBody
    public ResponseResult selectUser(@RequestParam("telephone") String telephone,@RequestParam("code") String code,
                                     @RequestParam("sessionId") String sessionId){

        ResponseResult result = validationCode(telephone, code, sessionId);
        if(result.getStatus() == 1){
            result = userService.selectUser(telephone, null);
            return result;
        }
        return result;
    }



    /**
     * 密码登陆
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResponseResult userLogin(@RequestParam("telephone") String telephone,
                                    @RequestParam("password") String password){
        if(StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)){
            return ResponseResult.error("-1","用户名或密码不能为空！");
        }
        ResponseResult result = userService.selectUser(telephone, password);
        return result;
    }



    /**
     * 短信修改密码
     */
    @RequestMapping("/updatePwd")
    @ResponseBody
    public ResponseResult updatePwd(@RequestParam("telephone") String telephone,@RequestParam("newPwd") String newPwd,
                                    @RequestParam("code") String code,@RequestParam("sessionId") String sessionId){

        if(newPwd == null || newPwd.length() < 6){
            return ResponseResult.error("-3","密码不能低于6位！");
        }
        ResponseResult result = validationCode(telephone, code, sessionId);
        if(result.getStatus() == 1){
            String salt = CommTool.getSalt();
            newPwd = PasswordUtil.generate(newPwd, salt);
            List<User> users = userService.selectUserByTel(null, telephone);
            if(users != null && users.size() == 1){
                User user = new User();
                user.setPassword(newPwd);
                user.setSalt(salt);
                user.setuId(users.get(0).getuId());
                int count = userService.updateByPrimaryKey(user);
                if(count == 1){
                    return ResponseResult.ok(new HashMap<>());
                }
                return ResponseResult.error("-4","修改失败！");
            }
            return ResponseResult.error("-5","用户异常！");
        }
        return result;
    }



    /**
     * 密码修改密码
     */
    @RequestMapping("/upPassword")
    @ResponseBody
    public ResponseResult updatePassword(@RequestParam("password") String password,@RequestParam("newPwd") String newPwd,
                                         @RequestParam("token") String token){
        if(newPwd == null || newPwd.length() < 6){
            return ResponseResult.error("-3","密码不能低于6位！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            List<User> userList = userService.selectUserByTel(uId, null);
            if(userList != null && userList.size()==1){
                User user = userList.get(0);
                if(PasswordUtil.generate(password, user.getSalt()).equals(user.getPassword())){
                    String salt = CommTool.getSalt();
                    newPwd = PasswordUtil.generate(newPwd, salt);
                    User user1 = new User();
                    user1.setuId(user.getuId());
                    user1.setPassword(newPwd);
                    user1.setSalt(salt);
                    int count = userService.updateByPrimaryKey(user1);
                    if(count == 1){
                        return ResponseResult.ok(new HashMap<>());
                    }
                    return ResponseResult.error("-4","修改失败！");
                }
                return ResponseResult.error("-1","原密码不正确！");
            }
        }
        return ResponseResult.error("1","token失效！");

    }


    /**
     * 获取用户信息
     */
    @RequestMapping("/info")
    @ResponseBody
    public ResponseResult getUserByToken(@RequestParam("token") String token){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            List<User> users = userService.selectUserByTel(uId, null);
            if(users != null && users.size() == 1){
                User user = users.get(0);
                user.setPassword(null);
                user.setSalt(null);
                Map<String, Object> map = new HashMap<>();
                map.put("user",user);
                return ResponseResult.ok(map);
            }
        }
        return ResponseResult.error("1","token失效！");

    }


    /**
     * 修改用户信息
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResponseResult updateUserInfo(User user,@RequestParam("token") String token){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            user.setuId(uId);
            int count = userService.updateByPrimaryKey(user);
            if(count == 1){
                return ResponseResult.ok(new HashMap<>());
            }
            return ResponseResult.error("-1","修改失败");

        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 图片上传
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public ResponseResult uploadPic(MultipartFile pic, HttpServletRequest request) {
        //图片不能为空
        if(pic.isEmpty()){
            return ResponseResult.error("-2","图片不能为空！");
        }
        String filePath = request.getSession().getServletContext().getRealPath("/");
        int index = filePath.indexOf("ZIWEI");

        String filename = pic.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."));

        filePath = filePath.substring(0, index) + "uploadHeadPic" + "/";
        String imgPath = filePath + System.currentTimeMillis() + CommTool.getYzm() + extName;
        if (Utils.saveUpload(pic, imgPath)) {
            String path = imgPath.substring(imgPath.indexOf("uploadHeadPic"));
            return ResponseResult.ok(path);
        }
        return ResponseResult.error("-1","图片上传失败！");
    }


    /**
     * 修改头像
     */
    @RequestMapping("/upavatar")
    @ResponseBody
    public ResponseResult updateUserAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request,
                                           @RequestParam("token") String token){
        //图片不能为空
        if(avatar.isEmpty()){
            return ResponseResult.error("-2","图片不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            String filePath = request.getSession().getServletContext().getRealPath("/");
            int index = filePath.indexOf("ZIWEI");

            String filename = avatar.getOriginalFilename();
            String extName = filename.substring(filename.lastIndexOf("."));

            filePath = filePath.substring(0, index) + "uploadHeadPic" + "/";
            String imgPath = filePath + System.currentTimeMillis() + uId + extName;
            if (Utils.saveUpload(avatar, imgPath)) {
                String path = imgPath.substring(imgPath.indexOf("uploadHeadPic"));
                User user = new User();
                user.setuId(uId);
                user.setHeadpic(path);
                int count = userService.updateByPrimaryKey(user);
                if(count == 1){
                    return ResponseResult.ok(new HashMap<>());
                }
                return ResponseResult.error("-1","修改失败！");
            }
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 用户退出
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ResponseResult userLogout(@RequestParam("token") String token){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            //记录登陆信息
            String newToken = CommTool.getUUID();
            MobileLogin login = new MobileLogin();
            login.setuId(uId);
            login.setToken(newToken);
            login.setLastAccessTime(System.currentTimeMillis());
            login.setStatus(1);
            login.setCreatedAt(new Date());
            int count = mobileLoginService.insert(login);
            if(count == 1){
                return ResponseResult.ok(new HashMap<>());
            }
            return ResponseResult.error("-1","退出失败！");
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 查询用户目标
     */
    @RequestMapping("/target")
    @ResponseBody
    public ResponseResult selectUserTarget(@RequestParam("token") String token){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            ResponseResult result = userService.selectUserTarget(uId);
            return result;

        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 视频历史
     */
    @RequestMapping("/vediolist")
    @ResponseBody
    public ResponseResult selectUserListen(@RequestParam("token") String token,
                                           @RequestParam(value = "page", required = true) Integer page,
                                           @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            ResponseResult result = userService.selectUserListen(uId, page, pageSize);
            return result;
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 播放视频
     */
    @RequestMapping("/upvedio")
    @ResponseBody
    public ResponseResult selectUserListen(@RequestParam("token") String token, @RequestParam("knowId") Integer knowId,
                                           @RequestParam("listenTime") String listenTime){
        if(StringUtils.isEmpty(knowId) || StringUtils.isEmpty(listenTime)){
            return ResponseResult.error("-2","播放时长不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            UserListenRecord userListenRecord = new UserListenRecord();
            userListenRecord.setuId(uId);
            userListenRecord.setKnowId(knowId);
            userListenRecord.setListenTime(listenTime);
            userListenRecord.setStatus(1);
            userListenRecord.setCreateTime(new Date());
            userListenRecord.setModifyTime(new Date());
            ResponseResult result = userService.updateUserListen(userListenRecord);
            return result;
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 用户积分明细
     */
    @RequestMapping("/integral")
    @ResponseBody
    public ResponseResult selectUserPoints(@RequestParam("token") String token,
                                           @RequestParam(value = "page", required = true) Integer page,
                                           @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            ResponseResult result = userService.selectUserPoints(uId, page, pageSize);
            return result;
        }
        return ResponseResult.error("1","token失效！");
    }



    /////////////后台///////////////
    /**
     * 系统管理员查询所有用户
     */
    @RequestMapping("/alluser")
    @ResponseBody
    public ResponseEntity selectAllUser(@RequestParam("token") String token,
                                        @RequestParam(value = "page", required = true) Integer page,
                                        @RequestParam(value = "pageSize", required = true) Integer pageSize){

        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            return userService.selectAllUser(null, page, pageSize);
        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 健康管理师查询所有用户
     */
    @RequestMapping("/users")
    @ResponseBody
    public ResponseEntity selectAllUserBy(@RequestParam("token") String token,
                                          @RequestParam(value = "page", required = true) Integer page,
                                          @RequestParam(value = "pageSize", required = true) Integer pageSize){

        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            return userService.selectAllUser(uId, page, pageSize);
        }
        return ResponseEntity.op("0","token失效！");
    }


    /**
     * 条件搜索用户
     */
    @RequestMapping("/search")
    @ResponseBody
    public ResponseEntity selectLikeUser(@RequestParam("token") String token,@RequestParam("telephone") String telephone){
        if(telephone.length() < 6){
            return ResponseEntity.op("-1","请至少输入6位数字！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            AccountUser accountUser = accountUserService.getAuBy(null, uId + "");
            if(accountUser.getRoleId() == 3){
                return userService.selectLikeUser(telephone,uId);
            }
            return userService.selectLikeUser(telephone,null);
        }
        return ResponseEntity.op("0","token失效！");
    }


    /**
     * 查询用户目标
     */
    @RequestMapping("/htarget")
    @ResponseBody
    public ResponseResult getUserTarget(@RequestParam("token") String token, @RequestParam("uId")Long uId){
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            return userService.selectUserTarget(uId);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 插入或更新目标
     */
    @RequestMapping("/uptarget")
    @ResponseBody
    public ResponseEntity updateUserTarget(@RequestParam("token") String token,UserTarget userTarget){
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return userService.updateUserTarget(userTarget);
        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 系统管理员分配管理师
     */
    @RequestMapping("/manage")
    @ResponseBody
    public ResponseEntity updateUserManageId(@RequestParam("token") String token, @RequestParam("uId")Long uId,
                                             @RequestParam("manageId") Long manageId){
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long userId = mobileLogin.getuId();
            User user = new User();
            user.setuId(uId);
            user.setManageId(manageId);
            int count = userService.updateByPrimaryKey(user);
            if(count == 1){
                return ResponseEntity.se("");
            }
            return ResponseEntity.op("-1","操作失败！");
        }
        return ResponseEntity.op("0","token失效！");
    }




}
