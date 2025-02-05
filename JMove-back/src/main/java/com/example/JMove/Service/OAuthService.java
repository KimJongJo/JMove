package com.example.JMove.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class OAuthService {

    private static final String getKakaoAccessTokenURL = "https://kauth.kakao.com/oauth/token";
    private static final String getNaverAccessTokenURL = "https://nid.naver.com/oauth2.0/token";
    private static final String getGoogleAccessTokenURL = "https://oauth2.googleapis.com/token";


    // 로그인 api redirect uri
    @Value("${login.redirect.uri}")
    private String REDIRECT_URI;

    // 카카오 api key
    @Value("${login.api.client.id.kakao}")
    private String CLIENT_ID_KAKAO;

    // 네이버 api key
    @Value("${login.api.client.id.naver}")
    private String CLIENT_ID_NAVER;

    // 네이버 api secret
    @Value("${login.api.client.pw.naver}")
    private String CLIENT_PW_NAVER;

    // 구글 api key
    @Value("${login.api.client.id.google}")
    private String CLIENT_ID_GOOGLE;

    // 구글 api secret
    @Value("${login.api.client.pw.google}")
    private String CLIENT_PW_GOOGLE;

    // 액세스 토큰을 발급
    public String getAccessToken(String authCode, String provider){

        String accessToken;

        switch(provider){
            case "kakao" : accessToken = getKakaoToken(authCode); break;
            case "naver" : accessToken = getNaverToken(authCode); break;
            case "google" : accessToken = getGoogleToken(authCode); break;
            default : return null;
        }

        return accessToken;

    }

    // 액세스 토큰으로 사용자 정보 가져오기
    public Map<String, Object> getUserInfo(String accessToken, String provider){

        Map<String, Object> userInfo = null;

        switch(provider){
            case "kakao" : userInfo = getKakaoInfo(accessToken); break;
            case "naver" : userInfo = getNaverInfo(accessToken); break;
            case "google" : userInfo = getGoogleInfo(accessToken); break;
            default : return null;
        }

        return userInfo;


    }

    // 카카오 액세스 토큰 발급
    public String getKakaoToken(String authCode) {

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getKakaoAccessTokenURL + "?grant_type=authorization_code&client_id=" + CLIENT_ID_KAKAO + "&redirect_uri=" + REDIRECT_URI + "&code=" + authCode))
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                    .method("POST", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);

            return (String) responseMap.get("access_token");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 네이버 액세스 토큰 발급
    public String getNaverToken(String authCode) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getNaverAccessTokenURL + "?code=" + authCode + "&grant_type=authorization_code&client_id=" + CLIENT_ID_NAVER + "&client_secret=" + CLIENT_PW_NAVER
                           ))
                    .method("POST", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);

            return (String) responseMap.get("access_token");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 구글 액세스 토큰 발급
    public String getGoogleToken(String authCode) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getGoogleAccessTokenURL + "?code=" + authCode + "&client_id=" + CLIENT_ID_GOOGLE + "&client_secret=" + CLIENT_PW_GOOGLE
                     + "&redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .method("POST", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.readValue(response.body(), Map.class);

            return (String) responseMap.get("access_token");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 카카오 유저 정보
    public Map<String, Object> getKakaoInfo(String accessToken){

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://kapi.kakao.com/v2/user/me"))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), Map.class);

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    // 네이버 유저 정보
    public Map<String, Object> getNaverInfo(String accessToken){

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://openapi.naver.com/v1/nid/me"))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), Map.class);

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    // 구글 유저 정보
    public Map<String, Object> getGoogleInfo(String accessToken){

        try{
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.googleapis.com/oauth2/v3/userinfo"))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), Map.class);

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
