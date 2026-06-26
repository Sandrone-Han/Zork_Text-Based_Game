package org.uob.a2.gameobjects;


public class UseInformation {
    private boolean isUsed;
    private String action;
    private String target;
    private String result;
    private String message;

    public UseInformation(boolean isUsed, String action, String target, String result, String message) {
        this.isUsed = isUsed;
        this.action = action;
        this.target = target;
        this.result = result;
        this.message = message;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UseInformation{" +
                "isUsed=" + isUsed +
                ", action='" + action + '\'' +
                ", target='" + target + '\'' +
                ", result='" + result + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
