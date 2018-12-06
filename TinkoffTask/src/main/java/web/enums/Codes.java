package web.enums;

public enum Codes {

    OK("00.Result.OK"),
    NOT_FOUND("01.Result.NotFound"),
    ERROR("02.Result.Error");

    private String desk;
    private String error;
    Codes(String desk) {
        this.desk = desk;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
