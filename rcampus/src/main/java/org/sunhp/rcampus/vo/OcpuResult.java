package org.sunhp.rcampus.vo;

import org.sunhp.rcampus.components.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yufeng.fyf on 17/4/29.
 */
public class OcpuResult {
    private String sessionID;
    private String source;
    private String console;
    private String info;
    private List<String> files = new ArrayList<>();
    private List<String> graphics = new ArrayList<>();
    public OcpuResult(){};

    public OcpuResult(String source, String console) {
        this.source = source;
        this.console = console;
    }

    public OcpuResult(String source, String console, String info) {
        this.source = source;
        this.console = console;
        this.info = info;
    }

    public OcpuResult(String result){
        String[] list  = result.split("\n");
        if(list.length>0){
            this.sessionID=list[0].split("/")[3];
        }
        for(String str:list){
            String[] subList = str.split("/");
            if(subList.length>=4&&"files".equals(subList[4])){
                this.files.add(Constants.OPENCPU_HOST+str);
            }
        }
        for(String str:list){
            String[] subList = str.split("/");
            if(subList.length>=4&&"graphics".equals(subList[4])){
                this.graphics.add(Constants.OPENCPU_HOST+str+"/png");
            }
        }
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<String> getGraphics() {
        return graphics;
    }

    public void setGraphics(List<String> graphics) {
        this.graphics = graphics;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
