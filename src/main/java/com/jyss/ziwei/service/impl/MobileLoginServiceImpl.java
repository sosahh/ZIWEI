package com.jyss.ziwei.service.impl;

import com.jyss.ziwei.entity.MobileLogin;
import com.jyss.ziwei.mapper.MobileLoginMapper;
import com.jyss.ziwei.service.MobileLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class MobileLoginServiceImpl implements MobileLoginService {

    @Autowired
    private MobileLoginMapper mobileLoginMapper;

    @Override
    public int insert(MobileLogin mobileLogin) {
        return mobileLoginMapper.insert(mobileLogin);
    }

    @Override
    public List<MobileLogin> findUserByToken(String token) {
        List<MobileLogin> loginList = mobileLoginMapper.findUserByToken(token);
        if (loginList.size() == 1) {
            if (!loginList.get(0).getToken().equals(token)) {
                loginList = new ArrayList<MobileLogin>();
            }
        } else {
            loginList = new ArrayList<MobileLogin>();
        }
        return loginList;
    }


    ////////////web端////////////
    @Override
    public int insertMobileLogin(MobileLogin mobileLogin) {
        return mobileLoginMapper.insertMobileLogin(mobileLogin);
    }

    @Override
    public List<MobileLogin> selectWebUserByToken(String token) {
        List<MobileLogin> loginList = mobileLoginMapper.selectWebUserByToken(token);
        if (loginList.size() == 1) {
            if (!loginList.get(0).getToken().equals(token)) {
                loginList = new ArrayList<MobileLogin>();
            }
        } else {
            loginList = new ArrayList<MobileLogin>();
        }
        return loginList;
    }


}
