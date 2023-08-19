package by.senla.training.bulyha.carservice.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class MasterDto {

    @NotEmpty(message = "Name cannot be null or empty")
    private String name;

    @NotEmpty(message = "Qualification cannot be null or empty")
    private String qualification;

    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 70, message = "Age should not be greater than 70")
    private Integer age;

    public MasterDto() {
    }

    public MasterDto(String name, String qualification, int age) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MasterDto{" +
                "name='" + name + '\'' +
                ", qualification='" + qualification + '\'' +
                ", age=" + age +
                '}';
    }
}
