package id.co.ultrajaya.safeandro.model.response.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Table {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("username")
    @Expose
    private var username: String? = null
    @SerializedName("password")
    @Expose
    private var password: String? = null
    @SerializedName("role")
    @Expose
    private var role: Int? = null
    @SerializedName("email")
    @Expose
    private var email: String? = null
    @SerializedName("tanggal_buat")
    @Expose
    private var tanggalBuat: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getRole(): Int? {
        return role
    }

    fun setRole(role: Int?) {
        this.role = role
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getTanggalBuat(): String? {
        return tanggalBuat
    }

    fun setTanggalBuat(tanggalBuat: String) {
        this.tanggalBuat = tanggalBuat
    }

}
