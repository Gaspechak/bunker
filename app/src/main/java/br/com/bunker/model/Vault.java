package br.com.bunker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

public class Vault implements Parcelable {

    @Exclude
    public String key;
    public String description;
    public String url;
    public String username;
    public String password;
    public String note;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.note);
    }

    public Vault() {
    }

    protected Vault(Parcel in) {
        this.key = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.username = in.readString();
        this.password = in.readString();
        this.note = in.readString();
    }

    public static final Parcelable.Creator<Vault> CREATOR = new Parcelable.Creator<Vault>() {
        @Override
        public Vault createFromParcel(Parcel source) {
            return new Vault(source);
        }

        @Override
        public Vault[] newArray(int size) {
            return new Vault[size];
        }
    };
}
