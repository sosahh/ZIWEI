package com.jyss.ziwei.action;


import com.jyss.ziwei.constant.Constant;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.service.BodyDietService;
import com.jyss.ziwei.service.MobileLoginService;
import com.jyss.ziwei.utils.CommTool;
import com.jyss.ziwei.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/diet")
public class BodyDietAction {

    @Autowired
    private BodyDietService bodyDietService;
    @Autowired
    private MobileLoginService mobileLoginService;



    /**
     * 查询食物大卡
     */
    @RequestMapping("/foods")
    @ResponseBody
    public ResponseResult selectFood(@RequestParam(value = "page", required = true) Integer page,
                                     @RequestParam(value = "pageSize", required = true) Integer pageSize){

        return bodyDietService.selectFood(null,page,pageSize);
    }



    /**
     * 搜索食物大卡
     */
    @RequestMapping("/sfood")
    @ResponseBody
    public ResponseResult selectFoodByName(@RequestParam("name") String name,
                                           @RequestParam(value = "page", required = true) Integer page,
                                           @RequestParam(value = "pageSize", required = true) Integer pageSize){
        if(name == null || name.trim().length() == 0){
            return ResponseResult.error("-1","请输入食物名称");
        }
        return bodyDietService.selectFood(name,page,pageSize);
    }



    /**
     * 上传饮食照片
     */
    @RequestMapping("/updiet")
    @ResponseBody
    public ResponseResult uploadPicture(MultipartFile file, HttpServletRequest request) {
        //文件不能为空
        if(file.isEmpty()){
            return ResponseResult.error("-2","文件不能为空！");
        }

        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."));
        String filePath = request.getSession().getServletContext().getRealPath("/");
        int index = filePath.indexOf("ZIWEI");

        filePath = filePath.substring(0, index) + "uploadDiet" + "/";
        String imgPath = filePath + System.currentTimeMillis() + CommTool.getYzm() + extName;
        if (Utils.saveUpload(file, imgPath)) {
            String path = imgPath.substring(imgPath.indexOf("uploadDiet"));
            path = Constant.httpUrl + path;
            Map<String, Object> map = new HashMap<>();
            map.put("path",path);
            return ResponseResult.ok(map);
        }
        return ResponseResult.error("-1","文件上传失败！");
    }


