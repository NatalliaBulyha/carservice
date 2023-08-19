package by.senla.training.bulyha.carservice.model;

import by.senla.training.bulyha.carservice.model.enums.MastersQualification;
import by.senla.training.bulyha.carservice.model.enums.MastersStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "masters")
public class Master extends IdEntity {

    @Column (name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column (name = "qualification")
    private MastersQualification qualification;
    @Column (name = "age")
    private int age;
    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private MastersStatus status;

    public Master() {
    }

    public Master(int id) {
        this.id = id;
    }

    public Master(int id, String name, MastersQualification qualification, int age, MastersStatus status) {
        this.id = id;
        this.name = name;
        this.qualification = qualification;
        this.age = age;
        this.status = status;
    }

    public Master(String name, MastersQualification qualification, int age, MastersStatus status) {
        this.name = name;
        this.qualification = qualification;
        this.age = age;
        this.status = status;
    }

    public Master(String name, MastersQualification qualification, int age) {
        this.name = name;
        this.qualification = qualification;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MastersQualification getQualification() {
        return qualification;
    }

    public void setQualification(MastersQualification qualification) {
        this.qualification = qualification;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MastersStatus getStatus() {
        return status;
    }

    public void setStatus(MastersStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Master{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qualification=" + qualification +
                ", age=" + age +
                ", status=" + status +
                '}';
    }
}