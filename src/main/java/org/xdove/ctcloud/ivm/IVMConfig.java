package org.xdove.ctcloud.ivm;

import lombok.Data;

@Data
public class IVMConfig {
    private String apiurl;
    private String secret;
    private String appkey;

    public IVMConfig(String apiurl, String secret, String appkey) {
        this.apiurl = apiurl;
        this.secret = secret;
        this.appkey = appkey;
    }
}
