package com.example.db.dto;

import com.example.db.entity.Account;
import lombok.Data;

@Data
public class KakaoAccountDTO {

    private Long id;
    private Account kakao_account;
    private Properties properties;

    @Data
    public static class Properties{
        private String nickname;
        private String profile_image;
    }
    @Data
    public static class KakaoAccount{
        private Profile profile;
        private String email;

        @Data
        public static class Profile{
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
        }
    }
}
