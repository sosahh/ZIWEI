package com.jyss.ziwei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.mapper.*;
import com.jyss.ziwei.service.UserService;
import com.jyss.ziwei.utils.CommTool;
import com.jyss.ziwei.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MobileLoginMapper mobileLoginMapper;
    @Autowired
    private UserTargetMapper userTargetMapper;
    @Autowired
    private UserListenRecordMapper userListenMapper;
    @Autowired
    private UserPointsRecordMapper userPointsMapper;


    @Override
    public List<User> selectUserByTel(Long uId, String telephone) {
        return userMapper.selectUserByTel(uId,telephone);
    }


    /**
     * 用户注册
     */
    @Override
    public ResponseResult insertUser(String telephone, String password) {
        String salt = CommTool.getSalt();
        String pwd = PasswordUtil.generate(password, salt);
        String code = CommTool.getYzm();

        User user = new User();
        user.setTelephone(telephone);
        user.setNickname("user" + code);
        user.setPassword(pwd);
        user.setSalt(salt);
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //记录登陆信息
        String token = CommTool.getUUID();
        MobileLogin mobileLogin = new MobileLogin();
        mobileLogin.setuId(user.getuId());
        mobileLogin.setToken(token);
        mobileLogin.setLastAccessTime(System.currentTimeMillis());
        mobileLogin.setStatus(1);
        mobileLogin.setCreatedAt(new Date());
        int count = mobileLoginMapper.insert(mobileLogin);
        if(count == 1){
            Map<String, String> map = new HashMap<>();
            map.put("token",token);
            return ResponseResult.ok(map);
        }
        return ResponseResult.error("-5","注册失败！");
    }


    /**
     * 用户登陆
     */
    @Override
    public ResponseResult selectUser(String telephone, String password) {
        List<User> users = userMapper.selectUserByTel(null, telephone);
        if(users != null && users.size() == 1){
            User user = users.get(0);
            if(!StringUtils.isEmpty(password)){
                if(!PasswordUtil.generate(password,user.getSalt()).equals(user.getPassword())){
                    return ResponseResult.error("-2","密码错误！");
                }
            }
            //记录登陆信息
            String token = CommTool.getUUID();
            MobileLogin mobileLogin = new MobileLogin();
            mobileLogin.setuId(user.getuId());
            mobileLogin.setToken(token);
            mobileLogin.setLastAccessTime(System.currentTimeMillis());
            mobileLogin.setStatus(1);
            mobileLogin.setCreatedAt(new Date());
            int count = mobileLoginMapper.insert(mobileLogin);
            if(count == 1){
                Map<String, String> map = new HashMap<>();
                map.put("token",token);
                return ResponseResult.ok(map);
            }
            return ResponseResult.error("-3","登陆失败！");
        }
        return ResponseResult.error("-4","用户异常！");
    }


    /**
     * 更新用户
     */
    @Override
    public int updateByPrimaryKey(User user) {
        return userMapper.updateByPrimaryKey(user);
    }


    /**
     * 查询用户目标
     */
    @Override
    public ResponseResult selectUserTarget(Long uId) {
        List<UserTarget> targets = userTargetMapper.selectUserTarget(uId);
        if(targets != null && targets.size() == 1){
            Map<String, Object> map = new HashMap<>();
            map.put("target",targets.get(0));
            return ResponseResult.ok(map);
        }
        return ResponseResult.error("-1","暂无健康目标！");
    }




    /**
     * 查询视频历史
     */
    @Override
    public ResponseResult selectUserListen(Long uId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<UserListenRecord> records = userListenMapper.selectUserListen(uId);
        PageInfo<UserListenRecord> pageInfo = new PageInfo<>(records);
        Page<UserListenRecord> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }


    /**
     * 插入或更新视频
     */
    @Override
    public ResponseResult updateUserListen(UserListenRecord userListenRecord) {
        int count = userListenMapper.updateUserListen(userListenRecord);
        if(count > 0){
            return ResponseResult.ok(new HashMap<>());
        }
        return ResponseResult.error("-1","操作失败！");
    }


    /**
     * 用户积分明细
     */
    @Override
    public ResponseResult selectUserPoints(Long uId, Integer page, Integer pageSize) {
        List<User> users = userMapper.selectUserByTel(uId, null);
        if(users != null && users.size() == 1){
            PageHelper.startPage(page,pageSize);
            List<UserPointsRecord> records = userPointsMapper.selectUserPoints(uId,null,null);
            PageInfo<UserPointsRecord> pageInfo = new PageInfo<>(records);
            Page<UserPointsRecord> result = new Page<>(pageInfo);

            Map<String, Object> map = new HashMap<>();
            map.put("level",users.get(0).getLevel());
            map.put("integral",users.get(0).getIntegral());
            map.put("result",result);
            return ResponseResult.ok(map);
        }
        return ResponseResult.error("1","token失效！");
    }


    /////////////后台///////////////
    /**
     * 查询所有用户
     */
    @Override
    public ResponseEntity selectAllUser(Long manageId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<User> users = userMapper.selectUserByHT(manageId);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        Page<User> result = new Page<>(pageInfo);
        return ResponseEntity.se(result);
    }


    /**
     * 条件搜索用户
     */
    @Override
    public ResponseEntity selectLikeUser(String telephone, Long manageId) {
        List<User> users = userMapper.selectUserBy(telephone, manageId);
        return ResponseEntity.se(users);
    }



    /**
     * 插入或更新目标
     */
    @Override
    public ResponseEntity updateUserTarget(UserTarget userTarget) {
        int count = userTargetMapper.updateUserTarget(userTarget);
        if(count > 0){
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("-1","操作失败！");
    }



}
