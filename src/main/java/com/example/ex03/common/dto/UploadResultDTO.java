package com.example.ex03.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {

    private String fileName;

    private String uuid;

    private String folderPath;

    //public으로 작성하여야 view단에서 ImageURL 속성으로 받을 수 있음.
    public String getImageURL(){
        try{
            return URLEncoder.encode(folderPath + "/" + uuid+ "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    //public으로 작성하여야 view단에서 ImageURL 속성으로 받을 수 있음.
    public String getThumbnailURL(){
        try{
            return URLEncoder.encode(folderPath + "/" + "s_" + uuid+ "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
