package com.jyss.ziwei.action;

import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.service.HealthActivityService;
import com.jyss.ziwei.utils.CommTool;
import com.jyss.ziwei.utils.DateFormatUtils;
import com.jyss.ziwei.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/healthActivity")
public class HealthActivityAction {
    @Autowired
    private HealthActivityService haService;


    ///////////////////////app//////////////////////
    /**
     * 健康活动浏览
     * @param token
     * @return
     */
    @RequestMapping("/browse")
    @ResponseBody
    public ResponseResult browseHealthActivity(@RequestParam("token") String token,@RequestParam(value = "page", required = true) int page,
                                               @RequestParam(value = "limit", required = true) int limit) {
        return haService.browseHealthActivity(token,page,limit);

    }

    /**
     * 健康活动浏览==具体活动详情
     * @param token
     * @param hId
     * @param isFaliure  1=审核失败
     * @return
     */
    @RequestMapping("/browseDetail")
    @ResponseBody
    public ResponseResult browseDetailHealthActivity(@RequestParam("token") String token, @RequestParam("hId") String hId, @RequestParam(value="isFaliure",required = false) Integer isFaliure) {
        System.out.println("=======================================>"+isFaliure);
        return haService.browseDetailHealthActivity(token,hId,isFaliure);

    }

    /**
     * 我的活动  bz 1=待审核；2=待支付；3=待参加；4=已结束,5=审核失败；
     * @param token
     * @param bz
     * @return
     */
    @RequestMapping("/browseMyActivity")
    @ResponseBody
    public ResponseResult browseMyActivity(@RequestParam("token") String token, @RequestParam("bz") String bz,@RequestParam(value = "page", required = true) int page,
                                           @RequestParam(value = "limit", required = true) int limit) {
        return haService.browseMyActivity(token,bz,page,limit);

    }

    /**
     *参加活动---活动type 1=预约-审核-付钱；2=预约-付钱，无审核；
     * @param token
     * @param hr
     * @return
     */
    @RequestMapping("/join")
    @ResponseBody
    public ResponseResult joinHealthActivity(@RequestParam("token") String token, HealthActivityReport hr) {
        return haService.joinHealthActivity(token,hr);

    }

    /**
     * 活动付费 type ：1=支付宝支付，2=微信支付，3=线下支付
     * @param token
     * @param rId
     * @return
     */
    @RequestMapping("/mypay")
    @ResponseBody
    public ResponseResult payHealthActivity(@RequestParam("token") String token, @RequestParam("rId") String rId,
                                            @RequestParam("type") String type, @RequestParam("pzPic") String pzPic) {
      if(type.equals("3")){
          return haService.payPzHealthActivity(token,rId,pzPic);
      }
      return haService.payHealthActivity(token,rId,type);

    }



    /**
     * 支付凭证上传
     */
    @RequestMapping("/uploadPz")
    @ResponseBody
    public ResponseResult uploadPz(@RequestParam("pic") MultipartFile pic, HttpServletRequest request) {
        //图片不能为空
        Map<String,String> m  =new HashMap<String,String>();
        if(pic.isEmpty()){
            return ResponseResult.error("-2","图片不能为空！");
        }
        String filePath = request.getSession().getServletContext().getRealPath("/");
        int index = filePath.indexOf("ZIWEI");

        String filename = pic.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."));

        filePath = filePath.substring(0, index) + "uploadPzPic" + "/"+ DateFormatUtils.getDateText(new Date(), "yyyyMMdd") + "/";
        String imgPath = filePath + System.currentTimeMillis() + CommTool.getYzm() + extName;
        if (Utils.saveUpload(pic, imgPath)) {
            String path = imgPath.substring(imgPath.indexOf("uploadPzPic"));
            m.put("url",path);
            return ResponseResult.ok(m);
        }
        return ResponseResult.error("-1","上传凭证失败！");
    }

    /**
     *  活动图片上传
     * @param pic
     * @param request
     * @return
     */
    @RequestMapping("/uploadActivty")
    @ResponseBody
    public ResponseResult uploadActivty(@RequestParam("pic") MultipartFile pic, HttpServletRequest request) {
        //图片不能为空
        Map<String,String> m  =new HashMap<String,String>();
        if(pic.isEmpty()){
            return ResponseResult.error("-2","图片不能为空！");
        }
        String filePath = request.getSession().getServletContext().getRealPath("/");
        int index = filePath.indexOf("ZIWEI");

        String filename = pic.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."));

        filePath = filePath.substring(0, index) + "uploadActivtyPic" + "/"+ DateFormatUtils.getDateText(new Date(), "yyyyMMdd") + "/";
        String imgPath = filePath + System.currentTimeMillis() + CommTool.getYzm() + extName;
        if (Utils.saveUpload(pic, imgPath)) {
            String path = imgPath.substring(imgPath.indexOf("uploadActivtyPic"));
            m.put("url",path);
            return ResponseResult.ok(m);
        }
        return ResponseResult.error("-1","上传凭证失败！");
    }



    ///////////////////////web//////////////////////
    /**
     * 活动审核
     * @param token
     * @param rId
     * @return
     */
    @RequestMapping("/check")
    @ResponseBody
    public ResponseEntity checkHealthActivity(@RequestParam("token") String token, @RequestParam("rId") String rId) {
        return haService.checkHealthActivity(token,rId);

    }

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping("/insert")
    @ResponseBody
    public ResponseEntity insertHealthActivity(@RequestParam("token") String token, HealthActivity ha) {
        return haService.insertHealthActivity(token,ha);

    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseEntity updateHealthActivity(@RequestParam("token") String token, HealthActivity ha) {
        return haService.updateHealthActivity(token,ha);

    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseEntity deleteHealthActivity(@RequestParam("token") String token, @RequestParam("hId") String hId) {
        return haService.deleteHealthActivity(token,hId);

    }




}