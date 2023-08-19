package by.senla.training.bulyha.carservice.dto;


public class ComparisonWithDBMasterDto {

    private String name;
    private String qualification;
    private int age;

    public ComparisonWithDBMasterDto(String name, String qualification, int age) {
        this.name = name;
        this.qualification = qualification;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ComparisonWithDBMasterDto{" +
                "name='" + name + '\'' +
                ", qualification=" + qualification +
                ", age=" + age +
                '}';
    }
}
