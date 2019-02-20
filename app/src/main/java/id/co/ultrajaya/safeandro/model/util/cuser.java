package id.co.ultrajaya.safeandro.model.util;

public class cuser {
    private String _IdUser = "";
    private String _Passwd;
    private String _NamaUser;
    private String _ErrMsg = "";

    public void set_IdUser(String ival) {
        _IdUser = ival;
    }

    public String get_IdUser() {
        return _IdUser;
    }

    public void set_NamaUser(String _NamaUser) {
        this._NamaUser = _NamaUser;
    }

    public String get_NamaUser() {
        return _NamaUser;
    }

    public void set_Passwd(String _Passwd) {
        this._Passwd = _Passwd;
    }

    public String get_Passwd() {
        return _Passwd;
    }

    public void set_ErrMsg(String _ErrMsg) {
        this._ErrMsg = _ErrMsg;
    }

    public String get_ErrMsg() {
        return _ErrMsg;
    }
}

