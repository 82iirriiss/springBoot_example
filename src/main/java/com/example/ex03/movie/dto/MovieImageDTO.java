package com.example.ex03.movie.dto;

import lombok.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieImageDTO {
    private String uuid;
    private String imgName;
    private String path;
    public String getImageURL(){
        try{
            return URLEncoder.encode(path + "/" + uuid + "_" + imgName, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL(){
        try{
            return URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, "UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return "";
    }
}
