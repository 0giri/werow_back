package com.werow.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "{\"id\":2048524511,\"connected_at\":\"2021-12-24T08:20:10Z\",\"properties\":{\"nickname\":\"안영길\",\"profile_image\":\"http://k.kakaocdn.net/dn/mHUc0/btreJY2VrLO/ZPKqRByUdGiH6qmMACkAK0/img_640x640.jpg\",\"thumbnail_image\":\"http://k.kakaocdn.net/dn/mHUc0/btreJY2VrLO/ZPKqRByUdGiH6qmMACkAK0/img_110x110.jpg\"},\"kakao_account\":{\"profile_nickname_needs_agreement\":false,\"profile_image_needs_agreement\":false,\"profile\":{\"nickname\":\"안영길\",\"thumbnail_image_url\":\"http://k.kakaocdn.net/dn/mHUc0/btreJY2VrLO/ZPKqRByUdGiH6qmMACkAK0/img_110x110.jpg\",\"profile_image_url\":\"http://k.kakaocdn.net/dn/mHUc0/btreJY2VrLO/ZPKqRByUdGiH6qmMACkAK0/img_640x640.jpg\",\"is_default_image\":false},\"has_email\":true,\"email_needs_agreement\":false,\"is_email_valid\":true,\"is_email_verified\":true,\"email\":\"dog9463@naver.com\"}}";
    }
}
