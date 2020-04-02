package com.kyriexu.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author KyrieXu
 * @since 2020/4/1 16:46
 **/
public class UploadFileUtil {
    private static StorageClient1 client;

    static {
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            client = new StorageClient1(trackerServer, storageServer);
        }
        catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }

    public static String upload(MultipartFile file) throws IOException, MyException {
        String oldName = file.getOriginalFilename();
        assert oldName != null;
        String postFix = oldName.substring(oldName.lastIndexOf(".") + 1);
        return client.upload_file1(file.getBytes(), postFix, null);
    }
}
