package com.jyss.ziwei.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.config.AliConfig;
import com.jyss.ziwei.constant.Constant;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.mapper.*;
import com.jyss.ziwei.service.HealthActivityService;
import com.jyss.ziwei.service.PointsRecordService;
import com.jyss.ziwei.service.UserMessageService;
import com.jyss.ziwei.utils.CommTool;
import com.jyss.ziwei.utils.DateFormatUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HealthActivityServiceImpl implements HealthActivityService {

    @Autowired
    private HealthActivityMapper haMapper;
    @Autowired
    private HealthActivityReportMapper hrMapper;
    @Autowired
    private MobileLoginMapper loginMapper;
    @Autowired
    private WebLoginMapper webLoginMapper;
    @Autowired
    private UserMessageMapper umMapper;
    @Autowired
    private PointsRecordService pointService;

    @Override
    public int insertHealthActivity(HealthActivity healthActivity) {
        return haMapper.insertHealthActivity(healthActivity);
    }

    @Override
    public int updateByPrimaryKey(HealthActivity healthActivity) {
        return haMapper.updateByPrimaryKey(healthActivity);
    }

    @Override
    public int deleteHealthActivity(@Param("id") String id) {
        return haMapper.deleteHealthActivity(id);
    }

    @Override
    public List<HealthActivity> getHealthActivity(@Param("status") String status, @Param("type") String type, @Param("id") String id) {
        return haMapper.getHealthActivity(status, type, id);
    }

    @Override
    public List<HealthActivity> getHealthActivityDetials(@Param("status") String status, @Param("type") String type, @Param("id") String id) {
        return haMapper.getHealthActivityDetials(status, type, id);
    }

    @Override
    public List<HealthActivity> getMyActivityReport(@Param("status") String status, @Param("uId") String uId, @Param("id") String id) {
        return haMapper.getMyActivityReport(status, uId, id);
    }

    /////浏览健康活动
    @Override
    public ResponseResult browseHealthActivity(String token, int page, int limit) {
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        ////2.分页查询活动
        PageHelper.startPage(page, limit);// 分页语句
        List<HealthActivity> haList = haMapper.getHealthActivity(null, null, null);
        ////3.循环查找每个活动参加人数,并判断当前用户是否参加 0未参加
        if (haList != null && haList.size() > 0) {
            for (HealthActivity ha : haList) {
                ////查询活动
                List<HealthActivityReport> hrList = new ArrayList<HealthActivityReport>();
                hrList = hrMapper.getActivityReport(null, null, null, ha.getId() + "");
                if (hrList != null && hrList.size() > 0) {
                    ha.setPeople(hrList.size() + "");
                    //判断当前用户是否参加,默认为参加0
                   /* ha.setIsJion("0");
                    for(HealthActivityReport hr:hrList){
                        ////LOng对象比较==比较地址，longValue比较值
                        if(hr.getuId().longValue()==uId.longValue()){
                            ha.setIsJion("1");
                            break;
                        }
                    }*/
                } else {
                    ha.setPeople("0");
                    //ha.setIsJion("0");
                }
            }
        }
        PageInfo<HealthActivity> pageInfo = new PageInfo<HealthActivity>(haList);
        return ResponseResult.ok(new Page<HealthActivity>(pageInfo));
    }


    ///我的健康活动   bz 1=待审核；2=待支付；3=待参加；4=已结束；5=审核失败；
    @Override
    public ResponseResult browseMyActivity(String token, String bz, int page, int limit) {
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        ////2.分页查询活动= bz 1=待审核；2=待支付；3=待参加；4=已结束；5=审核失败；
        //报名status =1=正常未支付，待审核，2=审核通过，未支付，3=预约成功，无需审核，未支付，4=已支付,待审核；5=已支付已审核；6=活动已结束；7=审核失败
        List<HealthActivity> haList = new ArrayList<HealthActivity>();
        PageHelper.startPage(page, limit);// 分页语句
        if (bz.equals("1")) {
            haList = haMapper.getMyActivityReport("1,4", uId + "", null);
        } else if (bz.equals("2")) {
            haList = haMapper.getMyActivityReport("2,3", uId + "", null);
        } else if (bz.equals("3")) {
            haList = haMapper.getMyActivityReport("5", uId + "", null);
        } else if (bz.equals("4")) {
            haList = haMapper.getMyActivityReport("6", uId + "", null);
        } else if (bz.equals("5")) {
            haList = haMapper.getMyActivityReport("7", uId + "", null);
        } else {
            return ResponseResult.error("0", "查询异常！");
        }
        ////3.循环查找
        if (haList != null && haList.size() > 0) {
            for (HealthActivity ha : haList) {
                List<HealthActivityReport> hrList = new ArrayList<HealthActivityReport>();
                ////查询活动
                if(bz.equals("5")){
                    hrList = hrMapper.getActivityReportRefuseSh(null, null, ha.getId() + "");
                }else{
                    hrList = hrMapper.getActivityReport(null, null, null, ha.getId() + "");
                }
                if (hrList != null && hrList.size() > 0) {
                    ha.setPeople(hrList.size() + "");
                } else {
                    ha.setPeople("0");
                }
            }
        }
        PageInfo<HealthActivity> pageInfo = new PageInfo<HealthActivity>(haList);
        return ResponseResult.ok(new Page<HealthActivity>(pageInfo));
    }

    /////浏览健康活动详情
    @Override
    public ResponseResult browseDetailHealthActivity(String token, String hId,Integer isFaliure) {
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }

        List<HealthActivity> haList = haMapper.getHealthActivityDetials(null, null, hId);
        ////2.循环查找活动参加人数,并判断当前用户是否参加 0未参加
        List<HealthActivityReport> hrList = null;
        HealthActivityReport h = new HealthActivityReport();
        HealthActivity ha = new HealthActivity();
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("activity",ha);
        m.put("join",h);
        if (haList != null && haList.size() == 1) {
            ha = haList.get(0);
            ////查询活动参加人数
            hrList = hrMapper.getActivityReport(null, null, null, ha.getId() + "");
            if (hrList != null && hrList.size() > 0) {
                ha.setPeople(hrList.size() + "");
            }
            ////审核失败
            if(isFaliure!=null&&isFaliure==1){
                System.out.println("=======================================>"+isFaliure);
                 hrList = hrMapper.getActivityReportRefuseSh(null, uId.toString(), ha.getId() + "");
                 if(hrList!=null&&hrList.size()>0){
                    m.put("join",hrList.get(0));
                    ha.setIsJion("1");
                 }
            ///不是审核失败
            }else{
                System.out.println("qqq=======================================>"+isFaliure);
                if (hrList != null && hrList.size() > 0) {
                    //判断当前用户是否参加,默认为参加0
                    ha.setIsJion("0");
                    for (HealthActivityReport hr : hrList) {
                        ////LOng对象比较==比较地址，longValue比较值
                        if (hr.getuId().longValue() == uId.longValue()) {
                            ha.setIsJion("1");
                            m.put("join",hr);
                            break;
                        }
                    }
                } else {
                    ha.setPeople("0");
                    ha.setIsJion("0");
                }
            }
        }
        m.put("activity",ha);
        return ResponseResult.ok(m);
    }

    ///参加健康活动
    @Override
    public ResponseResult joinHealthActivity(String token, HealthActivityReport hr) {
        Map<String, Object> m = new HashMap<String, Object>();
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        String outTradeNo = DateFormatUtils.getNowDateText("yyMMddHHmmss") + "O" + uId
                + "r" + (long) (Math.random() * 1000L);
        ////2.查询当前活动，判断是否可以进行报名，查询活动信息
        List<HealthActivity> haList = haMapper.getHealthActivityDetials(null, null, hr.getrId() + "");
        if (haList == null || haList.size() != 1) {
            return ResponseResult.error("0", "当前活动异常！");
        }

        HealthActivity ha = haList.get(0);
        // 2.1//活动status//1=正常，未开始，2-已开始，3=已结束
        if (ha.getStatus() == 2) {
            return ResponseResult.error("0", "当前活动已开始！");
        } else if (ha.getStatus() == 3) {
            return ResponseResult.error("0", "当前活动已结束！");
        }
        ///2.2判断是否参加过
        List<HealthActivityReport> hrList = hrMapper.getActivityReport(null, null, uId + "", hr.getrId() + "");
        if (hrList != null && hrList.size() > 0) {
            return ResponseResult.error("0", "当前活动已预约！");
        }
        hr.setuId(uId);
        hr.sethType(ha.getType());
        hr.setMoney(ha.getMoney());
        hr.setOrderSn(outTradeNo);
        hr.setZfType(0);
        hr.sethTitle(ha.getTitles());
        //2.2//报名status//1=正常未支付，待审核，2=审核通过，未支付，3=预约成功，无需审核，未支付，4=已支付,待审核；5=已支付已审核；6=活动已结束
        ///活动type=活动类型=1=预约-审核-付钱；2=预约-付钱，无审核；
        if (ha.getType() == 1) {
            hr.setStatus(1);
        } else if (ha.getType() == 2) {
            hr.setStatus(3);
        }
        hr.setOrderSn(outTradeNo);
        int count = hrMapper.insertReport(hr);
        if (count == 1) {
            m.put("msg", "预约成功！");
            m.put("status", hr.getStatus());
            return ResponseResult.ok(m);
        }
        return ResponseResult.error("0", "预约失败！");
    }

    ////活动支付
    @Override
    public ResponseResult payHealthActivity(String token, String rId, String type) {
        Map<String, Object> m = new HashMap<String, Object>();
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        ///2=验证订单
        List<HealthActivityReport> hrList = hrMapper.getActivityReport(null, null, uId + "", rId);
        if (hrList == null || hrList.size() != 1) {
            return ResponseResult.error("0", "预约活动异常！");
        }
        HealthActivityReport har = hrList.get(0);
        // 报名status//1=正常未支付，待审核，2=审核通过，未支付，3=预约成功，无需审核，未支付，4=已支付,待审核；5=已支付已审核；6=活动已结束
        if (har.getStatus() == 1 || har.getStatus() == 4 || har.getStatus() == 5 || har.getStatus() == 6) {
            return ResponseResult.error("0", "预约活动状态异常！");
        }
        ///3=选择支付方式 type ：1=支付宝支付，2=微信支付
        if (type.equals("1")) {
            return aliPayHealthActivity(har);
        } else if (type.equals("2")) {

        }
        return ResponseResult.error("0", "支付异常！");
    }

    ////支付宝支付
    public ResponseResult aliPayHealthActivity(HealthActivityReport har) {
        Map<String, Object> m = new HashMap<String, Object>();
        String subject = "紫薇活动付费";
        String totalAmount = har.getMoney() + "";
        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "紫薇活动付费 " + totalAmount + "元";
        String outTradeNo = har.getOrderSn();
        String timeoutExpress = "30m";   // 支付超时，定义为30分钟
        String notifyUrl = Constant.httpUrl + "ZIWEI/healthActivity/AliNotify.action";
        /////3.=支付初始化
        AliConfig config = new AliConfig();

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(config.getURL(), config.getAPP_ID(), config.getAPP_PRIVATE_KEY(),
                "json", "UTF-8", config.getALIPAY_PUBLIC_KEY(), config.getSIGN_TYPE());

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(outTradeNo);
        model.setTimeoutExpress(timeoutExpress);
        model.setTotalAmount(totalAmount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyUrl);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            //System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            /////4.=插入自我业务未支付订单----此处目前没有
         /*   int count = uoMapper.insert(uo);
            if(count!=1){
                return ResponseResult.error("0", "订单预处理失败！");
            }*/
            m.put("msg", response.getBody());
            m.put("status", 5);
            return ResponseResult.ok(m);
        } catch (AlipayApiException e) {
            return ResponseResult.error("0", "支付异常！");
        }
    }

    ////活动支付===线下
    @Override
    public ResponseResult payPzHealthActivity(String token, String rId, String pzPic) {
        Map<String, Object> m = new HashMap<String, Object>();
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        ///2=验证订单
        List<HealthActivityReport> hrList = hrMapper.getActivityReport(null, null, uId + "", rId);
        if (hrList == null || hrList.size() != 1) {
            return ResponseResult.error("0", "预约活动异常！");
        }
        HealthActivityReport har = hrList.get(0);
        // 报名status//1=正常未支付，待审核，2=审核通过，未支付，3=预约成功，无需审核，未支付，4=已支付,待审核；5=已支付已审核；6=活动已结束
        if (har.getStatus() == 1 || har.getStatus() == 4 || har.getStatus() == 5 || har.getStatus() == 6) {
            return ResponseResult.error("0", "预约活动状态异常！");
        }
        HealthActivityReport h = new HealthActivityReport();
        h.setId(har.getId());
        h.setStatus(4);
        h.setZfType(3);
        h.setLastModifyTime(CommTool.getNowTimestamp());
        ///3=修改订单状态
        // 报名status//1=正常未支付，待审核，2=审核通过，未支付，3=预约成功，无需审核，未支付，4=已支付,待审核；5=已支付已审核；6=活动已结束
        try {
            hrMapper.updateByPrimaryKey(h);
            ///插入消息
            UserMessage um = new UserMessage();
            um.setStatus(0);
            um.setActivityId(har.getrId());
            um.setuId(uId);
            um.setMessage(har.gethTitle());
            um.setCreateTime(CommTool.getNowTimestamp());
            umMapper.insert(um);
            ///插入积分记录
            boolean c = pointService.updateUserPointsRecord(uId,har.getrId(),"健康活动",2);
            if (!c) {
                return ResponseResult.error("0", "提交支付凭证异常！");
            }
        } catch (Exception e) {
            return ResponseResult.error("0", "提交支付凭证异常！");
        }
        m.put("status", 4);
        m.put("msg", "提交凭证成功，待审核！");
        return ResponseResult.ok(m);
    }

    ///审核活动
    @Override
    public ResponseEntity checkHealthActivity(String token, String rId) {
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = webLoginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseEntity.op("0", "token失效！");
            }
        } else {
            return ResponseEntity.op("0", "token失效！");
        }
        ///查询报名信息
        List<HealthActivityReport> hrList = hrMapper.getActivityReport(null, null, uId + "", rId);
        if (hrList == null || hrList.size() != 1) {
            return ResponseEntity.op("0", "预约活动异常！");
        }
        HealthActivityReport har = hrList.get(0);
        HealthActivityReport h = new HealthActivityReport();
        h.setId(har.getId());
        h.setLastModifyTime(CommTool.getNowTimestamp());
        ////报名status//1=正常未支付，待审核，2=审核通过，未支付，3=预约成功，无需审核，未支付，4=已支付,待审核；5=已支付已审核；6=活动已结束
        if (har.getStatus() == 1) {
            h.setStatus(2);
        } else if (har.getStatus() == 4) {
            h.setStatus(5);
        } else {
            return ResponseEntity.op("0", "当前预约状态不支持审核！");
        }
        int count = hrMapper.updateByPrimaryKey(h);
        if (count != 1) {
            return ResponseEntity.op("0", "操作失败！");
        }
        return ResponseEntity.op("1", "操作成功！");
    }

    ///新增
    @Override
    public ResponseEntity insertHealthActivity(String token, HealthActivity ha) {
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = webLoginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseEntity.op("0", "token失效！");
            }
        } else {
            return ResponseEntity.op("0", "token失效！");
        }
        ////2.添加活动
        ha.setStatus(1);
        int count = haMapper.insertHealthActivity(ha);
        if (count != 1) {
            return ResponseEntity.op("0", "操作失败！");
        }
        return ResponseEntity.op("1", "操作成功！");
    }

    ///修改
    @Override
    public ResponseEntity updateHealthActivity(String token, HealthActivity ha) {
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = webLoginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseEntity.op("0", "token失效！");
            }
        } else {
            return ResponseEntity.op("0", "token失效！");
        }
        ////2.修改活动
        int count = haMapper.updateByPrimaryKey(ha);
        if (count != 1) {
            return ResponseEntity.op("0", "操作失败！");
        }
        return ResponseEntity.op("1", "操作成功！");


    }

    ///删除
    @Override
    public ResponseEntity deleteHealthActivity(String token, String hId) {

        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = webLoginMapper.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseEntity.op("0", "token失效！");
            }
        } else {
            return ResponseEntity.op("0", "token失效！");
        }
        ////2.删除活动
        int count = haMapper.deleteHealthActivity(hId);
        if (count != 1) {
            return ResponseEntity.op("0", "操作失败！");
        }
        return ResponseEntity.op("1", "操作成功！");

    }
}