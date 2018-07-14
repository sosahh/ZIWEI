package com.jyss.ziwei.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.service.MobileLoginService;
import com.jyss.ziwei.service.PhysicalTestService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/physicTest")
public class PhysicalTestAction {

    @Autowired
    private MobileLoginService loginservice;
    @Autowired
    private PhysicalTestService ptService;
    /**
     * 体检图片上传
     */
    @RequestMapping("/uploadTestpic")
    @ResponseBody
    public ResponseResult uploadTestpic(@RequestParam("pic") MultipartFile pic, HttpServletRequest request) {
        //图片不能为空
        Map<String,String> m  =new HashMap<String,String>();
        if(pic.isEmpty()){
            return ResponseResult.error("-2","图片不能为空！");
        }
        String filePath = request.getSession().getServletContext().getRealPath("/");
        int index = filePath.indexOf("ZIWEI");

        String filename = pic.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."));

        filePath = filePath.substring(0, index) + "uploadPhysicPic" + "/"+ DateFormatUtils.getDateText(new Date(), "yyyyMMdd") + "/";
        String imgPath = filePath + System.currentTimeMillis() + CommTool.getYzm() + extName;
        if (Utils.saveUpload(pic, imgPath)) {
            String path = imgPath.substring(imgPath.indexOf("uploadPhysicPic"));
            m.put("url",path);
            return ResponseResult.ok(m);
        }
        return ResponseResult.error("-1","上传图片失败！");
    }


    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * 新增常量
     */
    @RequestMapping("/insert")
    @ResponseBody
    public ResponseResult insertPhysicTest(@RequestParam("token")String token, PhysicalTest physicalTest){
        //1=验证登录
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("msg","错误");
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginservice.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        ///业务操作
        physicalTest.setStatus(1);
        physicalTest.setuId(uId.intValue());
        int count = ptService.insert(physicalTest);
        if(count == 1){
            m.put("msg","操作成功");
            return ResponseResult.ok(m);
        }
        return ResponseResult.error("0","操作失败！");

    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult deletePhysicTest(@RequestParam("token")String token, @RequestParam("cId")String cId){
        //1=验证登录
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("msg","错误");
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginservice.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        ///业务操作
        int count = ptService.deletePhysicalTest(cId);
        if(count == 1){
            m.put("msg","操作成功");
            return ResponseResult.ok(m);
        }
        return ResponseResult.error("0","操作失败！");

    }

    @RequestMapping("/getMyPhysicTest")
    @ResponseBody
    public ResponseResult getMyPhysicTest(@RequestParam("token")String token,@RequestParam(value = "page", required = true) int page,
                                          @RequestParam(value = "limit", required = true) int limit){
        //1=验证登录
        Long uId = 0l;
        if (token != null && !(token.equals(""))) {
            //判断登录
            List<MobileLogin> loginList = loginservice.findUserByToken(token);
            if (loginList != null && loginList.size() == 1) {
                MobileLogin mobileLogin = loginList.get(0);
                uId = mobileLogin.getuId();

            } else {
                return ResponseResult.error("0", "token失效！");
            }
        } else {
            return ResponseResult.error("0", "token失效！");
        }
        ///业务操作
        PageHelper.startPage(page, limit);// 分页语句
        List<PhysicalTest>  plist = ptService.getPhysicalTest("1",uId.toString(),null);
        PageInfo<PhysicalTest> pageInfo = new PageInfo<PhysicalTest>(plist);
        return ResponseResult.ok(new Page<PhysicalTest>(pageInfo));


    }



}