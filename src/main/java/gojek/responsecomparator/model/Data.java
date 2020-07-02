package gojek.responsecomparator.model;

public class Data {
    private String value1;
    private String value2;

    public Data(){
        this.value1 = null;
        this.value2 = null;
    }

    public Data(String url1, String url2){
        this.value1 = url1;
        this.value2 = url2;
    }

    public void setValue1(String value1){
        this.value1 = value1;
    }

    public String getValue1(){
        return this.value1;
    }

    public void setValue2(String value2){
        this.value2 = value2;
    }

    public String getValue2(){
        return this.value2;
    }
}
