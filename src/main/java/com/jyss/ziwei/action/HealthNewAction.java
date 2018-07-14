package com.jyss.ziwei.action;

import com.jyss.ziwei.constant.Constant;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.service.HealthNewService;
import com.jyss.ziwei.service.MobileLoginService;
import com.jyss.ziwei.service.PointsRecordService;
import com.jyss.ziwei.utils.CommTool;
import com.jyss.ziwei.utils.Utils;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hnews")
public class HealthNewAction {

    @Autowired
    private HealthNewService healthNewService;
    @Autowired
    private MobileLoginService mobileLoginService;
    @Autowired
    private PointsRecordService pointsRecordService;


    /**
     * 查询所有类目
     */
    @RequestMapping("/clist")
    @ResponseBody
    public ResponseResult selectNewsCategory(){
        return healthNewService.selectNewsCategory();
    }


    /**
     * 查询所有滑动推荐
     */
    @RequestMapping("/recommend")
    @ResponseBody
    public ResponseResult selectRecommend(){
        return healthNewService.selectRecommend();
    }



    /**
     * 根据类目查询健康知识
     */
    @RequestMapping("/newlist")
    @ResponseBody
    public ResponseResult selectHealthNews(@RequestParam("token") String token,
                                           @RequestParam("categoryId") Integer categoryId,
                                           @RequestParam(value = "page", required = true) Integer page,
                                           @RequestParam(value = "pageSize", required = true) Integer pageSize){
        if(StringUtils.isEmpty(token)){
            ResponseResult result = healthNewService.selectHealthNews(null, categoryId, page, pageSize);
            return result;
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            ResponseResult result = healthNewService.selectHealthNews(uId, categoryId, page, pageSize);
            return result;
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 健康知识详情
     */
    @RequestMapping("/details")
    @ResponseBody
    public ResponseResult selectHealthNewsBy(@RequestParam("token") String token, @RequestParam("knowId") Integer knowId,
                                             @RequestParam(value = "page", required = true) Integer page,
                                             @RequestParam(value = "pageSize", required = true) Integer pageSize){
        if(StringUtils.isEmpty(token)){
            ResponseResult result = healthNewService.selectHealthNewsBy(null, knowId, page, pageSize);
            return result;
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            ResponseResult result = healthNewService.selectHealthNewsBy(uId, knowId, page, pageSize);
            return result;
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 添加积分
     */
    @RequestMapping("/zsscore")
    @ResponseBody
    public ResponseResult updateUserPointsRecord(@RequestParam("token") String token,
                                                 @RequestParam("knowId") Integer knowId){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            boolean record = pointsRecordService.updateUserPointsRecord(uId, knowId, "阅读主题知识", 1);
            if(record){
                return ResponseResult.ok(new HashMap<>());
            }
            return ResponseResult.error("-1","今日阅读知识积分已达上限！");

        }
        return ResponseResult.error("1","token失效！");
    }




    /**
     * 我收藏的健康知识
     */
    @RequestMapping("/mycollec")
    @ResponseBody
    public ResponseResult selectHealthNewsBySC(@RequestParam("token") String token,
                                               @RequestParam(value = "page", required = true) Integer page,
                                               @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            ResponseResult result = healthNewService.selectHealthNewsBySC(uId, page, pageSize);
            return result;
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 我评论的健康知识
     */
    @RequestMapping("/mycomment")
    @ResponseBody
    public ResponseResult selectHealthNewsByPL(@RequestParam("token") String token,
                                               @RequestParam(value = "page", required = true) Integer page,
                                               @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            ResponseResult result = healthNewService.selectHealthNewsByPL(uId, page, pageSize);
            return result;
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 我点赞的健康知识
     */
    @RequestMapping("/mypraise")
    @ResponseBody
    public ResponseResult selectHealthNewsByDZ(@RequestParam("token") String token,
                                               @RequestParam(value = "page", required = true) Integer page,
                                               @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            ResponseResult result = healthNewService.selectHealthNewsByDZ(uId, page, pageSize);
            return result;
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 我分享的健康知识
     */
    @RequestMapping("/myshare")
    @ResponseBody
    public ResponseResult selectHealthNewsByFX(@RequestParam("token") String token,
                                               @RequestParam(value = "page", required = true) Integer page,
                                               @RequestParam(value = "pageSize", required = true) Integer pageSize){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            ResponseResult result = healthNewService.selectHealthNewsByFX(uId, page, pageSize);
            return result;
        }
        return ResponseResult.error("1","token失效！");
    }



    //////////后台部分///////////
    /**
     * 添加类目
     */
    @RequestMapping("/cinsert")
    @ResponseBody
    public ResponseEntity insertNewsCategory(HealthNewsCategory healthNewsCategory, @RequestParam("token") String token){

        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            if(healthNewsCategory.getSort() == null){
                healthNewsCategory.setSort(99);
            }
            healthNewsCategory.setFid(0);
            healthNewsCategory.setPicture("");
            healthNewsCategory.setStatus(1);
            healthNewsCategory.setCreatedAt(new Date());
            healthNewsCategory.setLastModifyTime(new Date());
            int count = healthNewService.insert(healthNewsCategory);
            if(count == 1){
                return ResponseEntity.se("");
            }
            return ResponseEntity.op("-1","添加失败！");
        }
        return ResponseEntity.op("0","token失效！");
    }


    /**
     * 修改类目
     */
    @RequestMapping("/cupdate")
    @ResponseBody
    public ResponseEntity updateNewsCategory(@RequestParam("token") String token, HealthNewsCategory healthNewsCategory){
        if(healthNewsCategory.getId() == null){
            return ResponseEntity.op("-1","请先选择一项修改！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            if(healthNewsCategory.getSort() == null){
                healthNewsCategory.setSort(99);
            }
            healthNewsCategory.setCreatedAt(null);
            healthNewsCategory.setLastModifyTime(new Date());
            int count = healthNewService.updateByPrimaryKey(healthNewsCategory);
            if(count == 1){
                return ResponseEntity.se("");
            }
            return ResponseEntity.op("-2","修改失败！");
        }
        return ResponseEntity.op("0","token失效！");
    }


    /**
     * 删除类目
     */
    @RequestMapping("/cdelete")
    @ResponseBody
    public ResponseEntity deleteNewsCategory(@RequestParam("token") String token,
                                             @RequestParam("categoryId") Integer categoryId){
        if(categoryId == null){
            return ResponseEntity.op("-1","请先选择一项！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            HealthNewsCategory newsCategory = new HealthNewsCategory();
            newsCategory.setId(categoryId);
            newsCategory.setStatus(-1);
            int count = healthNewService.updateByPrimaryKey(newsCategory);
            if(count == 1){
                return ResponseEntity.se("");
            }
            return ResponseEntity.op("-2","删除失败！");

        }
        return ResponseEntity.op("0","token失效！");
    }


    /**
     * 文件上传     type:1=图片，2=视频
     */
    @RequestMapping("/upfile")
    @ResponseBody
    public ResponseEntity uploadFile(MultipartFile file, HttpServletRequest request,@RequestParam("type") Integer type ) {
        //文件不能为空
        if(file.isEmpty()){
            return ResponseEntity.op("-2","文件不能为空！");
        }

        Map<String, Object> map = new HashMap<>();
        String filename = file.getOriginalFilename();
        String extName = filename.substring(filename.lastIndexOf("."));
        if(type == 2){
            String extName1 = extName.toLowerCase();
            if(extName1.equals("mp4") || extName1.equals("flv") || extName1.equals("mkv") || extName1.equals("avi")){
                //获取视频时长
                CommonsMultipartFile cf= (CommonsMultipartFile)file;
                DiskFileItem fi = (DiskFileItem)cf.getFileItem();
                File f2 = fi.getStoreLocation();
                Encoder encoder = new Encoder();
                Long length = 0L;
                try {
                    MultimediaInfo m = encoder.getInfo(f2);
                    length = m.getDuration()/1000;
                    map.put("length",length);
                } catch (Exception e) {
                    return ResponseEntity.op("-1","文件上传失败！");
                }
            }
            return ResponseEntity.op("-3","视频格式必须为“mp4、flv、mkv、avi”！");

        }else{
            map.put("length",0);
        }

        String filePath = request.getSession().getServletContext().getRealPath("/");
        int index = filePath.indexOf("ZIWEI");

        filePath = filePath.substring(0, index) + "uploadNews" + "/";
        String imgPath = filePath + System.currentTimeMillis() + CommTool.getYzm() + extName;
        if (Utils.saveUpload(file, imgPath)) {
            String path = imgPath.substring(imgPath.indexOf("uploadNews"));
            path = Constant.httpUrl + path;
            map.put("path",path);
            return ResponseEntity.se(map);
        }
        return ResponseEntity.op("-1","文件上传失败！");
    }



    /**
     * 添加健康知识
     */
    @RequestMapping("/ninsert")
    @ResponseBody
    public ResponseEntity insertHealthNews(@RequestParam("token") String token, HealthNews healthNews){
        if(StringUtils.isEmpty(healthNews.getCategoryId())){
            return ResponseEntity.op("-1","请先选择一个类目！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            if(!StringUtils.isEmpty(healthNews.getPic())){
                String pic = healthNews.getPic();
                healthNews.setPic(pic.substring(pic.indexOf("uploadNews")));
            }
            if(!StringUtils.isEmpty(healthNews.getInPic())){
                String inPic = healthNews.getInPic();
                healthNews.setInPic(inPic.substring(inPic.indexOf("uploadNews")));
            }
            healthNews.setStatus(1);
            healthNews.setCreatedAt(new Date());
            healthNews.setLastModifyTime(new Date());
            int count = healthNewService.insertHealthNews(healthNews);
            if(count == 1){
                return ResponseEntity.se("");
            }
            return ResponseEntity.op("-2","添加失败！");

        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 查询健康知识列表
     */
    @RequestMapping("/nlist")
    @ResponseBody
    public ResponseEntity getHealthNewsList(@RequestParam("categoryId")Integer categoryId,
                                            @RequestParam(value = "page", required = true) Integer page,
                                            @RequestParam(value = "pageSize", required = true) Integer pageSize){
        if(StringUtils.isEmpty(categoryId)){
            return ResponseEntity.op("-1","请先选择一个类目！");
        }
        return healthNewService.getHealthNewsList(categoryId, page, pageSize);
    }



    /**
     * 查询健康知识详情和评论
     */
    @RequestMapping("/ndetails")
    @ResponseBody
    public ResponseEntity updateHealthNewsById(@RequestParam("knowId")Integer knowId,
                                               @RequestParam(value = "page", required = true) Integer page,
                                               @RequestParam(value = "pageSize", required = true) Integer pageSize){
        if(knowId == null){
            return ResponseEntity.op("-1","请先选择一个！");
        }
        return healthNewService.getHealthNewsById(knowId,page,pageSize);
    }



    /**
     * 修改健康知识
     */
    @RequestMapping("/nupdate")
    @ResponseBody
    public ResponseEntity getHealthNewsById(@RequestParam("token") String token, HealthNews healthNews){
        if(healthNews.getId() == null){
            return ResponseEntity.op("-1","请先选择一个！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            healthNews.setLastModifyTime(new Date());
            int count = healthNewService.updateByPrimaryKey(healthNews);
            if(count == 1){
                return ResponseEntity.se("");
            }
            return ResponseEntity.op("-2","修改失败！");

        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 删除健康知识
     */
    @RequestMapping("/ndelete")
    @ResponseBody
    public ResponseEntity deleteHealthNews(@RequestParam("token") String token, @RequestParam("knowId") Integer knowId){
        if(knowId == null){
            return ResponseEntity.op("-1","请先选择一个！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            HealthNews healthNews = new HealthNews();
            healthNews.setId(knowId);
            healthNews.setStatus(0);
            int count = healthNewService.updateByPrimaryKey(healthNews);
            if(count == 1){
                return ResponseEntity.se("");
            }
            return ResponseEntity.op("-2","删除失败！");

        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 设置推荐    type:1=设置推荐，2=取消推荐
     */
    @RequestMapping("/upcommend")
    @ResponseBody
    public ResponseEntity updateRecommend(@RequestParam("token") String token, @RequestParam("knowId") Integer knowId,
                                          @RequestParam("type") Integer type){
        if(knowId == null){
            return ResponseEntity.op("-1","请先选择一个！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();

            int count = 0;
            if(type == 1){
                count = healthNewService.updateRecommend(2,1,knowId);
            }else if(type == 2){
                count = healthNewService.updateRecommend(1,2,knowId);
            }
            if(count == 1){
                return ResponseEntity.se("");
            }
            return ResponseEntity.op("-2","设置失败！");
        }
        return ResponseEntity.op("0","token失效！");
    }




}
