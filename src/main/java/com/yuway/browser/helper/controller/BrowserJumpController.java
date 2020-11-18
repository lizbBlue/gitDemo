package com.yuway.browser.helper.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 跳转到指定浏览器相关控制类
 * @author lingy
 * @date 2020-05-19 11:45
 */
@RestController
public class BrowserJumpController {
    /**
     * 当前谷歌浏览器exe文件的全路径
     */
    @Value("${chrome.path}")
    private String chromePath;
    /**
     * 默认打开谷歌浏览器跳转的页面地址
     */
    @Value("${chrome.default.jump.url}")
    private String chromeDefaultJumpUrl;

    /**
     * 打开谷歌浏览器，并打开指定的页面
     * @param url 要跳转的页面链接【需要对链接中的特殊字符进行转义，如：&+=?这些符号】
     * @param maxWindow 是否最大窗口打开，true以最大窗口打开
     * @param windowBounds 设置窗口大小，可选位置和可选比例因子。“1024x768”创建一个大小为1024x768的窗口。
     *                     “100+200-1024x768”将窗口定位在100,200。对于高DPI显示，“1024x768*2”将比例因子设置为2。
     *                     “800,0+800-800x800”适用于800x800分辨率的两个显示器。
     *                     “800,0+800-800x800,0+1600-800x800”适用于800x800分辨率的三个显示器
     *                     【需要对链接中的特殊字符进行转义，如：&+=?这些符号】
     * @return
     * @throws IOException
     */
    @GetMapping("/jumpChrome")
    @ResponseBody
    @CrossOrigin // 使用注解方式添加跨域访问消息头
    public String jumpChrome(@RequestParam(value = "url", required = false) String url,
                             @RequestParam(value = "urlTwo", required = false) String urlTwo,
                             @RequestParam(value = "maxWindow", required = false, defaultValue = "false") boolean maxWindow,
                             @RequestParam(value = "windowBounds", required = false) String windowBounds
                             ) throws IOException {
        String jumpUrl = StringUtils.isEmpty(url) ? chromeDefaultJumpUrl : url;
        jumpUrl.replaceAll("userId=1","userId=2");
        List<String> cmd = new ArrayList<>();
        cmd.add(chromePath);
        if(maxWindow)   cmd.add("--start-maximized");// 窗口启动最大化
//        cmd.add("--incognito");// 隐身模式打开
        if(StringUtils.isNotBlank(windowBounds))    cmd.add("--ash-host-window-bounds=\"" + "1024x768" + "\"");
        cmd.add(jumpUrl);
        ProcessBuilder process = new ProcessBuilder(cmd);
        process.start();

        return "打开成功";
    }


    public static void main(String[] args) throws IOException {
        String url="https://byzzbtest.by.gov.cn/zzbweb/index.html?userId=1toUserId=40";
    }
}
