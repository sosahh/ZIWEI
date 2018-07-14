package com.jyss.ziwei.action;

import com.jyss.ziwei.entity.ResponseResult;
import com.jyss.ziwei.entity.UserDevices;
import com.jyss.ziwei.service.UserDevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserDevicesAction {
    @Autowired
    private UserDevicesService udService;

    /**
     * 添加设备
     * @param token
     * @param ud
     * @return
     */
    @RequestMapping("/addDevice")
    @ResponseBody
    public ResponseResult addDevice(@RequestParam("token") String token, UserDevices ud) {
        return udService.addDevice(token,ud);

    }
    /**
     * 修改设备
     * @param token
     * @param ud
     * @return
     */
    @RequestMapping("/upDevice")
    @ResponseBody
    public ResponseResult upDevice(@RequestParam("token") String token, UserDevices ud) {
        return udService.upDevice(token,ud);

    }

    /**
     * 删除设备
     * @param token
     * @param udId
     * @return
     */
    @RequestMapping("/delDevice")
    @ResponseBody
    public ResponseResult delDevice(@RequestParam("token") String token, @RequestParam("udId") String udId) {
        return udService.delDevice(token,udId);

    }

    /**
     * 获取我的设备
     * @param token
     * @return
     */
    @RequestMapping("/getMyDevice")
    @ResponseBody
    public ResponseResult getMyDevice(@RequestParam("token") String token) {
        return udService.getMyDevice(token);

    }




}