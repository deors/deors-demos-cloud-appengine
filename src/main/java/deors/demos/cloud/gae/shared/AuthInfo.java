package deors.demos.cloud.gae.shared;

import java.io.Serializable;

public class AuthInfo implements Serializable {

    private static final long serialVersionUID = 5251640986422888374L;

    private boolean logged;

    private String nick;

    private String email;

    private String loginUrl;

    private String logoutUrl;

    public AuthInfo() {
        super();
    }

    public AuthInfo(boolean logged, String nick, String email, String loginUrl, String logoutUrl) {
        super();
        this.logged = logged;
        this.nick = nick;
        this.email = email;
        this.loginUrl = loginUrl;
        this.logoutUrl = logoutUrl;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }
}
