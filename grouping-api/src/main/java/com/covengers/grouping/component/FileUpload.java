package com.covengers.grouping.component;

import com.covengers.grouping.constant.ResponseCode;
import com.covengers.grouping.exception.CommonException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;

@Component
public class FileUpload {
    private static final String SAVE_GROUP_IMAGE_PATH = "resources/groupimages/";

    public String imgRestore( MultipartFile groupImg) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedImgString);
            String saveFileName = createFileName();
            System.out.println(saveFileName);
            FileUtils.writeByteArrayToFile(new File(SAVE_GROUP_IMAGE_PATH + saveFileName), decodedBytes);
            return saveFileName;
        } catch (IOException e) {
            throw new CommonException(ResponseCode.UNKNOWN_ERROR);
        }
    }

    private String createFileName() {
        String fileName = "";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH);
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        fileName += ".png";

        return fileName;
    }

}
