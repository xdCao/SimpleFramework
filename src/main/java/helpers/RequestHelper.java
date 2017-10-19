package helpers;
/*
    created by xdCao on 2017/10/19
*/

import entity.FormParam;
import entity.Param;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ArrayUtil;
import utils.CodecUtil;
import utils.StreamUtil;
import utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

public final class RequestHelper {

    private static final Logger LOGGER= LoggerFactory.getLogger(RequestHelper.class);

    public static Param createParams(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList=new ArrayList<FormParam>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {

        List<FormParam> formParamList=new ArrayList<FormParam>();
        String body= CodecUtil.decodeUrl(StreamUtil.getString(request.getInputStream()));
        if (StringUtils.isNotEmpty(body)){
            String[] kvs=StringUtils.split(body,"&");
            if (ArrayUtil.isNotEmpty(kvs)){
                for(String kv:kvs){
                    String[] array=StringUtils.split(kv,"=");
                    if (ArrayUtil.isNotEmpty(array)&&array.length==2){
                        String fieldName=array[0];
                        String fieldValue=array[1];
                        formParamList.add(new FormParam(fieldName,fieldValue));
                    }
                }
            }
        }
        return formParamList;

    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request) {

        List<FormParam> formParamList=new ArrayList<FormParam>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String fieldName = parameterNames.nextElement();
            String[] fieldValues=request.getParameterValues(fieldName);
            if (ArrayUtil.isNotEmpty(fieldValues)){
                Object fieldValue;
                if (fieldValues.length==1){
                    fieldValue=fieldValues[0];
                }else {
                    StringBuilder stringBuilder = new StringBuilder("");
                    for (int i=0;i<fieldValues.length;i++){
                        stringBuilder.append(fieldValues[i]);
                        if (i!=fieldValues.length-1){
                            stringBuilder.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue=stringBuilder.toString();
                }
                formParamList.add(new FormParam(fieldName,fieldValue));
            }
        }
        return formParamList;

    }

}
