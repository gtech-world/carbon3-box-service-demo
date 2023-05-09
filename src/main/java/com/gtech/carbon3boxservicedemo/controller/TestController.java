package com.gtech.carbon3boxservicedemo.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SignUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.http.HttpUtil;
import com.gtech.carbon3boxservicedemo.domain.CommonResponse;
import com.gtech.carbon3boxservicedemo.domain.TestRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @PostMapping("/reportTest")
    public CommonResponse test(@RequestBody TestRequest request) {
        var rsa = new RSA();
        var sign = SignUtil.sign(SignAlgorithm.MD5withRSA, rsa.getPrivateKey().getEncoded(), rsa.getPublicKey().getEncoded());
        var signed = sign.sign(request.getName());
        var result = HttpUtil.get("https://api.gtech.world/ping/test?sign=" + Base64.encode(signed));
        return CommonResponse.success(result);
    }
}
