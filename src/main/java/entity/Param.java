package entity;
/*
    created by xdCao on 2017/10/18
*/

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import utils.CastUtil;
import utils.CollectionUtil;
import utils.StringUtil;

import javax.naming.event.ObjectChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.StringUtil.SEPARATOR;

public class Param {

    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    public Map<String,Object> getFieldMap(){
        Map<String,Object> fieldMap=new HashMap<String,Object>();
        if (CollectionUtils.isNotEmpty(formParamList)){
            for (FormParam formParam:formParamList){
                String fieldName=formParam.getFieldName();
                Object fieldValue=formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName,fieldValue);
            }
        }
        return fieldMap;
    }

    public Map<String,List<FileParam>> getFileMap(){
        Map<String,List<FileParam>> fileMap=new HashMap<String, List<FileParam>>();
        if (CollectionUtils.isNotEmpty(fileParamList)){
            for (FileParam fileParam:fileParamList){
                String fieldName=fileParam.getFieldName();
                List<FileParam> fileParamList;
                if (fileMap.containsKey(fieldName)){
                    fileParamList=fileMap.get(fieldName);
                }else {
                    fileParamList=new ArrayList<FileParam>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName,fileParamList);
            }
        }
        return fileMap;
    }

    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    public FileParam getFile(String fieldName){
        List<FileParam> fileList = getFileList(fieldName);
        if (CollectionUtils.isNotEmpty(fileList)&&fileList.size()==1){
            return fileList.get(0);
        }
        return null;
    }


    public boolean isEmpty(){
        return CollectionUtils.isEmpty(fileParamList)&&CollectionUtils.isEmpty(formParamList);
    }

    public String getString(String name){
        return CastUtil.castString(getFieldMap().get(name));
    }

    public double getDouble(String name){
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    public long getLong(String name){
        return CastUtil.castLong(getFieldMap().get(name));
    }

    public int getInt(String name){
        return CastUtil.castInt(getFieldMap().get(name));
    }

    public boolean getBoolean(String name){
        return CastUtil.casetBoolean(getFieldMap().get(name));
    }

}
