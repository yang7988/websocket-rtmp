package com.yang.rtmp.web.license;

import de.schlichtherle.license.LicenseContent;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class LicenseInstallService {
    private static final Logger logger = LoggerFactory.getLogger(LicenseInstallService.class);
    @Autowired
    private LicenseConfiguration licenseConfiguration;

    public LicenseContent installLicense(String license) {
        if(StringUtils.isBlank(license)) {
            logger.info("++++++++无可用的license++++++++");
        }
        LicenseVerifyParam verifyParam = new LicenseVerifyParam();
        verifyParam.setSubject(licenseConfiguration.getSubject());
        verifyParam.setLicensePath(license);
        verifyParam.setPublicAlias(licenseConfiguration.getPublicAlias());
        verifyParam.setPublicKeysStorePath(licenseConfiguration.getPublicKeysStorePath());
        verifyParam.setStorePass(licenseConfiguration.getStorePass());
        LicenseVerify licenseVerify = new LicenseVerify();
        licenseVerify.initLicenseParam(verifyParam);
        boolean falg = licenseVerify.verify();
        return null;
    }

}