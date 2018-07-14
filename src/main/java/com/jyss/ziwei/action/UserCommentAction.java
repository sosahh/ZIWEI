package com.jyss.ziwei.action;

import com.jyss.ziwei.entity.MobileLogin;
import com.jyss.ziwei.entity.ResponseEntity;
import com.jyss.ziwei.entity.ResponseResult;
import com.jyss.ziwei.service.MobileLoginService;
import com.jyss.ziwei.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class UserCommentAction {

    @Autowired
    private MobileLoginService mobileLoginService;
    @Autowired
    private UserCommentService commentService;


    /**
     * 收藏
     */
    @RequestMapping("/collection")
    @ResponseBody
    public ResponseResult insertUserCollection(@RequestParam("token") String token,@RequestParam("knowId") Integer knowId){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.insertUserCollection(uId, knowId);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 取消收藏
     */
    @RequestMapping("/upcollec")
    @ResponseBody
    public ResponseResult updateUserCollection(@RequestParam("token") String token,@RequestParam("knowId") Integer knowId){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.updateUserCollection(uId, knowId);
        }
        return ResponseResult.error("1","token失效！");

    }



    /**
     * 点赞
     */
    @RequestMapping("/praise")
    @ResponseBody
    public ResponseResult insertUserPraise(@RequestParam("token") String token,@RequestParam("knowId") Integer knowId){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.insertUserPraise(uId, knowId);
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 取消点赞
     */
    @RequestMapping("/uppraise")
    @ResponseBody
    public ResponseResult updateUserPraise(@RequestParam("token") String token,@RequestParam("knowId") Integer knowId){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.updateUserPraise(uId, knowId);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 添加评论
     */
    @RequestMapping("/incomment")
    @ResponseBody
    public ResponseResult insertUserComment(@RequestParam("token") String token,@RequestParam("knowId") Integer knowId,
                                            @RequestParam("nickname") String nickname,@RequestParam("content") String content){
        if(knowId == null || content == null || content.trim().length() == 0){
            return ResponseResult.error("-1","文字不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.insertUserComment(uId, knowId, nickname, content);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 添加分享
     */
    @RequestMapping("/inshare")
    @ResponseBody
    public ResponseResult insertUserShare(@RequestParam("token") String token, @RequestParam("knowId") Integer knowId,
                                          @RequestParam("type") Integer type){
        if(knowId == null){
            return ResponseResult.error("-1","请选择一项！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.insertUserShare(uId, knowId, type);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 添加反馈
     */
    @RequestMapping("/infeed")
    @ResponseBody
    public ResponseResult insertFeedBack(@RequestParam("token") String token, @RequestParam("nickname") String nickname,
                                         @RequestParam("message") String message){
        if(message == null || message.trim().length() == 0){
            return ResponseResult.error("-1","文字不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.insertFeedBack(uId, nickname, message);
        }
        return ResponseResult.error("1","token失效！");
    }


    /**
     * 查询反馈
     */
    @RequestMapping("/feeds")
    @ResponseBody
    public ResponseResult selectFeedBack(@RequestParam("token") String token,
                                         @RequestParam(value = "page", required = true) Integer page,
                                         @RequestParam(value = "pageSize", required = true) Integer pageSize){

        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.selectFeedBack(uId, page, pageSize);
        }
        return ResponseResult.error("1","token失效！");
    }



    /**
     * 读取反馈消息
     */
    @RequestMapping("/readfeed")
    @ResponseBody
    public ResponseResult updateFeedStatus(@RequestParam("token")String token){
        List<MobileLogin> loginList = mobileLoginService.findUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.updateFeedStatus(uId);
        }
        return ResponseResult.error("1","token失效！");
    }


    /////////////后台部分////////////

    /**
     * 回复评论
     */
    @RequestMapping("/upcomment")
    @ResponseBody
    public ResponseEntity updateUserComment(@RequestParam("token") String token, @RequestParam("commentId") Integer commentId,
                                            @RequestParam("content") String content){
        if(commentId == null || content == null || content.trim().length() == 0){
            return ResponseEntity.op("-1","文字不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.updateUserComment(uId, commentId, content);
        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 删除评论
     */
    @RequestMapping("/delcomment")
    @ResponseBody
    public ResponseEntity deleteUserComment(@RequestParam("token") String token,
                                            @RequestParam("commentId") Integer commentId){
        if(commentId == null){
            return ResponseEntity.op("-1","请选择一条评论删除！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.deleteUserComment(commentId);
        }
        return ResponseEntity.op("0","token失效！");
    }


    /**
     * 查询反馈    type:1=已回复，2=未回复，3=所有
     */
    @RequestMapping("/flist")
    @ResponseBody
    public ResponseEntity selectFeedBackBy(@RequestParam("token") String token, @RequestParam("type") Integer type,
                                           @RequestParam(value = "page", required = true) Integer page,
                                           @RequestParam(value = "pageSize", required = true) Integer pageSize){

        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long manageId = mobileLogin.getuId();

            if(type == 1){
                return commentService.selectFeedBackHT(manageId, page, pageSize, 1);
            }else if(type == 2){
                return commentService.selectFeedBackHT(manageId, page, pageSize, 0);
            }else{
                return commentService.selectFeedBackHT(manageId, page, pageSize, null);
            }
        }
        return ResponseEntity.op("0","token失效！");
    }



    /**
     * 回复反馈
     */
    @RequestMapping("/rback")
    @ResponseBody
    public ResponseEntity updateFeedBack(@RequestParam("token") String token, @RequestParam("id") Integer id,
                                         @RequestParam("content") String content){
        if(content == null || content.trim().length() == 0){
            return ResponseEntity.op("-1","文字不能为空！");
        }
        List<MobileLogin> loginList = mobileLoginService.selectWebUserByToken(token);
        if (loginList != null && loginList.size() == 1){
            MobileLogin mobileLogin = loginList.get(0);
            Long uId = mobileLogin.getuId();
            return commentService.updateFeedBack(id, uId, content);
        }
        return ResponseEntity.op("0","token失效！");
    }




}
