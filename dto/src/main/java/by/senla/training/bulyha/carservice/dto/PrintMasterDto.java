package by.senla.training.bulyha.carservice.dto;

public class PrintMasterDto {

    private Integer id;
    private String name;
    private String qualification;
    private Integer age;
    private String masterStatus;

    public PrintMasterDto(int id, String name, String qualification, int age, String masterStatus) {
        this.id = id;
        this.name = name;
        this.qualification = qualification;
        this.age = age;
        this.masterStatus = masterStatus;
    }

    public Integer getId() {
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMasterStatus() {
        return masterStatus;
    }

    public void setMasterStatus(String masterStatus) {
        this.masterStatus = masterStatus;
    }

    @Override
    public String toString() {
        return "PrintMasterDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qualification=" + qualification +
                ", age=" + age +
                ", masterStatus=" + masterStatus +
                '}';
    }
}
