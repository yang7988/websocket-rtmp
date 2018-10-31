package com.yang.rtmp.web.license;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

public class LicenseManagerHolder {
    private static LicenseManager licenseManager;

    private LicenseManagerHolder() {
    }

    public static synchronized LicenseManager getInstance(LicenseParam param) {
        if (licenseManager == null) {
            licenseManager = new LicenseManager(param);
        }
        return licenseManager;
    }

}