package org.sunhp.rcampus.vo;

/**
 * Created by yufeng.fyf on 17/4/29.
 */
public class ExamResult {
    private Boolean status;
    private OcpuResult ocpuResult;
    private Boolean judgeStatus;
    private String judgeMsg;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public OcpuResult getOcpuResult() {
        return ocpuResult;
    }

    public void setOcpuResult(OcpuResult ocpuResult) {
        this.ocpuResult = ocpuResult;
    }

    public Boolean getJudgeStatus() {
        return judgeStatus;
    }

    public void setJudgeStatus(Boolean judgeStatus) {
        this.judgeStatus = judgeStatus;
    }

    public String getJudgeMsg() {
        return judgeMsg;
    }

    public void setJudgeMsg(String judgeMsg) {
        this.judgeMsg = judgeMsg;
    }
}
