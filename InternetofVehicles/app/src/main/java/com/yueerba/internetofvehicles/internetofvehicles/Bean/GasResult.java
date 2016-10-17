package com.yueerba.internetofvehicles.internetofvehicles.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */

public class GasResult implements Serializable{
    private String resultcode;
    private String reason;
    private int error_code;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    private ResultBean result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        private List<DataBean> data;
        private PageinfoBean pageinfo;

        public PageinfoBean getPageinfo() {
            return pageinfo;
        }

        public void setPageinfo(PageinfoBean pageinfo) {
            this.pageinfo = pageinfo;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable{
            private String id;
            private String name;
            private String area;
            private String areaname;
            private String address;
            private String brandname;
            private String type;
            private String discount;
            private String exhaust;
            private String position;
            private String lon;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAreaname() {
                return areaname;
            }

            public void setAreaname(String areaname) {
                this.areaname = areaname;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBrandname() {
                return brandname;
            }

            public void setBrandname(String brandname) {
                this.brandname = brandname;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getExhaust() {
                return exhaust;
            }

            public void setExhaust(String exhaust) {
                this.exhaust = exhaust;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public PriceBean getPrice() {
                return price;
            }

            public void setPrice(PriceBean price) {
                this.price = price;
            }

            public Object getGastprice() {
                return gastprice;
            }

            public void setGastprice(Object gastprice) {
                this.gastprice = gastprice;
            }

            public String getFwlsmc() {
                return fwlsmc;
            }

            public void setFwlsmc(String fwlsmc) {
                this.fwlsmc = fwlsmc;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            private String lat;

            private PriceBean price;
            private Object gastprice;
            private String fwlsmc;
            private int distance;

            public static class PriceBean implements Serializable {
                private String E90;
                private String E93;
                private String E97;
                private String E0;

                public String getE90() {
                    return E90;
                }

                public void setE90(String E90) {
                    this.E90 = E90;
                }

                public String getE93() {
                    return E93;
                }

                public void setE93(String E93) {
                    this.E93 = E93;
                }

                public String getE97() {
                    return E97;
                }

                public void setE97(String E97) {
                    this.E97 = E97;
                }

                public String getE0() {
                    return E0;
                }

                public void setE0(String E0) {
                    this.E0 = E0;
                }
            }
        }

        public static class PageinfoBean implements Serializable{
            private int pnums;
            private int current;
            private int allpage;

            public int getPnums() {
                return pnums;
            }

            public void setPnums(int pnums) {
                this.pnums = pnums;
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public int getAllpage() {
                return allpage;
            }

            public void setAllpage(int allpage) {
                this.allpage = allpage;
            }
        }
    }

}
