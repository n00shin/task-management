package ir.chica.task.enums;


import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskTypeEnum {

    FixedDuration("type_one"),FixedUnits("type_two"),FixedWork("type_three");

    private final String label;


    TaskTypeEnum(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }


    @Override
    public String toString() {
        return "TaskTypeEnum{" +
                "label='" + label + '\'' +
                '}';
    }
}
