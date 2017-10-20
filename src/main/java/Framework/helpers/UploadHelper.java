package Framework.helpers;
/*
    created by xdCao on 2017/10/19
*/


import Framework.entity.FileParam;
import Framework.entity.FormParam;
import Framework.entity.Param;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Framework.utils.CollectionUtil;
import Framework.utils.FileUtil;
import Framework.utils.StreamUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class UploadHelper {

    private static final Logger LOGGER= LoggerFactory.getLogger(UploadHelper.class);

    private static ServletFileUpload servletFileUpload;

    public static void init(ServletContext servletContext){
        File repo= (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload=new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,repo));
        int uploadLimit=ConfigHelper.getAppUploadLimit();
        if (uploadLimit!=0){
            servletFileUpload.setFileSizeMax(uploadLimit*1024*1024);
        }
    }

    public static boolean isMultipart(HttpServletRequest request){
        return ServletFileUpload.isMultipartContent(request);
    }

    public static Param createParam(HttpServletRequest request){
        List<FormParam> formParamList=new ArrayList<FormParam>();
        List<FileParam> fileParamList=new ArrayList<FileParam>();
        try {
            Map<String,List<FileItem>> fileItemListMap=servletFileUpload.parseParameterMap(request);
            if (CollectionUtil.isNotEmpty(fileItemListMap)){
                for (Map.Entry<String,List<FileItem>> entry:fileItemListMap.entrySet()){
                    String fieldName=entry.getKey();
                    List<FileItem> fileItemList=entry.getValue();
                    if (CollectionUtils.isNotEmpty(fileItemList)){
                        for (FileItem fileItem:fileItemList){
                            if (fileItem.isFormField()){
                                String fieldValue=fileItem.getString("utf-8");
                                formParamList.add(new FormParam(fieldName,fieldValue));
                            }else {
                                String fileName= FileUtil.getRealFileName(new String(fileItem.getName().getBytes(),"utf-8"));
                                if (StringUtils.isNotEmpty(fileName)){
                                    long fileSize=fileItem.getSize();
                                    String contentType=fileItem.getContentType();
                                    InputStream inputStream=fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName,fileName,fileSize,contentType,inputStream));
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error("构造param对象失败！！",e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList,fileParamList);
    }

    public static void uplaodFile(String basePath,FileParam fileParam){
        try {
            if (fileParam!=null){
                String filePath=basePath+fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream=new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream=new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream,outputStream);
            }
        }catch (Exception e){
            LOGGER.error("上传文件失败！！",e);
            throw new RuntimeException(e);
        }
    }

    public static void uploadFile(String basePath,List<FileParam> fileParamList){
        try {
            if (CollectionUtils.isNotEmpty(fileParamList)){
                for (FileParam fileParam:fileParamList){
                    uplaodFile(basePath,fileParam);
                }
            }
        }catch (Exception e){
            LOGGER.error("上传文件失败！！",e);
            throw new RuntimeException(e);
        }
    }


}
