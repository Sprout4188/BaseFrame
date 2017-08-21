package com.sprout.frame.baseframe.entity;

/**
 * Create by Sprout at 2017/8/15
 */
public  class LoginEntity {


    /**
     * result : {"resultCode":0,"resultMessage":"业务执行成功"}
     * entity : {"audit_status":3,"pledge_amount":0,"pledge_paied":true,"order":{"orderid":75222,"vehicleid":75591,"status":3,"create_time":"2017-08-01 12:12:12","fetch_time":"2017-08-01 12:12:12","time_price":26.654789,"return_time":"2017-09-01 12:12:12","mile_price":26.654789,"begin_km":26.654789,"current_km":26.654789,"fetch_lat":26.654789,"fetch_lng":26.654789,"return_lat":69.123456,"return_lng":26.654789,"price_discount":26.654789,"price_pay":26.654789,"position_start_chn":"8iHMpXh5iY","position_end_chn":"pzl0oDuRjM","take_car_time_left":92569,"price_total":26.654789},"reject_reason":"fsdfs","balance":26.54656}
     */

    private ResultBean result;
    private EntityBean entity;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
    }

    public static class ResultBean {
        /**
         * resultCode : 0
         * resultMessage : 业务执行成功
         */

        private int resultCode;
        private String resultMessage;

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMessage() {
            return resultMessage;
        }

        public void setResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }
    }

    public static class EntityBean {
        /**
         * audit_status : 3
         * pledge_amount : 0
         * pledge_paied : true
         * order : {"orderid":75222,"vehicleid":75591,"status":3,"create_time":"2017-08-01 12:12:12","fetch_time":"2017-08-01 12:12:12","time_price":26.654789,"return_time":"2017-09-01 12:12:12","mile_price":26.654789,"begin_km":26.654789,"current_km":26.654789,"fetch_lat":26.654789,"fetch_lng":26.654789,"return_lat":69.123456,"return_lng":26.654789,"price_discount":26.654789,"price_pay":26.654789,"position_start_chn":"8iHMpXh5iY","position_end_chn":"pzl0oDuRjM","take_car_time_left":92569,"price_total":26.654789}
         * reject_reason : fsdfs
         * balance : 26.54656
         */

        private int audit_status;
        private int pledge_amount;
        private boolean pledge_paied;
        private OrderBean order;
        private String reject_reason;
        private double balance;

        public int getAudit_status() {
            return audit_status;
        }

        public void setAudit_status(int audit_status) {
            this.audit_status = audit_status;
        }

        public int getPledge_amount() {
            return pledge_amount;
        }

        public void setPledge_amount(int pledge_amount) {
            this.pledge_amount = pledge_amount;
        }

        public boolean isPledge_paied() {
            return pledge_paied;
        }

        public void setPledge_paied(boolean pledge_paied) {
            this.pledge_paied = pledge_paied;
        }

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public String getReject_reason() {
            return reject_reason;
        }

        public void setReject_reason(String reject_reason) {
            this.reject_reason = reject_reason;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public static class OrderBean {
            /**
             * orderid : 75222
             * vehicleid : 75591
             * status : 3
             * create_time : 2017-08-01 12:12:12
             * fetch_time : 2017-08-01 12:12:12
             * time_price : 26.654789
             * return_time : 2017-09-01 12:12:12
             * mile_price : 26.654789
             * begin_km : 26.654789
             * current_km : 26.654789
             * fetch_lat : 26.654789
             * fetch_lng : 26.654789
             * return_lat : 69.123456
             * return_lng : 26.654789
             * price_discount : 26.654789
             * price_pay : 26.654789
             * position_start_chn : 8iHMpXh5iY
             * position_end_chn : pzl0oDuRjM
             * take_car_time_left : 92569
             * price_total : 26.654789
             */

            private int orderid;
            private int vehicleid;
            private int status;
            private String create_time;
            private String fetch_time;
            private double time_price;
            private String return_time;
            private double mile_price;
            private double begin_km;
            private double current_km;
            private double fetch_lat;
            private double fetch_lng;
            private double return_lat;
            private double return_lng;
            private double price_discount;
            private double price_pay;
            private String position_start_chn;
            private String position_end_chn;
            private int take_car_time_left;
            private double price_total;

            public int getOrderid() {
                return orderid;
            }

            public void setOrderid(int orderid) {
                this.orderid = orderid;
            }

            public int getVehicleid() {
                return vehicleid;
            }

            public void setVehicleid(int vehicleid) {
                this.vehicleid = vehicleid;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getFetch_time() {
                return fetch_time;
            }

            public void setFetch_time(String fetch_time) {
                this.fetch_time = fetch_time;
            }

            public double getTime_price() {
                return time_price;
            }

            public void setTime_price(double time_price) {
                this.time_price = time_price;
            }

            public String getReturn_time() {
                return return_time;
            }

            public void setReturn_time(String return_time) {
                this.return_time = return_time;
            }

            public double getMile_price() {
                return mile_price;
            }

            public void setMile_price(double mile_price) {
                this.mile_price = mile_price;
            }

            public double getBegin_km() {
                return begin_km;
            }

            public void setBegin_km(double begin_km) {
                this.begin_km = begin_km;
            }

            public double getCurrent_km() {
                return current_km;
            }

            public void setCurrent_km(double current_km) {
                this.current_km = current_km;
            }

            public double getFetch_lat() {
                return fetch_lat;
            }

            public void setFetch_lat(double fetch_lat) {
                this.fetch_lat = fetch_lat;
            }

            public double getFetch_lng() {
                return fetch_lng;
            }

            public void setFetch_lng(double fetch_lng) {
                this.fetch_lng = fetch_lng;
            }

            public double getReturn_lat() {
                return return_lat;
            }

            public void setReturn_lat(double return_lat) {
                this.return_lat = return_lat;
            }

            public double getReturn_lng() {
                return return_lng;
            }

            public void setReturn_lng(double return_lng) {
                this.return_lng = return_lng;
            }

            public double getPrice_discount() {
                return price_discount;
            }

            public void setPrice_discount(double price_discount) {
                this.price_discount = price_discount;
            }

            public double getPrice_pay() {
                return price_pay;
            }

            public void setPrice_pay(double price_pay) {
                this.price_pay = price_pay;
            }

            public String getPosition_start_chn() {
                return position_start_chn;
            }

            public void setPosition_start_chn(String position_start_chn) {
                this.position_start_chn = position_start_chn;
            }

            public String getPosition_end_chn() {
                return position_end_chn;
            }

            public void setPosition_end_chn(String position_end_chn) {
                this.position_end_chn = position_end_chn;
            }

            public int getTake_car_time_left() {
                return take_car_time_left;
            }

            public void setTake_car_time_left(int take_car_time_left) {
                this.take_car_time_left = take_car_time_left;
            }

            public double getPrice_total() {
                return price_total;
            }

            public void setPrice_total(double price_total) {
                this.price_total = price_total;
            }
        }
    }
}