    /**
     * 添加拍照上传饮食
     */
    @RequestMapping("/dinsert")
    @ResponseBody
    public ResponseResult insertBodyDiet(@RequestParam("token") String token, BodyDiet bodyDiet){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            if(!StringUtils.isEmpty(bodyDiet.getPicture())){
                String picture = bodyDiet.getPicture();
                bodyDiet.setPicture(picture.substring(picture.lastIndexOf("uploadDiet")));
            }
            bodyDiet.setuId(uId);
            bodyDiet.setDetails(bodyDiet.getEnergy() + "大卡");
            bodyDiet.setCreated(bodyDiet.getCreateTime());
            return bodyDietService.insertBodyDiet(bodyDiet);
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 添加选择饮食
     */
    @RequestMapping(value = "/plinsert", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult insertPLBodyDiet(@RequestParam("token") String token, @RequestParam("diets") String diets){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            return bodyDietService.insertPLBodyDiet(uId, diets);

        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 查询饮食记录
     */
    @RequestMapping("/diets")
    @ResponseBody
    public ResponseResult selectBodyDiet(@RequestParam("token")String token,
                                         @RequestParam(value = "page", required = true) Integer page,
                                         @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            return bodyDietService.selectBodyDiet(uId, page, pageSize);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 查询药物
     */
    @RequestMapping("/drugs")
    @ResponseBody
    public ResponseResult selectDrug(@RequestParam("drugType")Integer drugType,
                                     @RequestParam(value = "page", required = true) Integer page,
                                     @RequestParam(value = "pageSize", required = true) Integer pageSize){

        return bodyDietService.selectDrug(null, drugType, page, pageSize);
    }



    /**
     * 模糊查询药物
     */
    @RequestMapping("/sdrug")
    @ResponseBody
    public ResponseResult selectDrugByName(@RequestParam("name") String name, @RequestParam("drugType")Integer drugType,
                                           @RequestParam(value = "page", required = true) Integer page,
                                           @RequestParam(value = "pageSize", required = true) Integer pageSize){
        if(name == null || name.trim().length() == 0){
            return ResponseResult.error("-1","请输入药物名称！");
        }
        return bodyDietService.selectDrug(name, drugType, page, pageSize);
    }


    /**
     * 添加药物
     */
    @RequestMapping("/ginsert")
    @ResponseBody
    public ResponseResult insertBodyDrug(@RequestParam("token") String token, BodyDrug bodyDrug){
        if(StringUtils.isEmpty(bodyDrug.getMeasure()) || StringUtils.isEmpty(bodyDrug.getName())){
            return ResponseResult.error("-1","用药量不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            bodyDrug.setuId(uId);
            bodyDrug.setStatus(1);
            return bodyDietService.insertBodyDrug(bodyDrug);

        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 查询药物记录
     */
    @RequestMapping("/gselect")
    @ResponseBody
    public ResponseResult selectBodyDrug(@RequestParam("token") String token,
                                         @RequestParam(value = "page", required = true) Integer page,
                                         @RequestParam(value = "pageSize", required = true) Integer pageSize){

        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            return bodyDietService.selectBodyDrug(uId, page, pageSize);
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 查询最近的饮食和药物记录
     */
    @RequestMapping("/body")
    @ResponseBody
    public ResponseResult selectDrugAndDiet(@RequestParam("token") String token){

        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            return bodyDietService.selectDrugAndDiet(uId);
        }
        return ResponseResult.error("1","token失效！");
    }



    /////////////后台////////////
    /**
     * 上传食物图片
     */
    @RequestMapping("/loadfood")
    @ResponseBody
    public ResponseEntity uploadFoodPicture(MultipartFile file, HttpServletRequest request) {
        //文件不能为空
        if(file.isEmpty()){
            return ResponseEntity.op("-2","文件不能为空！");
        }

        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."));
        String filePath = request.getSession().getServletContext().getRealPath("/");
        int index = filePath.indexOf("ZIWEI");

        filePath = filePath.substring(0, index) + "uploadFood" + "/";
        String imgPath = filePath + System.currentTimeMillis() + CommTool.getYzm() + extName;
        if (Utils.saveUpload(file, imgPath)) {
            String path = imgPath.substring(imgPath.indexOf("uploadFood"));
            path = Constant.httpUrl + path;
            Map<String, Object> map = new HashMap<>();
            map.put("path",path);
            return ResponseEntity.se(map);
        }
        return ResponseEntity.op("-1","文件上传失败！");
    }



    /**
     * 添加食物
     */
    @RequestMapping("/infood")
    @ResponseBody
    public ResponseEntity insertFood(@RequestParam("token")String token,Food food){
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            if(!StringUtils.isEmpty(food.getPicture())){
                String picture = food.getPicture();
                food.setPicture(picture.substring(picture.lastIndexOf("uploadFood")));
            }
            food.setStatus(1);
            food.setCreateTime(new Date());
            bodyDietService.insertFood(food);
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 修改食物
     */
    @RequestMapping("/upfood")
    @ResponseBody
    public ResponseEntity updateFood(@RequestParam("token")String token,Food food){
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            if(!StringUtils.isEmpty(food.getPicture())){
                String picture = food.getPicture();
                food.setPicture(picture.substring(picture.lastIndexOf("uploadFood")));
            }
            food.setCreateTime(new Date());
            bodyDietService.updateByPrimaryKey(food);
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 删除食物
     */
    @RequestMapping("/delfood")
    @ResponseBody
    public ResponseEntity deleteFood(@RequestParam("token")String token,@RequestParam("fId")Integer fId){
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            Food food = new Food();
            food.setId(fId);
            food.setStatus(0);
            bodyDietService.updateByPrimaryKey(food);
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 添加药物
     */
    @RequestMapping("/indrug")
    @ResponseBody
    public ResponseEntity insertDrug(@RequestParam("token")String token,Drug drug){
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            drug.setStatus(1);
            drug.setCreateTime(new Date());
            bodyDietService.insertDrug(drug);
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("0","token失效！");
    }


    /**
     * 修改药物
     */
    @RequestMapping("/updrug")
    @ResponseBody
    public ResponseEntity updateDrug(@RequestParam("token")String token,Drug drug){
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            drug.setCreateTime(new Date());
            bodyDietService.updateDrugByKey(drug);
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("0","token失效！");
    }


    /**
     * 删除药物
     */
    @RequestMapping("/deldrug")
    @ResponseBody
    public ResponseEntity deleteDrug(@RequestParam("token")String token,@RequestParam("dId")Integer dId){
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            Drug drug = new Drug();
            drug.setId(dId);
            drug.setStatus(0);
            bodyDietService.updateDrugByKey(drug);
            return ResponseEntity.se("");
        }
        return ResponseEntity.op("0","token失效！");
    }


}
