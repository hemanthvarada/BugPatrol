
package com.example.bugpatrol;
public class HelperClass {

    String name, email, ph, password;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPh()
    {
        return ph;
    }

    public void setPh(String ph)
    {
        this.ph = ph;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public HelperClass(String name, String email, String ph, String password) {
        this.name = name;
        this.email = email;
        this.ph = ph;
        this.password = password;
    }

    public HelperClass() {
    }
}