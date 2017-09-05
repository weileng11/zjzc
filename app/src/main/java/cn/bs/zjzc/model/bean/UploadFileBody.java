package cn.bs.zjzc.model.bean;

import java.io.File;

/**
 * Created by Ming on 2016/6/15.
 */
public class UploadFileBody {
    public String fileName;
    public File file;

    public UploadFileBody() {
    }

    public UploadFileBody(String fileName, File file) {
        this.fileName = fileName;
        this.file = file;
    }

    @Override
    public String toString() {
        return "{" +
                "file:" + file.getAbsolutePath() +
                ", fileName:\"" + fileName + '\"' +
                '}';
    }
}
