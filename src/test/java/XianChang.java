import com.github.gin.qcsv.annotation.CSV;

import java.util.Date;

/**
 * Created by FSTMP on 2016/8/25.
 */
public class XianChang {
    @CSV(name = "报案号")
    private String bah;

    @CSV(name = "查勘开始时间")
    private Date rwkssj;

    @CSV(name = "查勘调度时间")
    private Date rwddsj;

    @CSV(name = "查勘完成时间")
    private Date hstgsj;

    @CSV(name = "第一查勘人姓名")
    private String dyckyxm;

    @CSV(name = "第一查勘人工号")
    private String dyckygh;

    @CSV(name = "查勘员分公司")
    private String dyckyfgs;

    @CSV(name = "第二查勘人姓名")
    private String deckyxm;

    @CSV(name = "第二查勘人工号")
    private String deckygh;

    @CSV(name = "是否已决")
    private String issettled;

    @CSV(name = "承保分公司")
    private String cbfgs;

    private String dataType;

    public String getBah() {
        return bah;
    }

    public void setBah(String bah) {
        this.bah = bah;
    }

    public Date getRwkssj() {
        return rwkssj;
    }

    public void setRwkssj(Date rwkssj) {
        this.rwkssj = rwkssj;
    }

    public Date getRwddsj() {
        return rwddsj;
    }

    public void setRwddsj(Date rwddsj) {
        this.rwddsj = rwddsj;
    }

    public Date getHstgsj() {
        return hstgsj;
    }

    public void setHstgsj(Date hstgsj) {
        this.hstgsj = hstgsj;
    }

    public String getDyckyxm() {
        return dyckyxm;
    }

    public void setDyckyxm(String dyckyxm) {
        this.dyckyxm = dyckyxm;
    }

    public String getDyckygh() {
        return dyckygh;
    }

    public void setDyckygh(String dyckygh) {
        this.dyckygh = dyckygh;
    }

    public String getDyckyfgs() {
        return dyckyfgs;
    }

    public void setDyckyfgs(String dyckyfgs) {
        this.dyckyfgs = dyckyfgs;
    }

    public String getDeckyxm() {
        return deckyxm;
    }

    public void setDeckyxm(String deckyxm) {
        this.deckyxm = deckyxm;
    }

    public String getDeckygh() {
        return deckygh;
    }

    public void setDeckygh(String deckygh) {
        this.deckygh = deckygh;
    }

    public String getIssettled() {
        return issettled;
    }

    public void setIssettled(String issettled) {
        this.issettled = issettled;
    }

    public String getCbfgs() {
        return cbfgs;
    }

    public void setCbfgs(String cbfgs) {
        this.cbfgs = cbfgs;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
