package entity;
/*
    created by xdCao on 2017/10/19
*/

public class FormParam {

    private String fieldName;
    private Object fieldValue;

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public FormParam(String fieldName, Object fieldValue) {

        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
