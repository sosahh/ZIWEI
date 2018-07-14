package com.jyss.ziwei.service.impl;

import com.jyss.ziwei.entity.BConstant;
import com.jyss.ziwei.entity.User;
import com.jyss.ziwei.entity.UserPointsRecord;
import com.jyss.ziwei.mapper.BConstantMapper;
import com.jyss.ziwei.mapper.UserMapper;
import com.jyss.ziwei.mapper.UserPointsRecordMapper;
import com.jyss.ziwei.service.PointsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class PointsRecordServiceImpl implements PointsRecordService {

    @Autowired
    private UserPointsRecordMapper pointsRecordMapper;
    @Autowired
    private BConstantMapper bConstantMapper;
    @Autowired
    private UserMapper userMapper;


    /**
     * 添加数据录入积分     type:1=主题知识，2=健康活动，3=数据录入
     */
    @Override
    public boolean updateUserPointsRecord(Long uId, Integer knowId, String detail, Integer type) {

        int zsmin = 5;
        int zsmax = 30;
        int hdmin = 100;
        int sjmin = 10;
        int sjmax = 200;
        //查询常量
        BConstant constant = bConstantMapper.selectBConstantBy("points_type", "1");
        if(constant != null){
            zsmin = Integer.parseInt(constant.getBzValue());
        }
        BConstant constant1 = bConstantMapper.selectBConstantBy("points_type", "2");
        if(constant1 != null){
            zsmax = Integer.parseInt(constant1.getBzValue());
        }
        BConstant constant2 = bConstantMapper.selectBConstantBy("points_type", "3");
        if(constant2 != null){
            hdmin = Integer.parseInt(constant2.getBzValue());
        }
        BConstant constant3 = bConstantMapper.selectBConstantBy("points_type", "4");
        if(constant3 != null){
            sjmin = Integer.parseInt(constant3.getBzValue());
        }
        BConstant constant4 = bConstantMapper.selectBConstantBy("points_type", "5");
        if(constant4 != null){
            sjmax = Integer.parseInt(constant4.getBzValue());
        }

        if(type == 1){
            int total = total(uId, type);
            if(total < zsmax){
                updateUserLevel(uId,knowId,detail,type,zsmin);
                return true;
            }
            return false;
        }else if(type == 2){
            updateUserLevel(uId,0,detail,type,hdmin);
            return true;
        }else if(type == 3){
            int total = total(uId, type);
            if(total < sjmax){
                updateUserLevel(uId,0,detail,type,sjmin);
                return true;
            }
            return false;
        }
        return false;
    }


    /**
     * 添加积分记录，更新等级
     * @param uId
     * @param knowId   健康知识id
     * @param detail   说明文字
     * @param type
     * @param score    积分
     */
    private void updateUserLevel(Long uId, Integer knowId, String detail, Integer type, Integer score){
        int value1 = 1000;
        int value2 = 2000;
        int value3 = 3000;
        int value4 = 4000;
        int value5 = 5000;
        //查询等级常量
        BConstant constant = bConstantMapper.selectBConstantBy("vip_type", "1");
        if(constant != null){
            value1 = Integer.parseInt(constant.getBzValue());
        }
        BConstant constant1 = bConstantMapper.selectBConstantBy("vip_type", "2");
        if(constant1 != null){
            value2 = Integer.parseInt(constant1.getBzValue());
        }
        BConstant constant2 = bConstantMapper.selectBConstantBy("vip_type", "3");
        if(constant2 != null){
            value3 = Integer.parseInt(constant2.getBzValue());
        }
        BConstant constant3 = bConstantMapper.selectBConstantBy("vip_type", "4");
        if(constant3 != null){
            value4 = Integer.parseInt(constant3.getBzValue());
        }
        BConstant constant4 = bConstantMapper.selectBConstantBy("vip_type", "5");
        if(constant4 != null){
            value5 = Integer.parseInt(constant4.getBzValue());
        }

        UserPointsRecord record = new UserPointsRecord();
        record.setuId(uId);
        record.setKnowId(knowId);
        record.setBzType(type);
        record.setDetail(detail);
        record.setScoreType(1);
        record.setScore(score);
        record.setStatus(1);
        record.setCreatedAt(new Date());
        pointsRecordMapper.insert(record);

        List<User> users = userMapper.selectUserByTel(uId, null);
        User user = users.get(0);

        score += user.getIntegral();
        User user1 = new User();
        user1.setuId(user.getuId());
        user1.setIntegral(score);
        if(score < value1){
            user1.setLevel(0);
        }else if(value1 <= score && score < value2){
            user1.setLevel(1);
        }else if(value2 <= score && score < value3){
            user1.setLevel(2);
        }else if(value3 <= score && score < value4){
            user1.setLevel(3);
        }else if(value4 <= score && score < value5){
            user1.setLevel(4);
        }else if(value5 <= score){
            user1.setLevel(5);
        }
        userMapper.updateByPrimaryKey(user1);
    }


    /**
     * 计算单日总积分
     */
    private int total(Long uId,Integer bzType){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<UserPointsRecord> list = pointsRecordMapper.selectUserPoints(uId, bzType, dateFormat.format(new Date()));
        int sum = 0;
        if(list != null && list.size() > 0){
            for (UserPointsRecord userPointsRecord : list) {
                sum += userPointsRecord.getScore();
            }
        }
        return sum;
    }



}
