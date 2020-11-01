package hggzs.club.entity;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "file")
public class FileInfo {
    @Id
    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private Long size;
    @Column
    private Integer dlNumber;
    @Column
    private String password;
    @Column
    private Date upTime;
    @Column
    private Integer time;
    @Column
    private Date exTime;

    public Integer getDlNumber() {
        return dlNumber;
    }

    public void setDlNumber(Integer dlNumber) {
        this.dlNumber = dlNumber;
    }

    @Override
    public String toString() {
        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", dlNumber=" + dlNumber +
                ", password=" + password +
                ", upTime=" + dt.format(upTime) +
                ", time=" + time +
                ", exTime=" + dt.format(exTime) +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Date getExTime() {
        return exTime;
    }

    public void setExTime(Date exTime) {
        this.exTime = exTime;
    }
}
