package com.sprout.frame.baseframe.entity;

/**
 * Create by Sprout at 2017/8/15
 */
public class LoginEntity {

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

        private int audit_status;
        private int pledge_amount;
        private boolean pledge_paied;
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
    }
}
