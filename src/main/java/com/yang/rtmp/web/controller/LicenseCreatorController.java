package com.yang.rtmp.web.controller;

import com.yang.rtmp.web.license.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/license")
public class LicenseCreatorController {
    /**
     * 证书生成路径
     */
    @Value("${license.licensePath}")
    private String licensePath;

    /**
     * 获取服务器硬件信息
     *
     * @param osName 操作系统类型，如果为空则自动判断
     * @return com.ccx.models.license.LicenseCheckModel
     * @author zifangsky
     * @date 2018/4/26 13:13
     * @since 1.0.0
     */
    @RequestMapping(value = "/getServerInfos", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public LicenseCheckModel getServerInfos(@RequestParam(value = "osName", required = false) String osName) {
        //操作系统类型
        if (StringUtils.isBlank(osName)) {
            osName = System.getProperty("os.name");
        }
        osName = osName.toLowerCase();

        AbstractServerInfos abstractServerInfos = osName.startsWith("windows") ? new WindowsServerInfos() : new LinuxServerInfos();

        return abstractServerInfos.getServerInfos();
    }

    /**
     * 生成证书
     *
     * @param param 生成证书需要的参数，如：{"subject":"ccx-models","privateAlias":"privateKey","keyPass":"5T7Zz5Y0dJFcqTxvzkH5LDGJJSGMzQ","storePass":"3538cef8e7","licensePath":"C:/Users/zifangsky/Desktop/license.lic","privateKeysStorePath":"C:/Users/zifangsky/Desktop/privateKeys.keystore","issuedTime":"2018-04-26 14:48:12","expiryTime":"2018-12-31 00:00:00","consumerType":"User","consumerAmount":1,"description":"这是证书描述信息","licenseCheckModel":{"ipAddress":["192.168.245.1","10.0.5.22"],"macAddress":["00-50-56-C0-00-01","50-7B-9D-F9-18-41"],"cpuSerial":"BFEBFBFF000406E3","mainBoardSerial":"L1HF65E00X9"}}
     * @return java.util.Map<java.lang.String   ,   java.lang.Object>
     * @author zifangsky
     * @date 2018/4/26 13:13
     * @since 1.0.0
     */
    @RequestMapping(value = "/generateLicense", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Map<String, Object> generateLicense(@RequestBody(required = true) LicenseCreatorParam param) {
        Map<String, Object> resultMap = new HashMap<>(2);

        if (StringUtils.isBlank(param.getLicensePath())) {
            param.setLicensePath(licensePath);
        }

        LicenseCreator licenseCreator = new LicenseCreator(param);
        boolean result = licenseCreator.generateLicense();

        if (result) {
            resultMap.put("result", "ok");
            resultMap.put("msg", param);
        } else {
            resultMap.put("result", "error");
            resultMap.put("msg", "证书文件生成失败！");
        }

        return resultMap;
    }


}