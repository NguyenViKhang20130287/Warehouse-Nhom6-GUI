package entity;

import lombok.Data;

@Data
public class KQXS {
    private String date;
    private String region;
    private String province;
    private String award;
    private String number;

    @Override
    public String toString() {
        return "Date: " + date + ", " +
                "Region: " + region + ", " +
                "Province: " + province + ", " +
                "Award: " + award + ", " +
                "Number: " + number + "\n";
    }
    public KQXS(KQXS hang) {
        this.date = hang.date;
        this.region = hang.region;
        this.province = hang.province;
        this.award = hang.award;
        this.number = hang.number;
    }

    public KQXS() {
    }
}
