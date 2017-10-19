package utils;
/*
    created by xdCao on 2017/10/19
*/

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileUtil {

    public static final Logger LOGGER= LoggerFactory.getLogger(FileUtil.class);

    public static String getRealFileName(String filename) {
        return FilenameUtils.getName(filename);
    }

    public static File createFile(String filePath) {
        File file;
        try {
            file=new File(filePath);
            File parentDir=file.getParentFile();
            if (!parentDir.exists()){
                FileUtils.forceMkdir(parentDir);
            }
        }catch (Exception e){
            LOGGER.error("创建文件失败！！",e);
            throw new RuntimeException(e);
        }
        return file;
    }
}
