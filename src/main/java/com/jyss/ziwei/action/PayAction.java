package com.jyss.ziwei.action;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

import com.jyss.ziwei.config.AliConfig;
import com.jyss.ziwei.entity.HealthActivityReport;
import com.jyss.ziwei.entity.ResponseResult;
import com.jyss.ziwei.entity.UserMessage;
import com.jyss.ziwei.service.HealthActivityReportService;
import com.jyss.ziwei.service.PointsRecordService;
import com.jyss.ziwei.service.UserMessageService;
import com.jyss.ziwei.utils.CommTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class PayAction {
    @Autowired
    private HealthActivityReportService hrService;
    @Autowired
    private UserMessageService umService;
    @Autowired
    private PointsRecordService pointService;

    private Logger logger = LoggerFactory.getLogger(PayAction.class);

    /**
     * 支付宝充值,服务端验证异步通知信息
     */
    @RequestMapping(value = "/healthActivity/AliNotify", method = RequestMethod.POST)
    public String updatehealthActivityReport(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            logger.info("支付宝回调信息key: " + name + ", value: " + valueStr);
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        AliConfig config = new AliConfig();
        boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(params, config.getALIPAY_PUBLIC_KEY(), "utf-8", "RSA2");
            if (flag) {
                String tradeStatus = request.getParameter("trade_status");
                if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
                    //验签成功,对支付结果中的业务内容进行1\2\3\4二次校验
                    String outTradeNo = request.getParameter("out_trade_no");
                    String totalAmount = request.getParameter("total_amount");
                    String sellerId = request.getParameter("seller_id");
                    String appId = request.getParameter("app_id");
                    if (config.getAPP_ID().equals(appId) && config.getSELLER_ID().equals(sellerId)) {
                        //自己业务处理==
                        //1=查询订单
                        //  String outTradeNo = "180706143252O1r962";
                        List<HealthActivityReport> llist = hrService.getPayActivityReport(null, null, null, null, outTradeNo);
                        if (llist == null || llist.size() != 1) {
                            logger.info("支付宝服务端验签发生异常==订单重复！");
                            return "failure";
                        }
                        // 报名status//1=正常未支付，待审核，2=审核通过，未支付，3=预约成功，无需审核，未支付，4=已支付,待审核；5=已支付已审核；6=活动已结束
                        if (!(llist.get(0).getStatus() == 2 || llist.get(0).getStatus() == 3)) {
                            logger.info("支付宝服务端验签发生异常==订单状态不是未支付！");
                            return "failure";
                        }
                        //2=处理订单
                        HealthActivityReport h = new HealthActivityReport();
                        h.setOrderSn(outTradeNo);
                        h.setStatus(5);/// ////报名status//1=正常未支付，待审核，2=审核通过，未支付，3=预约成功，无需审核，未支付，4=已支付,待审核；5=已支付已审核；6=活动已结束
                        h.setZfType(1);//支付宝支付
                        h.setLastModifyTime(CommTool.getNowTimestamp());
                        int count = hrService.updateByPrimaryKey(h);
                        if (count != 1) {
                            logger.info("支付宝服务端验签发生异常==支付失败！");
                            return "failure";
                        }else{
                            UserMessage um  = new UserMessage();
                            um.setStatus(0);
                            um.setActivityId(h.getrId());
                            um.setuId(h.getuId());
                            um.setMessage(h.gethTitle());
                            um.setCreateTime(CommTool.getNowTimestamp());
                            int c= umService.insert(um);
                            if(c!=1){
                                logger.info("支付宝服务端验签发生异常==userMessage插入失败！");
                                return "failure";
                            }else{
                                boolean c1 = pointService.updateUserPointsRecord(h.getuId(),h.getrId(),"健康活动",2);
                                if (!c1) {
                                    logger.info("支付宝服务端验签发生异常==积分收入插入失败！");
                                    return "failure";
                                }
                            }

                        }
                    }

                }
            }
            logger.info("支付宝服务端验证异步通知信息失败！");
            return "failure";          // 验签失败

        } catch (AlipayApiException e) {
            logger.info("支付宝服务端验签发生异常！");
            return "failure";          // 验签发生异常,则直接返回失败
        }

    }


}
